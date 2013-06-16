package customview

class CustomViewService {

	def sessionFactory
	def customViewPlugin
	def customViewFactory

	View createView(Map params) {
		View view = customViewFactory.createView()
		view.properties = params

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
		column.properties = params
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

	Table createTable(View view, Map params) {
		Table table = customViewFactory.createTable()
		table.properties = params
		view.addToTables table

		if(view.save()) {
			table 
		} else {
			log.warn view?.dump()
			log.warn table?.dump()
			throw new FailedToCreateTableException(table)
		}
		
	}

	Order createOrder(View view, Map params) {
		Order order = customViewFactory.createOrder()
		order.properties = params
		view.addToOrders order

		if(view.save()) {
			order 
		} else {
			log.warn "unable to save the view $view"
			log.warn view?.dump()
			log.warn order?.dump()
			throw new FailedToCreateOrderException(order)
		}
	}

	protected Integer getNextSequence(View view) {
		List<Column> columns = Column.createCriteria().list() {
			eq "view", view
			order "sequence", "desc"
		}

		columns ? columns[0].sequence + 1 : 1
	}


	Map fetch(View view, Integer offset) {
		def query = createQuery(view, offset)
		def records = query.run()
		def html = recordsToHTML(view, records)
		[
			offset: offset + (records?.size() ?: 0),
			html: html,
			moreData: (0 < records?.size() ?: false)
		]
	}

	Query createQuery(View view, Integer offset) {
		QueryBuilder builder = customViewFactory.createQueryBuilder()
		Query query = builder.createQuery(view, offset)
		query
	}
	
	String recordsToHTML(View view, List records) {
		BodyBuilder builder = customViewFactory.createBodyBuilder()
		def html = builder.build(view, records)
		html
	}

}

