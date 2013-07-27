package customview

class Setting {

	static belongsTo = [ column: Column ]

	static SORTS = [ "", "ASC", "DESC" ]
	
	static COMPARES = [
		"", "=", "<>", "<", ">", "<=", ">=", "begins with", "contains", "does not contain",
		"ends with", "is null", "is not null", "in list", "not in list",
	]

	Long userId
	Integer sequence
	Boolean visible = true
	String sort = SORTS[0]
	String compare = COMPARES[0]
	String content = ""

	static mapping = {
		table "customview_setting"
	}

	static constraints = {
		sort blank:true, inList: SORTS, maxSize: 10
		compare blank:true, inList:COMPARES, maxSize: 20
		content blank:true, maxSize: 1000
	}

	String toString() {
		"Setting[$id] $column.view | $column.name | $userId | $sequence | $visible"
	}

	def beforeValidate() {
		sort = sort?.trim()
		compare = compare?.trim()
		content = content?.trim()
	}

	void clearUserSorts() {
		column.clearUserSorts userId
	}
	
	static Setting getOrCreateSetting(Column column, Long userId) {
		def setting = Setting.findByColumnAndUserId(column, userId)
		
		if(setting) {
			println "existing setting $setting"
		} else {
			setting = new Setting(column:column, userId:userId)
			setting.sequence = getNextSettingSequence(column.view, userId)
			setting.save()
			println "created setting $setting"
		}

		setting
	}

	static Integer getNextSettingSequence(View view, Long currentUserId) {
		def settings = Setting.where {
			view.columns in columns && 
			userId == currentUserId
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
