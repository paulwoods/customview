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

	Setting getSortSetting(Long userId) {
		Setting.where {
			column in columns &&
			userId == userId
			sort != "" 
		}.find()
	}

	void clearUserSorts(Long userId) {
		def settings = getSettings(userId)
		settings*.sort = ""
		settings*.save()
	}
	
	List<Setting> getCompareSettings(Long userId) {
		Setting.where {
			column in columns &&
			userId == userId
			compare != "" 
		}.list()
	}


}
