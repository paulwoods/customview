package customview

class Table {

	def customViewFactory

	static belongsTo = [view: View]

	String name

	static mapping = {
		table "customview_table"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
	}

	String toString() {
		"Table[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

}
