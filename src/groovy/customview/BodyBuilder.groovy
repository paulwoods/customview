package customview

class BodyBuilder {
	
	def customViewFactory

	String build(View view, List records) {
		
		StringBuilder sb = new StringBuilder()

		records.each { record ->

			sb << "<tr>\n"

			view.columns.each { Column column ->
				def value = record[column.name]

				def clazz = column.classBody ? " class=\"$column.classBody\"" : ""

				sb << "<td$clazz>" 
				if(null == value) 
					sb << "&nbsp;"
				else if("date" == column.type)
					sb << value.format("yyyy-MM-dd")
				else
					sb << value 
				sb << "</td>\n"
			}

			sb << "</tr>\n"
		}

		sb.toString()
	}

}

