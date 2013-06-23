package customview

class HeadBuilder {
	
	String build(View view, Long userId) {
		
		StringBuilder sb = new StringBuilder()

		sb << "<tr>\n"

		view.columns.each { Column column ->

			Setting setting = column.getSetting(userId)

			if(setting.visible) {
				def clazz = column.classHead ? " class=\"$column.classHead\"" : ""
				sb << "<th$clazz>" 
				sb << column.name
				sb << "</th>\n"
			}
		}

		sb << "</tr>\n"

		sb.toString()
	}

}

