package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(CustomViewTagLib)
@Mock([View,Column,Table])
class CustomViewTagLibSpec extends Specification {

	def customViewService

	def setup() {
		customViewService = new CustomViewService()
		customViewService.customViewFactory = new CustomViewFactory()
		tagLib.customViewFactory = new CustomViewFactory()
	}

	def cleanup() {
	}

	def "null name throws exception"() {
		given:
		def call = """<specteam:customView />"""

		when:
		def actual = applyTemplate(call)

		then:
		Exception e = thrown()
		"Error executing tag <specteam:customView>: invalid custom view. name=null controller=null action=null" == e.message
	}

	def "invalid name throws exception"() {
		given:
		def call = """<specteam:customView name="wrong-name" />"""

		when:
		def actual = applyTemplate(call)

		then:
		Exception e = thrown()
		"Error executing tag <specteam:customView>: invalid custom view. name=wrong-name controller=null action=null" == e.message
	}

	def "build a three column header"() {
		given:
		View viewPcns = customViewService.createView("pcns")
		assert null != viewPcns
		viewPcns.customViewService = customViewService
		
		Column columnNumber = viewPcns.createColumn([name:"Number", sql:"pcn.number"])
		assert null != columnNumber

		Column columnTitle = viewPcns.createColumn([name:"Title", sql:"pcn.title"])
		assert null != columnTitle

		Column columnDescription = viewPcns.createColumn([name:"Description", sql:"pcn.description"])
		assert null != columnDescription

		def expected1 = """<table id="pcns" class="classview">
			|<caption>There are x records.</caption>
			|<thead>
			|<tr>
			|<th>Number</th>
			|<th>Title</th>
			|<th>Description</th>
			|</tr>
			|</thead>
			|<tbody>
			|</tbody>
			|</table>
			|""".stripMargin()

		def call = """<specteam:customView name="pcns"/>"""

		when:
		def actual = applyTemplate(call, [name:"pcns"])

		then:
		actual.contains(expected1)
	}

}

