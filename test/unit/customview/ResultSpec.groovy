package customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View])
class ResultSpec extends Specification {

	Result result
	def view1
	def queryBuilder = Mock(QueryBuilder)
	def sqlBuilder = Mock(SqlBuilder)
	def runner = Mock(Runner)
	def query = new Query()
	def database = [:]

	def offset = 111
	def userId = 123456

	def setup() {
		result = new Result()
		view1 = new View(name:"view1").save()
		assert view1

		result.queryBuilder = queryBuilder
		result.sqlBuilder = sqlBuilder
		result.runner = runner
		result.database = database
		result.offset = offset
		result.userId = userId
	}

	def "if view is null, no records are fetched"() {
		given:
		result.view = null

		when:
		result.fetchRecords()

		then:
		[] == result.records
	}

	def "fetch records"() {
		given:
		result.view = view1

		when:
		result.fetchRecords()

		then:
		1 * queryBuilder.build(view1, offset, userId) >> query
		1 * sqlBuilder.build(query) >> "select * from table1"
		1 * runner.run("select * from table1", database) >> [[a:1],[a:2]]

		query == result.query
		"select * from table1" == result.sql
		[[a:1],[a:2]] == result.records
		113 == result.offset
		true == result.moreData
	}

	def "if records were returned more data is true"() {
		given:
		result.view = view1

		when:
		result.fetchRecords()

		then:
		1 * queryBuilder.build(view1, offset, userId) >> query
		1 * sqlBuilder.build(query) >> "select * from table1"
		1 * runner.run("select * from table1", database) >> [[a:1],[a:2]]

		true == result.moreData
	}

	def "if no records were returned more data is false"() {
		given:
		result.view = view1

		when:
		result.fetchRecords()

		then:
		1 * queryBuilder.build(view1, offset, userId) >> query
		1 * sqlBuilder.build(query) >> "select * from table1"
		1 * runner.run("select * from table1", database) >> []

		false == result.moreData
	}

	def "offset is incremented by the number of records returned"() {
		given:
		result.view = view1

		when:
		result.fetchRecords()

		then:
		1 * queryBuilder.build(view1, offset, userId) >> query
		1 * sqlBuilder.build(query) >> "select * from table1"
		1 * runner.run("select * from table1", database) >> [[a:11],[a:22],[a:33],[a:44]]

		offset+4 == result.offset
	}

	def "records are stored in the result"() {
		given:
		result.view = view1

		when:
		result.fetchRecords()

		then:
		1 * queryBuilder.build(view1, offset, userId) >> query
		1 * sqlBuilder.build(query) >> "select * from table1"
		1 * runner.run("select * from table1", database) >> [[a:11],[a:22],[a:33],[a:44]]

		[[a:11],[a:22],[a:33],[a:44]] == result.records
	}

	def "create html from the result"() {
		given:
		def bodyBuilder = Mock(BodyBuilder)
		result.bodyBuilder = bodyBuilder
		result.view = view1
		result.records = [[a:1],[a:2]]

		when:
		result.createHTML()

		then:
		1 * bodyBuilder.build(view1, [[a:1],[a:2]], userId) >> "<div></div>"
		"<div></div>" == result.html
	}

}

