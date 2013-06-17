package customview

class CustomViewService {

	def sessionFactory
	def customViewPlugin
	def customViewFactory

	View createView(Map params) {
		View view = customViewFactory.createView()
		view.properties = params

		if(view.save()) {
			log.info "created view $params.name"
			view 
		} else {
			log.warn "unable to save the view $view"
			log.warn view.dump()
			throw new FailedToCreateViewException(view)
		}
	}

	Column createColumn(View view, Map params) {
		Column column = customViewFactory.createColumn()
		column.properties = params
		column.sequence = getNextColumnSequence(view)
		view.addToColumns column

		if(view.save()) {
			log.info "created column $params.name"
			column 
		} else {
			log.warn "unable to save the view $view"
			log.warn view.dump()
			throw new FailedToCreateColumnException(column)
		}
	}

	Table createTable(View view, Map params) {
		Table table = customViewFactory.createTable()
		table.properties = params
		view.addToTables table

		if(view.save()) {
			log.info "created table $params.name"
			table 
		} else {
			log.warn view.dump()
			throw new FailedToCreateTableException(table)
		}
	}

	Order createOrder(View view, Map params) {
		Order order = customViewFactory.createOrder()
		order.properties = params
		view.addToOrders order

		if(view.save()) {
			log.info "created order $params.name"
			order 
		} else {
			log.warn "unable to save the view $view"
			log.warn view.dump()
			throw new FailedToCreateOrderException(order)
		}
	}

	protected Integer getNextColumnSequence(View view) {
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
		builder.createQuery(view, offset)
	}
	
	String recordsToHTML(View view, List records) {
		BodyBuilder builder = customViewFactory.createBodyBuilder()
		builder.build(view, records)
	}

	List<Setting> getSettings(View view, Long userId) {
		def settings = []
		view.columns.each { Column column ->
			settings << getOrCreateSetting(column, userId)
		}
		settings
	}

	Setting getOrCreateSetting(Column column, Long userId) {
		def setting = Setting.findByColumnAndUserId(column, userId)
		
		if(setting) 
			setting

		setting = customViewFactory.createSetting()
		setting.column = column
		setting.userId = userId
		setting.sequence = getNextSettingSequence(column.view, userId)

		column.addToSettings setting
		
		if(column.save()) {
			log.info "created setting $column.name $userId"
			setting 
		} else {
			log.warn "unable to save the column $column"
			log.warn column.dump()
			throw new RuntimeException("something blew")
		}
	}

	Integer getNextSettingSequence(View view, Long userId) {
		def settings = Setting.where {
			column.view == view &&
			userId == userId
		}.list(sort:"sequence", order:"desc")

		settings ? settings[0].sequence + 1 : 1
	}

}

