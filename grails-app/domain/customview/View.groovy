package customview

import groovy.sql.*

class View {

	static hasMany = [ 
		columns: Column,
		tables: Table
	]

	String name
	Integer fetchSize = 50
	String url = ""
	String username = ""
	String password = ""
	String driver = ""

	static mapping = {
		table "customview_view"
		sort "name"
		columns sort:"sequence"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
		url blank:true, maxSize:500
		username blank:true, maxSize:100
		password blank:true, maxSize:100
		driver blank:true, maxSize:100
	}

	String toString() {
		"View[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

	List<Setting> getSettings(String userid) {
		columns.collect { Column column ->
			Setting.getOrCreateSetting column, userid
		}
	}

	List<Setting> getSettingsInOrder(String userid) {
		getSettings(userid).sort { it.sequence }
	}

	Setting getSortSetting(String currentUserid) {
		Setting.where {
			column in columns &&
			userid == currentUserid
			sort != "" 
		}.find()
	}

	void clearUserSorts(String userid) {
		def settings = getSettings(userid)
		settings*.sort = ""
		settings*.save()
	}
	
	List<Setting> getCompareSettings(String currentUserid) {
		Setting.where {
			column in columns &&
			userid == currentUserid
			compare != "" 
		}.list()
	}

	def getConnection() {
		if(!url || !username || null == password || !driver)
			null
		else
			Sql.newInstance url, username, password, driver
	}

}
