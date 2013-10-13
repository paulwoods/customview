package org.grails.plugin.customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class SqlBuilderSpec extends Specification {

	SqlBuilder builder
	Query query

	def setup() {
		builder = new SqlBuilder()
		query = new Query()
	}

	Boolean assertBuild(String expected) {
		assert expected == builder.build(query)
		true
	}

	def "build with no selects returns empty string"() {
		expect:
		assertBuild ""
	}

	def "build with no froms returns empty string"() {
		given:
		query.addSelect 'table1.column1 "the_name"'

		expect:
		assertBuild ""
	}

	def "build with select and from buils query"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'

		expect:
		assertBuild """select distinct abc.id from t1"""
	}

	def "multiple selects are comma separated"() {
		given:
		query.addSelect 'abc.id'
		query.addSelect 'def.id'
		query.addFrom 't1'

		expect:
		assertBuild """select distinct abc.id, def.id from t1"""
	}

	def "multiple froms are comma separated"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.addFrom 't2'

		expect:
		assertBuild """select distinct abc.id from t1, t2"""
	}

	def "where clauses are added"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.addWhere "abc.id = 123"
		
		expect:
		assertBuild """select distinct abc.id from t1 where abc.id = 123"""
	}

	def "multiple where clauses are separated by and"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.addWhere "abc.id = 123"
		query.addWhere "abc.name = 'alpha'"

		expect:
		assertBuild """select distinct abc.id from t1 where abc.id = 123 and abc.name = 'alpha'"""
	}

	def "order is added"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.addOrder 'abc.id ASC'

		expect:
		assertBuild """select distinct abc.id from t1 order by abc.id ASC"""
	}

	def "multiple orders are comma separated"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.addOrder 'abc.id ASC'
		query.addOrder 'abc.name DESC'

		expect:
		assertBuild """select distinct abc.id from t1 order by abc.id ASC, abc.name DESC"""
	}

	def "count is added"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.count = 100

		expect:
		assertBuild """select distinct abc.id from t1 limit 100"""
	}

	def "offset is added"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.offset = 100

		expect:
		assertBuild """select distinct abc.id from t1 limit 18446744073709551615 offset 100"""
	}

	def "offset and count are added"() {
		given:
		query.addSelect 'abc.id'
		query.addFrom 't1'
		query.offset = 100
		query.count = 200

		expect:
		assertBuild """select distinct abc.id from t1 limit 200 offset 100"""
	}

}

