package customview

import grails.test.mixin.TestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(CustomViewService)
@Mock([View,Column,Table,Setting])
class CustomViewServiceSpec extends Specification {

	def view1

	def viewRecords = [[a:1],[a:2],[a:3]]

	def viewConnection = [
		rows: { String q -> viewRecords },
		close: { -> }
	]

	def pluginRecords = [[z:11],[z:22],[z:33]]

	def pluginConnection = [
		rows: { String q -> pluginRecords },
		close: { -> }
	]

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1

		def column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:0).save()
		assert null != column1

		view1.metaClass.getConnection = { -> viewConnection }

		service.customViewPlugin = [
			getConnection: { -> pluginConnection },
			getCurrentUserId: { -> 123456L },
		]

	}

	def "if view is null then a exception is thrown"() {
		when:
		service.fetch null, 0

		then:
		CustomViewException e = thrown()
		"The view is null." == e.message
	}

	def "null offset returns empty result"() {
		when:
		service.fetch view1, null

		then:
		CustomViewException e = thrown()
		"The offset is null." == e.message
	}

	def "fetch records puts the view in the result"() {
		when:
		def result = service.fetch(view1, 0)

		then:
		view1 == result.view
	}

	def "fetch records puts the records in the result"() {
		when:
		def result = service.fetch(view1, 0)

		then:
		viewRecords == result.records
	}

	def "fetch records stores the offset plus number of records in the offset"() {
		when:
		def result = service.fetch(view1, 111)

		then:
		114 == result.offset
	}

	def "more data is true if there were records returned"() {
		when:
		def result = service.fetch(view1, 111)

		then:
		result.moreData
	}

	def "more data is false if there were zero records returned"() {
		given:
		viewRecords = []

		when:
		def result = service.fetch(view1, 111)

		then:
		!result.moreData
	}

	def "if no connection from view, use connection from plugin"() {
		given:
		viewConnection = null

		when:
		def result = service.fetch(view1, 111)

		then:
		pluginRecords == result.records
	}

	def "if no connection from view or plugin, exception is thrown"() {
		given:
		viewConnection = null
		pluginConnection = null

		when:
		service.fetch view1, 111

		then:
		CustomViewException e = thrown()
		"Unable to get database connection." == e.message
	}

}

