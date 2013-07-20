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
		url nullable:true, maxSize:500
		username nullable:true, maxSize:100
		password nullable:true, maxSize:100
		driver nullable:true, maxSize:100
	}

	String toString() {
		"View[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

	List<Setting> getSettings(Long userId) {
		columns.collect { Column column ->
			Setting.getOrCreateSetting column, userId
		}
	}

	List<Setting> getSettingsInOrder(Long userId) {
		getSettings(userId).sort { it.sequence }
	}

	Setting getSortSetting(Long currentUserId) {
		Setting.where {
			column in columns &&
			userId == currentUserId
			sort != "" 
		}.find()
	}

	void clearUserSorts(Long userId) {
		def settings = getSettings(userId)
		settings*.sort = ""
		settings*.save()
	}
	
	List<Setting> getCompareSettings(Long currentUserId) {
		Setting.where {
			column in columns &&
			userId == currentUserId
			compare != "" 
		}.list()
	}

	def getConnection() {
		if(!url || !username || !password || !driver)
			null
		else
			Sql.newInstance(url, username, password, driver)
	}

}
