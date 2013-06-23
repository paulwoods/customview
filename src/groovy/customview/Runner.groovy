package customview

import groovy.sql.Sql

class Runner {
	
	List run(Query query, database) {
		def text = query.toSQL()
		println "query = $text"
		def rows = database.rows(text)
		database.close()
		rows
	}

}

