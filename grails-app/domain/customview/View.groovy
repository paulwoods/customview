package customview

class View {

	def customViewService

	static hasMany = [ 
		columns: Column,
		tables: Table
	]

	String name
	Integer fetchSize = 50

	static mapping = {
		table "customview_view"
		sort "name"
		columns sort:"sequence"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
	}

	String toString() {
		"View[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

	Result fetch(Integer offset, Long userId, database) {
		customViewService.fetch this, offset, userId, database
	}

	List<Setting> getSettings(Long userId) {
		columns.collect { Column column ->
			Setting.getOrCreateSetting column, userId
		}
	}

	List<Setting> getSettingsInOrder(Long userId) {
		getSettings(userId).sort { it.sequence }
	}

	Setting getSortSetting(Long currentUserId) {
		Setting.where {
			column in columns &&
			userId == currentUserId
			sort != "" 
		}.find()
	}

	void clearUserSorts(Long userId) {
		def settings = getSettings(userId)
		settings*.sort = ""
		settings*.save()
	}
	
	List<Setting> getCompareSettings(Long currentUserId) {
		Setting.where {
			column in columns &&
			userId == currentUserId
			compare != "" 
		}.list()
	}


}
