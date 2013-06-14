import customview.*

class BootStrap {

	def customViewService

	def init = { servletContext ->
		build()
	}

	def destroy = {
	}

	void build() {
		View viewPcns = customViewService.createView("pcns")
		viewPcns.createColumn name:"Number", sql:"pcn.number", classHead:"number-head", classBody:"number-body"
		viewPcns.createColumn name:"Title", sql:"pcn.title"

		viewPcns.createColumn name:"Description", sql:"pcn.description"
		viewPcns.createColumn name:"Date Publish", sql:"pcn.date_publish", type:"date", classHead:"nowrap"
		viewPcns.createTable name:"pcn"

		View viewParts = customViewService.createView("parts")
		viewParts.createColumn name:"Part Number", sql:"part.part_number"
		viewParts.createTable name:"part"

		// String html = viewPcns.fetch(50, 0)
	}

}


