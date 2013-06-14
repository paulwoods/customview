package customview

class BodyBuilder {
	
	def customViewFactory

	private StringBuilder sb = new StringBuilder()
	private View view
	private List records
	private Map record
	private Column column

	String build(View view, List records) {
		this.view = view
		this.records = records

		records.each { record ->
			this.record = record
			process()
		}

		def html = sb.toString()

		column = null
		record = null
		records = null
		view = null
		sb = null

		html
	}

	private void process() {
		sb << "<tr>\n"

		view.columns.each { Column column ->
			this.column = column
			openCell()
			writeValue()
			closeCell()
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

