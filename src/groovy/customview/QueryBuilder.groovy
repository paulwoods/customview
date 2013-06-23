package customview

class QueryBuilder {
	
	Query build(View view, Integer offset, Long userId) {
		def query = new Query()

		if(null == view) {
			log.warn "view is null"
			return query
		}

		query.fetchSize = view.fetchSize
		query.offset = offset

		view.columns.each { Column column ->
			query.addSelect column.sql + " \"$column.name\""
		}

		view.tables.each { Table table ->
			query.addFrom table.name
		}

		def setting = view.getSortSetting(userId)
		if(setting)
			query.addOrder setting.column.sql + " " + setting.sort

		query
	}
}

