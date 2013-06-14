package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Column)
@Mock([View])
class ColumnSpec extends Specification {

	def view

	def setup() {
		view = new View(name:"name1").save()
	}

	def cleanup() {
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def column = new Column(view:view, name:" title ", sql:" table.title ", sequence:0).save()
		assert null != column

		when:
		column.beforeValidate()

		then:
		view == column.view
		"title" == column.name
		"table.title" == column.sql
	}

	def "toString returns debug text"() {
		given:
		def column = new Column(view:view, name:"title", sql:"table.title", sequence:12).save()
		assert null != column

		expect:
		"Column[1] title | table.title | 12" == column.toString()
	}

	def "name can't be null"() {
		expect:
		null == new Column(view:view, name:null, sql:"table.title", sequence:0).save()
	}

	def "name can't be blank"() {
		expect:
		null == new Column(view:view, name:"", sql:"table.title", sequence:0).save()
	}

	def "sql can't be null"() {
		expect:
		null == new Column(view:view, name:"title", sql:null, sequence:0).save()
	}

	def "sql can't be blank"() {
		expect:
		null == new Column(view:view, name:"title", sql:"", sequence:0).save()
	}

	def "view can't be null"() {
		expect:
		null == new Column(view:null, name:"title", sql:"table.title", sequence:0).save()
	}

	def "sequence can't be null"() {
		expect:
		null == new Column(view:view, name:"title", sql:"table.title", sequence:null).save()
	}

}
