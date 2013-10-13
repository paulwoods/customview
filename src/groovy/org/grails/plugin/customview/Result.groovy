package org.grails.plugin.customview

class Result {
	BodyBuilder bodyBuilder = new BodyBuilder()

	View view
	Integer offset = 0
	String userid = 0
	List records = []
	String html = ""
	Boolean moreData = false
	
	void createHTML() {
		html = bodyBuilder.build(view, records, userid)
	}

	Map toMap() {
		[offset: offset, html: html, moreData: moreData ]
	}

}
