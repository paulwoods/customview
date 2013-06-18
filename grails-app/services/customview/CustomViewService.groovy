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

	protected Integer getNextColumnSequence(View view) {
		List<Column> columns = Column.createCriteria().list() {
			eq "view", view
			order "sequence", "desc"
		}

		columns ? columns[0].sequence + 1 : 1
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
			return setting

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

	Map fetch(View view, Integer offset, Long userId) {
		QueryBuilder queryBuilder = customViewFactory.createQueryBuilder()
		Query query = queryBuilder.createQuery(view, offset, userId)

		def records = query.run()

		BodyBuilder bodyBuilder = customViewFactory.createBodyBuilder()
		String html = bodyBuilder.build(view, records, userId)

		[
			offset: offset + (records?.size() ?: 0),
			html: html,
			moreData: (0 < records?.size() ?: false)
		]
	}



}

