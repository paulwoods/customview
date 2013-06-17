package customview

class Column {

	def customViewFactory

	static belongsTo = [view: View]

	static hasMany = [ settings: Setting ]

	static TYPES = ["string","date"]

	String name
	String sql
	Integer sequence
	String type = TYPES[0]
	String classHead = ""
	String classBody = ""
	String td = ""
	String th = ""

	static mapping = {
		table "customview_column"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
		sql blank:false, maxSize:60
		type maxSize:50, inList:TYPES
		classHead maxSize:100
		classBody maxSize:100
		td maxSize:2000
		th maxSize:2000
	}

	String toString() {
		"Column[$id] $view.name | $name | $sql | $sequence"
	}

	def beforeValidate() {
		name = name?.trim()
		sql = sql?.trim()
		type = type?.trim()
		classHead = classHead?.trim()
		classBody = classBody?.trim()
		td = td?.trim()
		th = th?.trim()
	}

}
