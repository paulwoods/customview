package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column])
class BodyBuilderSpec extends Specification {

	def builder
	def view1
	def column1

	def setup() {
		builder = new BodyBuilder()

		view1 = new View(name:"view1").save()
		assert null != view1

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:0).save()
		assert null != column1
	}

	def cleanup() {
	}

	def "value of the column is output"() {
		given:
		def records = [[column1:"abc"]]

		when:
		def html = builder.build(view1, records)

		then:
		"<tr>\n<td>abc</td>\n</tr>\n" == html
	}

	def "null value is converted to blank"() {
		given:
		def records = [[column1:null]]

		when:
		def html = builder.build(view1, records)

		then:
		"<tr>\n<td>&nbsp;</td>\n</tr>\n" == html
	}

	def "date values are formatted yyyy-MM-dd"() {
		given:
		def date = new Date()

		column1.type = "date" 
		def records = [[column1:date]]

		when:
		def html = builder.build(view1, records)

		then:
		"<tr>\n<td>${date.format("yyy-MM-dd")}</td>\n</tr>\n" == html
	}

	def "class is added"() {
		given:

		column1.classBody = "the-class" 
		def records = [[column1:"abc"]]

		when:
		def html = builder.build(view1, records)

		then:
		"""<tr>\n<td class="the-class">abc</td>\n</tr>\n""" == html
	}

	def "process custom template"() {
		given:
		column1.td = """ return "123" """
		def records = [[column1:"abc"]]

		when:
		def html = builder.build(view1, records)

		then:
		"""<tr>\n<td>123</td>\n</tr>\n""" == html
	}

	def "process custom template and use record"() {
		given:
		column1.td = """ return record.column1+":"+record.column1 """
		def records = [[column1:"555"]]

		when:
		def html = builder.build(view1, records)

		then:
		"""<tr>\n<td>555:555</td>\n</tr>\n""" == html
	}

}
