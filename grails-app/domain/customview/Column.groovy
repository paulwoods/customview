package customview

class Column {

	def customViewFactory

	static belongsTo = [view: View]

	String name
	String sql
	Integer sequence

	static mapping = {
		table "customview_column"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
		sql blank:false, maxSize:60
	}

	String toString() {
		"Column[$id] $name | $sql | $sequence"
	}

	def beforeValidate() {
		name = name?.trim()
		sql = sql?.trim()
	}

}
