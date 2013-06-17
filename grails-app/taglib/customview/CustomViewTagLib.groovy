package customview

class CustomViewTagLib {

	def customViewFactory

	static namespace = "specteam"

	def customView = { attrs, body ->
		View view = View.findByName(attrs.name)
		if(!view)
			throw new IdInvalidException(this, attrs)

		openTable attrs
		caption view
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

	private caption(View view) {
		String link = g.createLink(
			controller:"customView", 
			action:"customize", 
			params:[name:view.name], 
			absolute:true)

		out << """<caption><a href="$link">customize this view</a></caption>\n"""
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
		fetchURL: '${g.createLink(controller: "customView", action: "fetch", absolute: "true")}'
	});
});
		""".stripMargin()

		out << """</script>\n"""

	}

}
