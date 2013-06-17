package customview

import groovy.sql.Sql

class Runner {
	
	def customViewFactory
	def customViewPlugin

	List run(Query query) {
		def database = new Sql(customViewPlugin.connection)
		def text = query.toSQL()
		println "query = $text"
		def rows = database.rows(text)
		database.close()
		rows
	}

}

