import customview.*

class BootStrap {

	def customViewService
	def customViewPlugin

	def init = { servletContext ->

		def userId = 1

		View viewPcns = new View(name:"pcns", fetchSize:50)
		viewPcns.addToTables name:"pcn"
		viewPcns.addToColumns name:"Number", sql:"pcn.number", classHead:"number-head",classBody:"number-body", sequence: 0
		viewPcns.addToColumns name:"Title", sql:"pcn.title", sequence: 1
		viewPcns.addToColumns name:"Description", sql:"pcn.description", sequence: 2
		viewPcns.addToColumns name:"Date Publish", sql:"pcn.date_publish", type:"date", classHead:"nowrap", sequence: 3
		viewPcns.save(flush:true)

		// def setting = title.getSetting(userId)
		// setting.sort = "ASC"
		// setting.save()
	}

	def destroy = {
	}

}


