package customview

class CustomViewService {

	def sessionFactory
	def customViewPlugin
	def customViewFactory

	View createView(String name) {
		View view = customViewFactory.createView()
		view.name = name

		if(view.save()) {
			view 
		} else {
			log.warn "unable to save the view $view"
			log.warn view?.dump()
			throw new FailedToCreateViewException(view)
		}
	}

	Column createColumn(View view, Map params) {
		Column column = customViewFactory.createColumn()
		column.name = params.name
		column.sql = params.sql
		column.sequence = getNextSequence(view)
		view.addToColumns column

		if(view.save()) {
			column 
		} else {
			log.warn "unable to save the view $view"
			log.warn view?.dump()
			log.warn column?.dump()
			throw new FailedToCreateColumnException(column)
		}
	}

	Integer getNextSequence(View view) {
		List<Column> columns = Column.createCriteria().list() {
			eq "view", view
			order "sequence", "desc"
		}

		columns ? columns[0].sequence + 1 : 1
	}

	Table createTable(View view, Map params) {
		Table table = customViewFactory.createTable()
		table.name = params.name
		view.addToTables table

		if(view.save()) {
			table 
		} else {
			log.warn view?.dump()
			log.warn table?.dump()
			throw new FailedToCreateTableException(table)
		}
		
	}

	Map fetch(View view, Integer fetchSize, Integer offset) {
		def query = createQuery(view, fetchSize, offset)
		def records = query.run()
		def html = recordsToHTML(view, records)
		[
			offset: offset + (records?.size() ?: 0),
			html: html,
			moreData: (0 < records?.size() ?: false)
		]
	}

	Query createQuery(View view, Integer fetchSize, Integer offset) {
		QueryBuilder builder = customViewFactory.createQueryBuilder()
		Query query = builder.createQuery(view, fetchSize, offset)
		query
	}
	
	String recordsToHTML(View view, List records) {
		def columns = view.columns.name
		
		StringBuilder sb = new StringBuilder()

		records.each { record ->
			sb << "<tr>"
			columns.each { key ->
				sb << "<td>" 
				sb << record[key] 
				sb << "</td>"
			}
			sb << "</tr>"
		}

		sb.toString()
	}

}

