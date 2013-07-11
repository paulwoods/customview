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

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1
	}

	def "invalid view returns empty result"() {
		when:
		def result = service.fetch(null, 0, 0, null)

		then:
		0 == result.offset
		"" == result.html
		false == result.moreData
	}

	def "fetch records"() {
		given:
		Result.metaClass.fetchRecords = { -> 
			delegate.records = [[a:1],[a:2]]
			delegate.offset = 2 
		}

		Result.metaClass.createHTML = { -> delegate.html = "<html></html>"}

		when:
		def result = service.fetch(view1, 111, 123456, null)

		then:
		2 == result.offset
		"<html></html>" == result.html
		false == result.moreData
		[[a:1],[a:2]] == result.records
	}

}

