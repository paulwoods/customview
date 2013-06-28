import customview.*

class BootStrap {

	def init = { servletContext ->

		def userId = 1

		View viewPcns = new View(name:"pcns", fetchSize:50)

		int sequence = 0

		viewPcns.addToTables name:"pcn"
		viewPcns.addToColumns name:"Number", sql:"pcn.number", sequence: sequence++, 
			td: ''' 
				"""<a href="https://giant.sc.ti.com/pcn/pcnsys.nsf/PCNNumber/$record.number">$record.number</a>""" 
			'''

		viewPcns.addToColumns name:"Title", sql:"pcn.title", sequence: sequence++
		viewPcns.addToColumns name:"Change Owner", sql:"pcn.pcn_owner", sequence: sequence++
		viewPcns.addToColumns name:"Qual Owner", sql:"pcn.qual_owner", sequence: sequence++
		viewPcns.addToColumns name:"Business Owner", sql:"pcn.business_owner", sequence: sequence++
		viewPcns.addToColumns name:"CMS", sql:"pcn.cms_number", sequence: sequence++
		viewPcns.addToColumns name:"Comment", sql:"pcn.comment", sequence: sequence++
		viewPcns.addToColumns name:"From Site", sql:"pcn.from_site", sequence: sequence++
		viewPcns.addToColumns name:"To Site", sql:"pcn.to_site", sequence: sequence++
		viewPcns.addToColumns name:"Sample Request Window Status", sql:"pcn.status", sequence: sequence++
		viewPcns.addToColumns name:"Program Manager", sql:"pcn.program_manager", sequence: sequence++
		viewPcns.addToColumns name:"Affected Businesses", sql:"pcn.affected_business", sequence: sequence++
		viewPcns.addToColumns name:"Sample Group Name", sql:"pcn.group_name", sequence: sequence++
		viewPcns.addToColumns name:"Description", sql:"pcn.description", sequence: sequence++
		viewPcns.addToColumns name:"Date Publish", sql:"pcn.date_publish", type:"DATE", classHead:"nowrap", sequence: sequence++
		viewPcns.addToColumns name:"Days Expiration", sql:"pcn.days_expiration", type:"NUMBER", classHead:"nowrap", sequence: sequence++
		viewPcns.save()
	}

	def destroy = {
	}

}


