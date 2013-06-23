package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(View)
class ViewSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def view = new View(name:" name1 ").save()
		assert null != view

		when:
		view.beforeValidate()

		then:
		"name1" == view.name
	}

	def "toString returns debug text"() {
		given:
		def view = new View(name:"name1").save()
		assert null != view

		expect:
		"View[1] name1" == view.toString()
	}

	def "name can't be null"() {
		expect:
		null == new View(name:null).save()
	}

	def "name can't be blank"() {
		expect:
		null == new View(name:"").save()
	}


}

