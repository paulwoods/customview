package customview

class Result {
	BodyBuilder bodyBuilder = new BodyBuilder()

	View view
	Integer offset = 0
	Long userId = 0
	List records = []
	String html = ""
	Boolean moreData = false
	
	void createHTML() {
		html = bodyBuilder.build(view, records, userId)
	}

	Map toMap() {
		[offset: offset, html: html, moreData: moreData ]
	}

}
