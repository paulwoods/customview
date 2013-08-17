package customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting])
class HeadBuilderSpec extends Specification {

	def builder
	def view1
	def column1
	def setting1

	def setup() {
		builder = new HeadBuilder()

		view1 = new View(name:"view1").save()
		assert view1

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		setting1 = new Setting(column:column1, userid:"paul.woods", sequence:1).save()
		assert setting1
	}

	def "value of the column is output"() {
		when:
		def html = builder.build(view1, "paul.woods")

		then:
		"<tr>\n<th>column1</th>\n</tr>\n" == html
	}

	def "class is added"() {
		given:
		column1.classHead = "head-class"

		when:
		def html = builder.build(view1, "paul.woods")

		then:
		"""<tr>\n<th class="head-class">column1</th>\n</tr>\n""" == html
	}

	def "if setting is hidden, no cell is created"() {
		when:
		setting1.visible = false
		def html = builder.build(view1, "paul.woods")

		then:
		"""<tr>\n</tr>\n""" == html
	}

}
