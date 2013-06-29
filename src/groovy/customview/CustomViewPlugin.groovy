package customview

import groovy.sql.Sql

class CustomViewPlugin {
	
	Sql getConnection() {

		groovy.sql.Sql.newInstance(
			"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000", 
			"sa", 
			"", 
			"org.h2.Driver")
	}

	Long getCurrentUserId() {
		1
	}

}

