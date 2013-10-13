package org.grails.plugin.customview

class CustomViewTagLib {

//	static namespace = "specteam"

	HeadBuilder headBuilder = new HeadBuilder()

	def customView = { attrs, body ->
		
		View view = View.findByName(attrs.name)
		if(!view) {
			log.warn "view not found: $attrs.name"
			out << "<div>view not found: $attrs.name</div>"
			return 
		}

		String userid = session.userid

		openTable attrs
		caption view
		openHead()
		writeHeader view, userid
		closeHead()
		openBody()
		closeBody()
		closeTable()
		writeWaiting()
		writeJavascript attrs
	}

	private openTable(attrs) {
		out << """<table id="$attrs.name" class="classview">\n"""
	}

	private closeTable() {
		out << "</table>\n"
	}

	private caption(View view) {

		if(session.userid) {
			String returnURL = g.createLink(
				controller:controllerName, 
				action:actionName, 
				params:params, 
				absolute:true)

			String link = g.link(
				controller:"customQuery", 
				action:"customize", 
				params:[name:view.name, returnURL:returnURL], 
				absolute:true) { "customize" }

			out << "<caption>$link</caption>\n"
		} else {
			// out << "<caption>login to customize the view</caption>\n"
		}
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

	private writeHeader(View view, String userid) {
		out << headBuilder.build(view, userid)
	}

	private writeWaiting() {
		def url = g.resource(dir:"images", file:"spinner.gif")
		out << """<div class="text-center"><img id="waiting" src="$url" alt="spinner" /></div>"""
	}

	private writeJavascript(attrs) {
		out << g.javascript(src:"customview.js")

		out << """
<script>
\$(function() {
	var customView = new CustomView({
		name: '${attrs.name}',
		fetchURL: '${g.createLink(controller: "customQuery", action: "fetch", absolute: "true")}'
	});
});
</script>
		"""

	}

}
