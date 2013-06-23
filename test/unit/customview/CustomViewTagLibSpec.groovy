package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(CustomViewTagLib)
@Mock([View,Column,Table,Setting])
class CustomViewTagLibSpec extends Specification {

	def customViewService

	def setup() {
		customViewService = new CustomViewService()
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
		View viewPcns = new View([name:"pcns"]).save()
		assert viewPcns
		viewPcns.customViewService = customViewService

		tagLib.customViewPlugin = [ getCurrentUserId: { -> 1 } ]

		Column columnNumber = new Column(view:viewPcns, name:"Number", sql:"pcn.number", sequence:0).save()
		assert columnNumber

		Column columnTitle = new Column(view:viewPcns, name:"Title", sql:"pcn.title", sequence:1).save()
		assert columnTitle

		Column columnDescription = new Column(view:viewPcns, name:"Description", sql:"pcn.description", sequence:2).save()
		assert columnDescription

		def expected1 = """<table id="pcns" class="classview">
			|<caption><a href="http://localhost:8080/customView/customize?name=pcns&returnURL=http%3A%2F%2Flocalhost%3A8080%2F">Customize</a></caption>
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

