package customview

class HeadBuilder {
	
	String build(View view, Long userId) {
		
		StringBuilder sb = new StringBuilder()

		sb << "<tr>\n"

		view.getSettingsInOrder(userId).each { setting -> 
			if(setting.visible) {
				def column = setting.column
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

