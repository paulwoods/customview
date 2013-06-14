package customview

import grails.converters.JSON

class CustomViewController {

	def customViewService

	def fetch(String name, Integer fetchSize, Integer offset) {
		View view = View.findByName(name)
		if(!view)
			render status:500, text: [message:"view not found"] as JSON
		 else
			render view.fetch(fetchSize, offset) as JSON
	}
	
}

