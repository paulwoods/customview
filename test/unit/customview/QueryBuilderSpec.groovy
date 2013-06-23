package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting,Table])
class QueryBuilderSpec extends Specification {

	QueryBuilder builder
	def userId = 123456
	def view1
	def table1, table2
	def column1, column2
	def setting1, setting2

	def setup() {
		builder = new QueryBuilder()
		view1 = new View(name:"view1", fetchSize:333).save()
		assert view1

		table1 = new Table(view:view1, name:"table1").save()
		assert table1

		table2 = new Table(view:view1, name:"table2").save()
		assert table2

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:0).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", sql:"table2.column2", sequence:1).save()
		assert column2

		setting1 = new Setting(column:column1, userId:userId, sequence:0).save()
		assert setting1

		setting2 = new Setting(column:column2, userId:userId, sequence:1).save()
		assert setting2
	}

	def "null view returns a empty query"() {
		when:
		def query = builder.build(null, 0, userId)

		then:
		!query.selects
		!query.froms
		!query.orders
		!query.fetchSize
		!query.offset
	}

	def "fetch size is stored in query"() {
		when:
		def query = builder.build(view1, 0, userId)

		then:
		view1.fetchSize == query.fetchSize
	}

	def "offset is stored in query"() {
		given:
		def offset = 555

		when:
		def query = builder.build(view1, offset, userId)

		then:
		offset == query.offset
	}

	def "query has the columns in the select"() {
		when:
		def query = builder.build(view1, 0, userId)

		then:
		['table1.column1 "column1"', 'table2.column2 "column2"'] == query.selects
	}

	def "query has the tables in the froms"() {
		when:
		def query = builder.build(view1, 0, userId)

		then:
		['table1','table2'] == query.froms
	}

	def "no setting with sort does not add a order"() {
		when:
		def query = builder.build(view1, 0, userId)

		then:
		[] == query.orders
	}

	def "sort is added"() {
		given:
		setting1.sort = "ASC"
		assert setting1.save()

		when:
		def query = builder.build(view1, 0, userId)

		then:
		["table1.column1 ASC"] == query.orders
	}

	def "descending sort is added"() {
		given:
		setting2.sort = "DESC"
		assert setting2.save()

		when:
		def query = builder.build(view1, 0, userId)

		then:
		["table2.column2 DESC"] == query.orders
	}

	def "add compare settings to the wheres"() {
		given:
		setting1.compare = "="
		setting1.value = "abc"
		assert setting1.save()

		when:
		def query = builder.build(view1, 0, userId)

		then:
		["table1.column1 = 'abc'"] == query.wheres
	}

	def "add compare settings with number columns"() {
		given:
		column1.type = "NUMBER"
		assert column1.save()

		setting1.compare = "="
		setting1.value = "123"
		assert setting1.save()

		when:
		def query = builder.build(view1, 0, userId)

		then:
		["table1.column1 = 123"] == query.wheres
	}

}

