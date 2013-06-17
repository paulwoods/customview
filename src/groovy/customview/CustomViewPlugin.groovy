package customview

import groovy.sql.Sql

class CustomViewPlugin {
	
	Sql getConnection() {

		groovy.sql.Sql.newInstance(
			"jdbc:mysql://localhost:3306/samples_dev", 
			"samples", 
			"samples", 
			"com.mysql.jdbc.Driver")
	}

	Long getCurrentUserId() {
		1
	}

}

