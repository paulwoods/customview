package customview

class Column {

	def customViewFactory

	static belongsTo = [view: View]

	static TYPES = ["string","date"]

	String name
	String sql
	Integer sequence
	String type = TYPES[0]
	String classHead = ""
	String classBody = ""

	static mapping = {
		table "customview_column"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
		sql blank:false, maxSize:60
		type maxSize:50, inList:TYPES
		classHead maxSize:200
		classBody maxSize:200
	}

	String toString() {
		"Column[$id] $name | $sql | $sequence"
	}

	def beforeValidate() {
		name = name?.trim()
		sql = sql?.trim()
	}

}
