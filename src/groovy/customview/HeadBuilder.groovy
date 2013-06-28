package customview

class HeadBuilder {
	
	String build(View view, Long userId) {
		
		StringBuilder sb = new StringBuilder()

		sb << "<tr>\n"

		view.getSettingsInOrder(userId).each { setting -> 
			def column = setting.column
			
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

