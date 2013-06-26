package customview

import groovy.sql.Sql

class Runner {
	
	List run(String query, database) {
		log.debug "$query"
		database.rows query
	}

}

