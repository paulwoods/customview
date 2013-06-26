package customview

class Column {

	static belongsTo = [view: View]

	static hasMany = [ settings: Setting ]

	static TYPES = ["STRING","DATE","NUMBER"]

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
		"Column[$id] $view.name | $name | $sql | $sequence | $type"
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

	Setting getSetting(Long userId) {
		Setting.getOrCreateSetting(this, userId)
	}

	void clearUserSorts(Long userId) {
		view.clearUserSorts userId
	}

	String value(Map record) {
		def val = record[name]
		
		if(null == val) 
			return ""

		if("DATE" == type)
			return val.format("yyyy-MM-dd") 

		val
	}
	
	
}

// protected Integer getNextColumnSequence(View view) {
// 	List<Column> columns = Column.createCriteria().list() {
// 		eq "view", view
// 		order "sequence", "desc"
// 	}

// 	columns ? columns[0].sequence + 1 : 1
// }
