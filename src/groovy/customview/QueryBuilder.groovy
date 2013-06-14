package customview

class QueryBuilder {
	
	def customViewFactory

	Query createQuery(View view, fetchSize, offset) {
		def query = customViewFactory.createQuery()

		query.fetchSize = fetchSize
		query.offset = offset

		view.columns.each { Column column ->
			query.addSelect(column.sql + " \"$column.name\"")
		}

		view.tables.each { Table table ->
			query.addFrom table.name
		}

		query
	}

}

