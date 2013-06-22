package customview

import grails.converters.JSON

class CustomViewController {

	def customViewService
	def customViewPlugin

	def fetch(String name, Integer offset) {
		View view = View.findByName(name)
		Long userId = customViewPlugin.getCurrentUserId()

		if(!view) {
            log.warn "view not found: $name"
            render status:500, text: [message:"view not found"] as JSON
        } else {
            render view.fetch(offset, userId) as JSON
        }
	}

	def customize(String name,String returnURL) {
		View view = View.findByName(name)
		if(!view) {
            log.warn "view not found: $name"
            render status:500, text: "The view was not found: $name"
        } else {
            [view:view, userId:customViewPlugin.currentUserId, returnURL:returnURL]
        }
	}

	def sort(Long settingId, Long userId, String sort) {
		try {
            Setting setting = getSetting(settingId)
            validateUserId(userId)

            // clear the sort fields for all columns for the current user
            setting.column.view.getSettings(userId).each {
				it.sort = ""
                saveSetting it
			}

			setting.sort = sort
            saveSetting setting
            log.info "change sort to $sort. $setting"
            renderSetting setting
		} catch(e) {
			log.error e.message, e
			render(status:500, contentType:"application/json") { [ message: e.message ] }
		}
	}


    def visible(Long userId, Long settingId, Boolean visible) {
		try {
            Setting setting = getSetting(settingId)
            validateUserId(userId)
            setting.visible = visible
            saveSetting setting
			log.info "change visibility to $visible. $setting"
            renderSetting setting
		} catch(e) {
			log.error e.message, e
			render(status:500, contentType:"application/json") { [ message: e.message ] }
		}
	}

    def compare(Long userId, Long settingId, String compare) {
        try {
            Setting setting = getSetting(settingId)
            validateUserId(userId)
            setting.compare = compare
            saveSetting setting
            log.info "change compare to $compare. $setting"
            renderSetting setting
        } catch(e) {
            log.error e.message, e
            render(status:500, contentType:"application/json") { [ message: e.message ] }
        }
    }

    def value(Long userId, Long settingId, String value) {
        try {
            Setting setting = getSetting(settingId)
            validateUserId(userId)
            setting.value = value
            saveSetting setting
            log.info "change value to $value. $setting"
            renderSetting setting
        } catch(e) {
            log.error e.message, e
            render(status:500, contentType:"application/json") { [ message: e.message ] }
        }
    }

    private void renderSetting(setting) {
        render(status: 200, contentType: "application/json") {
            [
                id: setting.id,
                visible: setting.visible,
                sort: params.sort,
                compare: setting.compare,
                value: setting.value
            ]
        }
    }

    private void saveSetting(Setting setting) {
        if (!setting.save()) {
            log.warn "unable to save the setting. $setting"
            log.warn setting.dump()
            throw new RuntimeException("Failed to save the setting.")
        }
    }

    private void validateUserId(long userId) {
        if (!userId) {
            log.warn "user not found: $userId"
            throw new RuntimeException("The user was not found: $userId")
        }
    }

    private Setting getSetting(settingId) {
        Setting setting = Setting.get(settingId)
        if (!setting) {
            log.warn "setting not found: $settingId"
            throw new RuntimeException("Setting not found: $settingId")
        }
        setting
    }

}

