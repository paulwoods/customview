package customview

class BodyBuilder {
	
	def customViewFactory

	private StringBuilder sb = new StringBuilder()
	private View view
	private List records
	private Map record
	private Column column
	private List<Setting> settings = []

	Long userId

	String build(View view, List records, Long userId) {
		this.view = view
		this.records = records
		this.userId = userId

		cacheSettings()

		processRecords()

		def html = sb.toString()

		column = null
		record = null
		records = null
		view = null
		sb = null
		settings = null

		html
	}
	
	private void cacheSettings() {
		this.settings = view.columns.collect { Column column ->
			this.column = column
			column.getSetting(userId)
		}
	}

	private void processRecords() {
		records.each { record ->
			this.record = record
			process()
		}
	}

	private void process() {
		sb << "<tr>\n"

		view.columns.eachWithIndex { Column column, index ->
			this.column = column
			
			Setting setting = settings[index]
			
			if(setting.visible) {
				openCell()
				writeValue()
				closeCell()
			}
		}

		sb << "</tr>\n"
	}

	private def getValue(Column column, Map record) {
		def value = record[column.name]
		value = (column.td) ? executeTemplate() : value
		value
	}

	private void openCell() {
		def clazz = column.classBody ? " class=\"$column.classBody\"" : ""
		sb << "<td$clazz>"
	}

	private void closeCell() {
		sb << "</td>\n"
	}

	private void writeValue() {
		def value = getValue(column, record)

		if(null == value) 
			value = "&nbsp;"
		else if("date" == column.type)
			value = value.format("yyyy-MM-dd")
	
		sb << value	
	}

	String executeTemplate() {
		def shell = new GroovyShell()
		def script = shell.parse(column.td)
		script.record = record

		try {
			script.run()
		} catch(e) {
			"#error"
		}
	}

}

