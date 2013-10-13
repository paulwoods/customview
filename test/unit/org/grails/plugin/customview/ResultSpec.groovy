package org.grails.plugin.customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View])
class ResultSpec extends Specification {

	Result result
	def view1
	def offset = 111
	def userid = "paul.woods"

	def setup() {
		result = new Result()

		view1 = new View(name:"view1").save()
		assert view1
	}

	def "create html calls bodybuilder"() {
		given:
		def records = [[a:1],[a:2],[a:3]]
		result.view = view1
		result.records = records
		result.userid = userid

		def bodyBuilder = mockFor(BodyBuilder)
		bodyBuilder.demand.build { View v, List r, String u -> 
			assert view1 == v
			assert records == r
			assert userid == u
			"<div></div>"
		}
		result.bodyBuilder = bodyBuilder.createMock()

		when:
		result.createHTML()

		then:
		"<div></div>" == result.html

		cleanup:
		bodyBuilder.verify()
	}

	def "result data can be converted to a map"() {
		given:
		result.offset = 123
		result.html = "<div></div>"
		result.moreData = true

		expect:
		[offset:123, html:"<div></div>", moreData:true] == result.toMap()
	}

}


