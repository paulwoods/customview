package customview

import grails.converters.JSON

class CustomViewController {

	def customViewService
	def customViewPlugin

	def fetch(String name, Integer offset) {
		View view = View.findByName(name)
		Long userId = customViewPlugin.getCurrentUserId()

		if(!view)
			render status:500, text: [message:"view not found"] as JSON
		else
			render view.fetch(offset, userId) as JSON
	}

	// show the customize view

	def customize(String name,String returnURL) {
		View view = View.findByName(name)
		if(!view)
			render status:500, text: "The view was not found: $name"
		else
			[view:view, userId:customViewPlugin.currentUserId, returnURL:returnURL]
	}

	// change the sort

	def sort() {
		try {
			Setting setting = Setting.get(params.settingId)
			if(!setting) 
				throw new RuntimeException("Setting not found: $params.settingId")

			Long userId = params.long('userId')
			if(!userId) 
				throw new RuntimeException("The user was not found: $params.userId")
		
			View view = setting.column.view

			view.getSettings(userId).each { 
				it.sort = ""
				if(!it.save()) throw new RuntimeException("Failed to clear the settings.")
			}

			setting.sort = params.sort
			if(!setting.save()) 
				throw new RuntimeException("Failed to save the setting.")

			render(status:200, contentType:"application/json") { [
				id: setting.id,
				sort: params.sort 
			] }
			
		} catch(e) {
			log.error e.message, e
			render(status:500, contentType:"application/json") { [ message: e.message ] }
		}
	}

	def visible(Long userId, Long settingId, Boolean visible) {
		println "#"*80
		println params
		try {
			Setting setting = Setting.get(settingId)
			println "setting = $setting"
			if(!setting) 
				throw new RuntimeException("Setting not found: $settingId")

			println "userId = $userId"
			if(!userId) 
				throw new RuntimeException("The user was not found: $userId")
			
			println "visible = $visible"
			setting.visible = visible
			if(!setting.save()) 
				throw new RuntimeException("Failed to save the setting.")

			log.info "change visibility. $setting => $visible"

			render(status:200, contentType:"application/json") { [
				id: setting.id,
				visible: visible 
			] }
				
		} catch(e) {
			log.error e.message, e
			render(status:500, contentType:"application/json") { [ message: e.message ] }
		}
	}
	
}

