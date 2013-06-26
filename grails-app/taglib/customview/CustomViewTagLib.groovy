package customview

class CustomViewTagLib {

	def customViewPlugin

	HeadBuilder headBuilder = new HeadBuilder()

	static namespace = "specteam"

	def customView = { attrs, body ->
		View view = View.findByName(attrs.name)
		if(!view) {
			log.warn "view not found: $attrs.name"
			out << "<div>view not found: $attrs.name</div>"
			return 
		}

		Long userId = customViewPlugin.getCurrentUserId()

		openTable attrs
		caption view
		openHead()
		writeHeader view, userId
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
		String returnURL = g.createLink(
			controller:controllerName, 
			action:actionName, 
			params:params, 
			absolute:true)

		String link = g.createLink(
			controller:"customView", 
			action:"customize", 
			params:[name:view.name, returnURL:returnURL], 
			absolute:true)

		out << """<caption><a href="$link">Customize</a></caption>\n"""
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

	private writeHeader(View view, Long userId) {
		out << headBuilder.build(view, userId)
	}

	private writeJavascript(attrs) {

		out << g.javascript(src:"customview.js")

		out << """
<script>
\$(function() {
	var customView = new CustomView({
		name: '${attrs.name}',
		fetchURL: '${g.createLink(controller: "customView", action: "fetch", absolute: "true")}'
	});
});
</script>
		"""

	}

}
