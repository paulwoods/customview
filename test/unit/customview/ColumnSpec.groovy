package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Column)
@Mock([View])
class ColumnSpec extends Specification {

	def view1

	def setup() {
		view1 = new View(name:"view1").save()
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def column = new Column(view:view1, name:" title ", code:" table.title ", sequence:0).save()
		assert null != column

		when:
		column.beforeValidate()

		then:
		"title" == column.name
		"table.title" == column.code
	}

	def "toString returns debug text"() {
		given:
		def column = new Column(view:view1, name:"column1", code:"table1.column1", type:"STRING", sequence:12).save()
		assert column

		expect:
		"Column[1] view1 | column1 | table1.column1 | 12 | STRING" == column.toString()
	}

	def "name can't be null"() {
		expect:
		null == new Column(view:view1, name:null, code:"table.title", sequence:0).save()
	}

	def "name can't be blank"() {
		expect:
		null == new Column(view:view1, name:"", code:"table.title", sequence:0).save()
	}

	def "code can't be null"() {
		expect:
		null == new Column(view:view1, name:"title", code:null, sequence:0).save()
	}

	def "code can't be blank"() {
		expect:
		null == new Column(view:view1, name:"title", code:"", sequence:0).save()
	}

	def "view can't be null"() {
		expect:
		null == new Column(view:null, name:"title", code:"table.title", sequence:0).save()
	}

	def "sequence can't be null"() {
		expect:
		null == new Column(view:view1, name:"title", code:"table.title", sequence:null).save()
	}

	def "default type is string"() {
		expect:
		"STRING" == new Column().type
	}

	def "null value returns empty string"() {
		given:
		def column1 = new Column(view:view1, type:"STRING", name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		expect:
		"" == column1.value([column1:null])
	}

	def "string value is returned"() {
		given:
		def column1 = new Column(view:view1, type:"STRING", name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		expect:
		"abc" == column1.value([column1:"abc"])
	}

	def "number value is returned"() {
		given:
		def column1 = new Column(view:view1, type:"STRING", name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		expect:
		"123" == column1.value([column1:123])
	}

	def "zero value is returned"() {
		given:
		def column1 = new Column(view:view1, type:"STRING", name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		expect:
		"0" == column1.value([column1:0])
	}

	def "date value is formatted yyyy-MM-dd"() {
		given:
		def column1 = new Column(view:view1, type:"DATE", name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		def date = Date.parse("yyyy-MM-dd", "2010-01-02")

		expect:
		"2010-01-02" == column1.value(column1: date)
	}

}

