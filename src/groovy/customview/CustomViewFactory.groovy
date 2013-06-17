package customview

class CustomViewFactory {
	
	def customViewPlugin
	def customViewService

	View createView() {
		new View(customViewFactory:this, customViewService:customViewService)
	}

	Column createColumn() {
		new Column(customViewFactory:this)
	}

	Table createTable() {
		new Table(customViewFactory:this)
	}

	Order createOrder() {
		new Order(customViewFactory:this)
	}

	QueryBuilder createQueryBuilder() {
		new QueryBuilder(customViewFactory:this)
	}

	Query createQuery() {
		new Query(customViewFactory:this)
	}

	Runner createRunner() {
		new Runner(customViewFactory:this, customViewPlugin:customViewPlugin)
	}

	HeadBuilder createHeadBuilder() {
		new HeadBuilder(customViewFactory:this)
	}

	BodyBuilder createBodyBuilder() {
		new BodyBuilder(customViewFactory:this)
	}

	Setting createSetting() {
		new Setting(customViewFactory:this)
	}

}

