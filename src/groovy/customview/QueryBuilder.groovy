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
			query.addSelect column.code + " \"$column.name\""
		}

		view.tables.each { Table table ->
			query.addFrom table.name
		}

		view.getCompareSettings(userId).each { Setting setting ->
			def content = setting.content
			
			switch(setting.compare) {
				case "=":
				case "<>":
				case "<":
				case ">":
				case "<=":
				case ">=":
				if(!content) return
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					content = "'" + content + "'"
					break

					case "NUMBER":
					if(!content.isNumber()) 
						content = "-1"
					break
				}
				
				query.addWhere setting.column.code + " " + setting.compare + " " + content
				
				break

				case "begins with":
				if(!content) return
				query.addWhere setting.column.code + " like '" + content + "%'"
				break

				case "contains":
				if(!content) return
				query.addWhere setting.column.code + " like '%" + content + "%'"
				break

				case "does not contain":
				if(!content) return
				query.addWhere setting.column.code + " not like '%" + content + "%'"
				break

				case "ends with":
				if(!content) return
				query.addWhere setting.column.code + " like '%" + content + "'"
				break

				case "is null":
				query.addWhere setting.column.code + " is null"
				break

				case "is not null":
				query.addWhere setting.column.code + " is not null"
				break

				case "in list":
				if(!content) return
				def contents = []
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					content.split("\n").each { 
						def v = it.trim() 
						if(v) {
							contents << "'" + v + "'"
						}
					}
					content = contents.join(",")
					break

					case "NUMBER":
					content.split("\n").each { 
						def v = it.trim() 
						if(v) {
							contents << v
						}
					}
					content = contents.join(",")
					break
				}

				query.addWhere setting.column.code + " in (" + content + ")"
				break

				case "not in list":
				if(!content) return
				def contents = []
				switch(setting.column.type) {
					case "STRING":
					case "DATE":
					content.split("\n").each { 
						def v = it.trim() 
						if(v) {
							contents << "'" + v + "'"
						}
					}
					content = contents.join(",")
					break

					case "NUMBER":
					content.split("\n").each { 
						def v = it.trim() 
						if(v) {
							contents << v
						}
					}
					content = contents.join(",")
					break
				}

				query.addWhere setting.column.code + " not in (" + content + ")"
				break
			}

		}

		def setting = view.getSortSetting(userId)
		if(setting)
			query.addOrder setting.column.code + " " + setting.sort

		query
	}

}

