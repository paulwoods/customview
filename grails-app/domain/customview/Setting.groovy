package customview

class Setting {

	static belongsTo = [ column: Column ]
	Long userId
	Integer sequence

	static mapping = {
		table "customview_setting"
	}

	static constraints = {
	}

	String toString() {
		"Setting[$id] $column.view | $column.name | $userId | $sequence"
	}
}
