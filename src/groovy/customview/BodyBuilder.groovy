package customview

/**
 * This class converts the list of records into html table rows
 **/
class BodyBuilder {
	
	def shell = new GroovyShell()

	String build(View view, List records, long userId) {
		if(!records || !view) 
			return ""

		def settings = view.columns*.getSetting(userId).sort { it.sequence }

		StringBuilder out = new StringBuilder()
		records.each { record ->
			out << "<tr>"
			settings.each { Setting setting ->
				if(setting.visible) {
					Column column = setting.column
					def clazz = column.classBody ? " class=\"$column.classBody\"" : ""
					out << "<td$clazz>"
					if(column.td) {
						out << executeTemplate(column, record)
					} else {
						out << column.value(record)
					}
					out << "</td>"
				}
			}
			out << "</tr>"
		}
		out.toString()
	}

	String executeTemplate(Column column, Map record) {
		def script = shell.parse(column.td)
		script.column = column
		script.record = record

		try {
			script.run()
		} catch(e) {
			"#error"
		}
	}

}


