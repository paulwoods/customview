package customview

import grails.converters.JSON

class CustomViewController {

	def customViewService

	def fetch(String name, Integer offset) {
		View view = View.findByName(name)
		if(!view)
			render status:500, text: [message:"view not found"] as JSON
		else
			render view.fetch(offset) as JSON
	}

	def customize(String name) {
		View view = View.findByName(name)
		if(!view)
			render status:500, text: "The view was not found: $name"
		else
			[view:view]
	}
	
}

