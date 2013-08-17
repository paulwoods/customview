package customview

class Setting {

	static belongsTo = [ column: Column ]

	static SORTS = [ "", "ASC", "DESC" ]
	
	static COMPARES = customview.compare.Symbols.enumConstants.symbol

	String userid
	Integer sequence
	Boolean visible = true
	String sort = SORTS[0]
	String compare = COMPARES[0]
	String content = ""

	static mapping = {
		table "customview_setting"
	}

	static constraints = {
		userid maxSize:100
		sort blank:true, inList: SORTS, maxSize: 10
		compare blank:true, inList:COMPARES, maxSize: 20
		content blank:true, maxSize: 1000
	}

	String toString() {
		"Setting[$id] $column.view | $column.name | $userid | $sequence | $visible"
	}

	def beforeValidate() {
		sort = sort?.trim()
		compare = compare?.trim()
		content = content?.trim()
	}

	void clearUserSorts() {
		column.clearUserSorts userid
	}
	
	static Setting getOrCreateSetting(Column column, String userid) {
		def setting = Setting.findByColumnAndUserid(column, userid)
		
		if(!setting) {
			setting = new Setting(column:column, userid:userid)
			setting.sequence = getNextSettingSequence(column.view, userid)
			setting.save()
		}

		setting
	}

	static Integer getNextSettingSequence(View view, String currentUserid) {
		def settings = Setting.where {
			view.columns in columns && 
			userid == currentUserid
		}.list(sort:"sequence", order:"desc")

		settings ? settings[0].sequence + 1 : 1
	}

	/**
	 * returns the number of rows in the content + 1. Usefull for setting the textarea's rows attribute.
	 **/
	Integer getNumRows() {
		1 + (content ? content.split('\n').size() : 0)
	}

}
