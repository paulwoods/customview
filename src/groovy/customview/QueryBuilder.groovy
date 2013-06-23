package customview

class QueryBuilder {
	
	Query build(View view, Integer offset, Long userId) {
		def query = new Query()

		query.fetchSize = view.fetchSize
		query.offset = offset

		view.columns.each { Column column ->
			query.addSelect(column.sql + " \"$column.name\"")
		}

		view.tables.each { Table table ->
			query.addFrom table.name
		}

		def sort = view.getSort(userId)
		if(sort) 
			query.addOrder sort

		query
	}

}

