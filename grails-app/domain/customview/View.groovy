package customview

class View {

	def customViewFactory
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

	Map fetch(Integer offset, Long userId, database) {
		customViewService.fetch this, offset, userId, database
	}

	List<Setting> getSettings(Long userId) {
		columns.collect { Column column ->
			Setting.getOrCreateSetting column, userId
		}
	}

	String getSort(userId) {
		def setting = Setting.findAllByUserIdAndColumnInList(userId, columns).find { it.sort }

		if(setting) 
			"${setting.column.sql} ${setting.sort}"
		else
			""
	}

	void clearUserSorts(Long userId) {
		def settings = getSettings(userId)
		settings*.sort = ""
		settings*.save()
	}
	

}
