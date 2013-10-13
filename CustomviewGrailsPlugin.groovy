class CustomviewGrailsPlugin {
	def groupId = "org.grails.plugins"
	def version = "0.2"
	def grailsVersion = "2.0 > *"

	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	def title = "Customview Plugin"
	def author = "Paul Woods"
	def authorEmail = "mr.paul.woods@gmail.com"
	def description = '''\
		Brief summary/description of the plugin.
	'''

	def documentation = "http://grails.org/plugin/customview"

    def license = "APACHE"

    def issueManagement = [ system: "GitHub", url: "http://github.com/paulwoods/customview/issues" ]

    def scm = [ url: "http://github.com/paulwoods/customview" ]

	def doWithWebDescriptor = { xml ->
	}

	def doWithSpring = {
	}

	def doWithDynamicMethods = { ctx ->
	}

	def doWithApplicationContext = { applicationContext ->
	}

	def onChange = { event ->
	}

	def onConfigChange = { event ->
	}

	def onShutdown = { event ->
	}

}
