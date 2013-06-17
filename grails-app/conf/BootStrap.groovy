import customview.*

class BootStrap {

	def customViewService
	def customViewPlugin

	def init = { servletContext ->

		def userId = 1

		View viewPcns = customViewService.createView(name:"pcns", fetchSize: 50)
		Column number = viewPcns.createColumn name:"Number", sql:"pcn.number", classHead:"number-head",classBody:"number-body"
		Column title = viewPcns.createColumn name:"Title", sql:"pcn.title"
		viewPcns.createColumn name:"Description", sql:"pcn.description"
		viewPcns.createColumn name:"Date Publish", sql:"pcn.date_publish", type:"date", classHead:"nowrap"
		viewPcns.createTable name:"pcn"
		
		def setting = title.getSetting(userId)
		setting.sort = "ASC"
		setting.save()
	}

	def destroy = {
	}

}


