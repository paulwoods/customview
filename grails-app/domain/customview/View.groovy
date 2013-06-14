package customview

class View {

	def customViewFactory
	def customViewService

	static hasMany = [ 
		columns: Column,
		tables: Table
	]

	String name

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

	Map fetch(Integer fetchSize, Integer offset) {
		customViewService.fetch this, fetchSize, offset
	}

}
