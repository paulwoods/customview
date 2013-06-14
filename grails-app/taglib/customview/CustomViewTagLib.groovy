package customview

class CustomViewTagLib {

	def customViewFactory

	static namespace = "specteam"

	def customView = { attrs, body ->
		View view = View.findByName(attrs.name)
		if(!view)
			throw new IdInvalidException(this, attrs)

		openTable attrs
		caption()
		openHead()
		writeHeader view
		closeHead()
		openBody()
		closeBody()
		closeTable()
		writeJavascript attrs
	}

	private openTable(attrs) {
		out << """<table id="$attrs.name" class="classview">\n"""
	}

	private closeTable() {
		out << "</table>\n"
	}

	private caption() {
		out << """<caption>There are x records.</caption>\n"""
	}

	private openHead() {
		out << """<thead>\n"""
	}

	private closeHead() {
		out << """</thead>\n"""
	}

	private openBody() {
		out << """<tbody>\n"""
	}

	private closeBody() {
		out << """</tbody>\n"""
	}

	private writeHeader(View view) {
		HeadBuilder builder = customViewFactory.createHeadBuilder()
		out << builder.build(view)
	}

	private writeJavascript(attrs) {

		out << g.javascript(src:"customview.js")

		out << """<script>\n"""

		out << """
\$(function() {
	var customView = new CustomView({
		name: '${attrs.name}',
		fetchURL: '${g.createLink(controller: "customView", action: "fetch", absolute: "true")}',
		fetchSize: '100' 
	});
});
		""".stripMargin()

		out << """</script>\n"""

	}

}
