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

	QueryBuilder createQueryBuilder() {
		new QueryBuilder(customViewFactory:this)
	}

	Query createQuery() {
		new Query(customViewFactory:this)
	}

	Runner createRunner() {
		new Runner(customViewFactory:this, customViewPlugin:customViewPlugin)
	}
}

