package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CustomViewController)
@Mock([View])
class CustomViewControllerSpec extends Specification {

	def setup() {
		controller.customViewPlugin = [getCurrentUserId:{ -> 1 } ]
	}

	def cleanup() {
	}

	def "fetch returns a block of data"() {
		given:
		View view1 = new View(name:"view1").save()
		assert null != view1

		view1.metaClass.fetch = { Integer o, Long u -> [a:1]}
		when:
		controller.fetch("view1", 0)

		then:
		1 == response.json.a
	}

	def "invalid view return status code 500"() {
		given:
		View view1 = new View(name:"view1").save()
		assert null != view1

		view1.metaClass.fetch = { Integer o -> [a:1]}
		when:
		controller.fetch("missing-view", 0)

		then:
		500 == response.status
		"view not found" == response.json.message
	}
	
}
