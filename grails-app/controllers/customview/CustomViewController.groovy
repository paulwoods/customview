package customview

import grails.converters.JSON

class CustomViewController {

	def customViewPlugin
	def customViewService

	def index() {
		redirect uri:"/"
	}

	def fetch(String name, Integer offset) {
		View view = View.findByName(name)
		if(!view) {
			log.warn "view not found: $name"
			return render(status:500, text: [message:"view not found"] as JSON)
		}

		try {
			Result result = customViewService.fetch(view, offset)
			result.createHTML()
			render result.toMap() as JSON
		} catch(e) {
			log.error e.message, e
			return render(status:500, text: [message:e.message] as JSON)
		}
	}

	def customize(String name,String returnURL) {
		cache false
		View view = View.findByName(name)
		if(!view) {
			log.warn "view not found: $name"
			return render(status:500, text: "The view was not found: $name")
		}
		[view:view, userId:customViewPlugin.getCurrentUserId(), returnURL:returnURL]
	}

	def sort(Long settingId, String sort) {
		withSetting(settingId) { Setting setting ->
			setting.clearUserSorts()
			setting.sort = sort
			finish setting
		}
	}
	
	def visible(Long settingId, Boolean visible) {
		withSetting(settingId) { Setting setting ->
			setting.visible = visible
			finish setting
		}
	}

	def compare(Long settingId, String compare) {
		withSetting(settingId) { Setting setting ->
			setting.compare = compare
			finish setting
		}
	}

	def value(Long settingId, String value) {
		withSetting(settingId) { Setting setting ->
			setting.value = value
			finish setting
		}
	}

	def reset(Long settingId) {
		withSetting(settingId) { Setting setting ->
			setting.visible = true
			setting.sort = Setting.SORTS[0]
			setting.compare = Setting.COMPARES[0]
			setting.value = ""
			finish setting
		}
	}

	def order() {
		params.each { key, value -> 
			if(key.startsWith("id")) {
				def setting = Setting.get(key.substring(2))
				if(setting) {
					setting.sequence = value.toInteger()
					setting.save()
				}
			}
		}
		render(status: 200, contentType: "application/json") { [:] }
	}

	private def withSetting(Long settingId, Closure closure) {
		Setting setting = Setting.get(settingId)
		if (setting)
			return closure(setting)

		log.warn "The setting was not found: $settingId"

		return render(status:500, contentType:"application/json") { [ 
			message: "The setting was not found." 
		] }
	}

	private def finish(Setting setting) {
		if (setting.save(flush:true)) {
			log.info "updated setting. $setting"
			render(status: 200, contentType: "application/json") {
				[
					id: setting.id,
					userId: setting.userId,
					visible: setting.visible,
					sort: setting.sort,
					compare: setting.compare,
					value: setting.value
				]
			}
		} else {
			log.warn "Unable to save the setting. $setting"
			return render(status:500, contentType:"application/json") { [ 
				message: "Unable to save the setting." 
			] }
		}
	}

}

