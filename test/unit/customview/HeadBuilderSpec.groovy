package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column])
class HeadBuilderSpec extends Specification {

	def builder
	def view1
	def column1

	def setup() {
		builder = new HeadBuilder()

		view1 = new View(name:"view1").save()
		assert null != view1

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:0).save()
		assert null != column1
	}

	def cleanup() {
	}

	def "value of the column is output"() {
		when:
		def html = builder.build(view1)

		then:
		"<tr>\n<th>column1</th>\n</tr>\n" == html
	}

	def "class is added"() {
		given:
		column1.classHead = "head-class"

		when:
		def html = builder.build(view1)

		then:
		"""<tr>\n<th class="head-class">column1</th>\n</tr>\n""" == html
	}

}
