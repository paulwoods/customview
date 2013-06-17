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

	Column createColumn(Map params) {
		customViewService.createColumn this, params
	}

	Table createTable(Map params) {
		customViewService.createTable this, params
	}

	Map fetch(Integer offset, Long userId) {
		customViewService.fetch this, offset, userId
	}

	List<Setting> getSettings(Long userId) {
		customViewService.getSettings(this, userId)
	}

	String getSort(userId) {
		def setting = Setting.findAllByUserIdAndColumnInList(userId, columns).find { it.sort }

		if(setting) 
			"${setting.column.sql} ${setting.sort}"
		else
			""
	}

}
