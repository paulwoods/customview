package customview

class QueryBuilder {
	
	Query build(View view, Integer offset, Long userId) {
		def query = new Query()

		if(null == view) {
			log.warn "view is null"
			return query
		}

		query.count = view.fetchSize
		query.offset = offset

		view.columns.each { Column column ->
			query.addSelect column.sql + " \"$column.name\""
		}

		view.tables.each { Table table ->
			query.addFrom table.name
		}

		view.getCompareSettings(userId).each { Setting setting ->
			def value = setting.value
			
			switch(setting.compare) {
				case "=":
				case "<>":
				case "<":
				case ">":
				case "<=":
				case ">=":
				if(!value) return
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					value = "'" + value + "'"
					break

					case "NUMBER":
					break
				}
				
				query.addWhere setting.column.sql + " " + setting.compare + " " + value
				
				break

				case "begins with":
				if(!value) return
				query.addWhere setting.column.sql + " like '" + value + "%'"
				break

				case "contains":
				if(!value) return
				query.addWhere setting.column.sql + " like '%" + value + "%'"
				break

				case "does not contain":
				if(!value) return
				query.addWhere setting.column.sql + " not like '%" + value + "%'"
				break

				case "ends with":
				if(!value) return
				query.addWhere setting.column.sql + " like '%" + value + "'"
				break

				case "is null":
				query.addWhere setting.column.sql + " is null"
				break

				case "is not null":
				query.addWhere setting.column.sql + " is not null"
				break

				case "in list":
				if(!value) return
				def values = []
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					value.split("\n").each { 
						def v = it.trim() 
						if(v) {
							values << "'" + v + "'"
						}
					}
					value = values.join(",")
					break

					case "NUMBER":
					value.split("\n").each { 
						def v = it.trim() 
						if(v) {
							values << v
						}
					}
					value = values.join(",")
					break
				}

				query.addWhere setting.column.sql + " in (" + value + ")"
				break

				case "not in list":
				if(!value) return
				def values = []
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					value.split("\n").each { 
						def v = it.trim() 
						if(v) {
							values << "'" + v + "'"
						}
					}
					value = values.join(",")
					break

					case "NUMBER":
					value.split("\n").each { 
						def v = it.trim() 
						if(v) {
							values << v
						}
					}
					value = values.join(",")
					break
				}

				query.addWhere setting.column.sql + " not in (" + value + ")"
				break
			}

		}

		def setting = view.getSortSetting(userId)
		if(setting)
			query.addOrder setting.column.sql + " " + setting.sort

		query
	}

}

