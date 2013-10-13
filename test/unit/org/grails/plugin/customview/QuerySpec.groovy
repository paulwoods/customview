package org.grails.plugin.customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting,Table])
class QuerySpec extends Specification {

	Query query

	def setup() {
		query = new Query()
	}

	def "add a select"() {
		when:
		query.addSelect 'table1.column1 "the_name"'

		then:
		['table1.column1 "the_name"'] == query.selects
	}

	def "add a from"() {
		when:
		query.addFrom "table1"

		then:
		["table1"] == query.froms
	}

	def "add a order"() {
		when:
		query.addFrom "table1.column1 ASC"

		then:
		["table1.column1 ASC"] == query.froms
	}

	def "add a where"() {
		when:
		query.addFrom "table1.column1 = 'abc'"

		then:
		["table1.column1 = 'abc'"] == query.froms
	}

}

