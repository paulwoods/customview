package org.grails.plugin.customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Table)
@Mock([View])
class TableSpec extends Specification {

	def view

	def setup() {
		view = new View(name:"name1").save()
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def table = new Table(view:view, name:" title ").save()
		assert null != table

		when:
		table.beforeValidate()

		then:
		view == table.view
		"title" == table.name
	}

	def "toString returns debug text"() {
		given:
		def table = new Table(view:view, name:"title").save()

		expect:
		"Table[1] title" == table.toString()
	}

	def "name can't be null"() {
		expect:
		null == new Table(view:view, name:null).save()
	}

	def "name can't be blank"() {
		expect:
		null == new Table(view:view, name:"").save()
	}

	def "view can't be null"() {
		expect:
		null == new Table(view:null, name:"title").save()
	}

}
