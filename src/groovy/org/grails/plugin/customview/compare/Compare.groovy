package org.grails.plugin.customview.compare

import org.grails.plugin.customview.*

abstract class Compare {
	String symbol
	View view
	String userid
	Query query
	Setting setting

	abstract void execute()

	protected Boolean hasContent() {
		if(setting.content) {
			true
		} else {
			log.warn "content empty. $setting.column"
			false
		}
	}

	protected String formatContent(String content) {
		switch(setting.column.type) {
			case "STRING":
			case "DATE":
			return "'" + content + "'"

			case "NUMBER":
			if(content.isNumber()) 
				return content
			else
				return "-1"

			break

			default:
			assert false
		}

		""
	}

	protected void simple() {

		if(!hasContent())
			return
			
		def content = formatContent(setting.content)
		if(!content)
			return

		query.addWhere setting.column.code + " " + setting.compare + " " + content
	}

	protected String multiple() {

		if(!setting.content) 
			return ""

		def content = setting.content.split("\n") as List

		content = content.collect { it.trim() }

		switch(setting.column.type) {
			case "STRING":
			case "DATE":
			content = content.collect { "'" + it + "'" }
			break

			case "NUMBER":
			break

			default:
			assert false
		}

		content.join ","
	}

}

