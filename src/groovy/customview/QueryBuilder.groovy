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

		view.getCompareSettings(userId).each { Setting setting ->
			if("STRING" == setting.column.type)
				query.addWhere setting.column.sql + " " + setting.compare + " '" + setting.value + "'"
			else if("NUMBER" == setting.column.type) 
				query.addWhere setting.column.sql + " " + setting.compare + " " + setting.value
			else if("DATE" == setting.column.type) 
				query.addWhere setting.column.sql + " " + setting.compare + " '" + setting.value + "'"
			else
				assert false
		}

		def setting = view.getSortSetting(userId)
		if(setting)
			query.addOrder setting.column.sql + " " + setting.sort

		query
	}
}

