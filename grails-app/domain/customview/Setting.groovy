package customview

class Setting {

	static belongsTo = [ column: Column ]

	static SORTS = [ "", "ASC", "DESC" ]

	Long userId
	Integer sequence
	Boolean visible = true
	String sort = ""
    String compare = ""
    String value = ""

	static mapping = {
		table "customview_setting"
	}
    static constraints = {
		sort inList: SORTS, maxSize: 10
        compare blank:true, maxSize: 20
        value blank:true, maxSize: 1000
	}

    String toString() {
		"Setting[$id] $column.view | $column.name | $userId | $sequence"
	}
}
