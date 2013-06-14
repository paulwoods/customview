package customview

class HeadBuilder {
	
	def customViewFactory

	String build(View view) {
		
		StringBuilder sb = new StringBuilder()

		sb << "<tr>\n"

		view.columns.each { Column column ->
			def clazz = column.classHead ? " class=\"$column.classHead\"" : ""
			sb << "<th$clazz>" 
			sb << column.name
			sb << "</th>\n"
		}

		sb << "</tr>\n"

		sb.toString()
	}

}

