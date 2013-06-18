package customview

class Setting {

	static belongsTo = [ column: Column ]

	static SORTS = [ "", "ASC", "DESC" ]

	Long userId
	Integer sequence
	Boolean visible = true
	String sort = ""

	static mapping = {
		table "customview_setting"
	}

	static constraints = {
		sort inList: SORTS
	}

	String toString() {
		"Setting[$id] $column.view | $column.name | $userId | $sequence"
	}
}
