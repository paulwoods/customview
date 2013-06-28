import customview.*

class BootStrap {

	def init = { servletContext ->

		def userId = 1

		View viewPcns = new View(name:"pcns", fetchSize:50)
		viewPcns.addToTables name:"pcn"
		viewPcns.addToColumns name:"Number", sql:"pcn.number", classHead:"number-head",classBody:"number-body", sequence: 0
		viewPcns.addToColumns name:"Title", sql:"pcn.title", sequence: 1
		viewPcns.addToColumns name:"Description", sql:"pcn.description", sequence: 2
		viewPcns.addToColumns name:"Date Publish", sql:"pcn.date_publish", type:"DATE", classHead:"nowrap", sequence: 3
		viewPcns.addToColumns name:"Days Expiration", sql:"pcn.days_expiration", type:"NUMBER", classHead:"nowrap", sequence: 4
		viewPcns.save(flush:true)
	}

	def destroy = {
	}

}


