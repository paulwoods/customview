package customview

class Table {

	static belongsTo = [view: View]

	String name

	static mapping = {
		table "customview_table"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:"view"
	}

	String toString() {
		"Table[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

}
