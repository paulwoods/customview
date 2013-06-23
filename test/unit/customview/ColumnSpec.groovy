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

	def cleanup() {
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def column = new Column(view:view1, name:" title ", sql:" table.title ", sequence:0).save()
		assert null != column

		when:
		column.beforeValidate()

		then:
		"title" == column.name
		"table.title" == column.sql
	}

	def "toString returns debug text"() {
		given:
		def column = new Column(view:view1, name:"column1", sql:"table1.column1", type:"string", sequence:12).save()
		assert null != column

		expect:
		"Column[1] view1 | column1 | table1.column1 | 12 | string" == column.toString()
	}

	def "name can't be null"() {
		expect:
		null == new Column(view:view1, name:null, sql:"table.title", sequence:0).save()
	}

	def "name can't be blank"() {
		expect:
		null == new Column(view:view1, name:"", sql:"table.title", sequence:0).save()
	}

	def "sql can't be null"() {
		expect:
		null == new Column(view:view1, name:"title", sql:null, sequence:0).save()
	}

	def "sql can't be blank"() {
		expect:
		null == new Column(view:view1, name:"title", sql:"", sequence:0).save()
	}

	def "view can't be null"() {
		expect:
		null == new Column(view:null, name:"title", sql:"table.title", sequence:0).save()
	}

	def "sequence can't be null"() {
		expect:
		null == new Column(view:view1, name:"title", sql:"table.title", sequence:null).save()
	}

	def "default type is string"() {
		expect:
		"string" == new Column().type
	}



}
