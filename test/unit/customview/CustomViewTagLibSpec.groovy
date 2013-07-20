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

	def "null name throws exception"() {
		given:
		def call = """<specteam:customView />"""

		when:
		def actual = applyTemplate(call)

		then:
		"<div>view not found: null</div>" == actual
	}

	def "invalid name throws exception"() {
		given:
		def call = """<specteam:customView name="wrong-name" />"""

		when:
		def actual = applyTemplate(call)

		then:
		"<div>view not found: wrong-name</div>" == actual
	}

	def "build a three column header"() {
		given:
		View view1 = new View([name:"view1"]).save()
		assert view1

		tagLib.customViewPlugin = [ getCurrentUserId: { -> 1 } ]

		Column column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:0).save()
		assert column1

		Column column2 = new Column(view:view1, name:"column2", sql:"table1.column2", sequence:1).save()
		assert column2

		Column column3 = new Column(view:view1, name:"column3", sql:"table1.column3", sequence:2).save()
		assert column3

		def expected1 = """<table id="view1" class="classview">
			|<caption><a href="http://localhost:8080/customView/customize?name=view1&amp;returnURL=http%3A%2F%2Flocalhost%3A8080%2F">customize</a></caption>
			|<thead>
			|<tr>
			|<th>column1</th>
			|<th>column2</th>
			|<th>column3</th>
			|</tr>
			|</thead>
			|<tbody>
			|</tbody>
			|</table>
			|""".stripMargin()

		def call = """<specteam:customView name="view1"/>"""

		when:
		def actual = applyTemplate(call, [name:"view1"])

		then:
		println actual
		actual.contains(expected1)
	}

}

