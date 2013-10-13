package org.grails.plugin.customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View])
class RunnerSpec extends Specification {

	Runner runner

	def setup() {
		runner = new Runner()
	}

	def "run the query"() {
		given:
		def database = [ rows: { String q -> [[a:1],[b:2]] }]

		expect:
		[[a:1],[b:2]] == runner.run("select * from t1", database)
	}

}

