import customview.*

class BootStrap {

	def customViewService

	def init = { servletContext ->
		build()
	}

	def destroy = {
	}

	void build() {
		View viewPcns = customViewService.createView(name:"pcns", fetchSize: 1)
		viewPcns.createColumn name:"Number", sql:"pcn.number", classHead:"number-head", classBody:"number-body"
		viewPcns.createColumn name:"Title", sql:"pcn.title"
		viewPcns.createColumn name:"Description", sql:"pcn.description"
		viewPcns.createColumn name:"Date Publish", sql:"pcn.date_publish", type:"date", classHead:"nowrap"
		viewPcns.createTable name:"pcn"
		viewPcns.createOrder name:"pcn.number asc"

		View viewParts = customViewService.createView(name:"parts")
		viewParts.createColumn name:"Part Number", sql:"part.part_number"
		viewParts.createTable name:"part"
		viewParts.createOrder name:"part_number asc"
	}

}


