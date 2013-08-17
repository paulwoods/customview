package customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting])
class BodyBuilderSpec extends Specification {

	BodyBuilder builder
	View view1
	Column column1
	Setting setting1

	def setup() {
		builder = new BodyBuilder()

		view1 = new View(name:"view1", fetchSize:50).save()
		assert view1
		
		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence: 0, type:"STRING").save()
		assert column1

		setting1 = new Setting(column:column1, userid: "paul.woods", sequence: 0).save()
		assert setting1
	}

	void assertBody(View view, List records, String expected) {
		def actual = builder.build(view, records, "paul.woods")
		assert expected == actual
	}

	def "null view returns blank html"() {
		expect:
		assertBody null, [[x:1]], ""
	}

	def "null records returns blank html"() {
		expect:
		assertBody view1, null, ""
	}

	def "empty records returns blank html"() {
		expect:
		assertBody view1, [], ""
	}

	def "single column records returns the columns text"() {
		expect:
		assertBody view1, [[column1:"abc"]], "<tr><td>abc</td></tr>"
	}

	def "two rows returns two table rows"() {
		expect:
		assertBody view1, [[column1:"abc"],[column1:"def"]], "<tr><td>abc</td></tr><tr><td>def</td></tr>"
	}

	def "null values changed to empty strings"() {
		expect:
		assertBody view1, [[column1:null]], "<tr><td></td></tr>"
	}

	def "date values changed to yyyy-MM-dd"() {
		given:
		column1.type = "DATE"
		assert column1.save()

		Date date = Date.parse("yyyy-MM-dd", "2010-03-20")

		expect:
		assertBody view1, [[column1:date]], "<tr><td>2010-03-20</td></tr>"
	}

	def "the class is added"() {
		given:
		column1.classBody = "the-class" 

		expect:
		assertBody view1, [[column1:"abc"]], """<tr><td class="the-class">abc</td></tr>"""
	}

	def "process a td template"() {
		given:
		column1.td = "return '1' + '2' + '3'"
		assert column1.save()
		
		expect:
		assertBody view1, [[column1:"abc"]], """<tr><td>123</td></tr>"""
	}

	def "process a td template with record"() {
		given:
		column1.td = "return record.column1 + record.column1"
		assert column1.save()
		
		expect:
		assertBody view1, [[column1:"abc"]], """<tr><td>abcabc</td></tr>"""
	}

	def "process a td template with record and column"() {
		given:
		column1.td = "return record.column1 + column.type"
		assert column1.save()
		
		expect:
		assertBody view1, [[column1:"abc"]], """<tr><td>abcSTRING</td></tr>"""
	}

	def "td template exception return #error"() {
		given:
		column1.td = "throw new RuntimeException('boom!')"
		assert column1.save()
		
		expect:
		assertBody view1, [[column1:"abc"]], """<tr><td>#error</td></tr>"""
	}

	def "if column is hidden, its not output"() {
		given:
		setting1.visible = false
		assert setting1.save()
		
		expect:
		assertBody view1, [[column1:"abc"]], """<tr></tr>"""
	}


}



