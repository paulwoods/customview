package org.grails.plugin.customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting,Table])
class QueryBuilderSpec extends Specification {

	QueryBuilder builder
	def userid = "paul.woods"
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

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", code:"table2.column2", sequence:1).save()
		assert column2

		setting1 = new Setting(column:column1, userid:userid, sequence:0).save()
		assert setting1

		setting2 = new Setting(column:column2, userid:userid, sequence:1).save()
		assert setting2
	}

	boolean assertBuilt(String type, String compare, String content, List result) {
		column1.type = type
		assert column1.save()

		setting1.compare = compare
		setting1.content = content
		assert setting1.save()

		def query = builder.build(view1, 0, userid)

		assert result == query.wheres

		true
	}

	def "null view returns a empty query"() {
		when:
		def query = builder.build(null, 0, userid)

		then:
		!query.selects
		!query.froms
		!query.orders
		!query.count
		!query.offset
	}

	def "fetch size is stored in query"() {
		when:
		def query = builder.build(view1, 0, userid)

		then:
		view1.fetchSize == query.count
	}

	def "offset is stored in query"() {
		given:
		def offset = 555

		when:
		def query = builder.build(view1, offset, userid)

		then:
		offset == query.offset
	}

	def "query has the columns in the select"() {
		when:
		def query = builder.build(view1, 0, userid)

		then:
		['table1.column1 "column1"', 'table2.column2 "column2"'] == query.selects
	}

	def "query has the tables in the froms"() {
		when:
		def query = builder.build(view1, 0, userid)

		then:
		['table1','table2'] == query.froms
	}

	def "no setting with sort does not add a order"() {
		when:
		def query = builder.build(view1, 0, userid)

		then:
		[] == query.orders
	}

	def "sort is added"() {
		given:
		setting1.sort = "ASC"
		assert setting1.save()

		when:
		def query = builder.build(view1, 0, userid)

		then:
		["table1.column1 ASC"] == query.orders
	}

	def "descending sort is added"() {
		given:
		setting2.sort = "DESC"
		assert setting2.save()

		when:
		def query = builder.build(view1, 0, userid)

		then:
		["table2.column2 DESC"] == query.orders
	}

	def "test blank compare"() {
		expect:
		assertBuilt "STRING", "", "abc",        []
		assertBuilt "NUMBER", "", "123",        []
		assertBuilt "DATE",   "", "2000-01-02", []
	}

	def "test equals"() {
		expect:
		assertBuilt "STRING", "=", "abc",        	["table1.column1 = 'abc'"]
		assertBuilt "NUMBER", "=", "123",        	["table1.column1 = 123"]
		assertBuilt "DATE",   "=", "2000-01-02", 	["table1.column1 = '2000-01-02'"]

		assertBuilt "STRING", "=", "",        		[]
		assertBuilt "NUMBER", "=", "",       		[]
		assertBuilt "DATE",   "=", "", 				[]
	}

	def "test not equals"() {
		expect:
		assertBuilt "STRING", "<>", "abc",        	["table1.column1 <> 'abc'"]
		assertBuilt "NUMBER", "<>", "123",        	["table1.column1 <> 123"]
		assertBuilt "DATE",   "<>", "2000-01-02", 	["table1.column1 <> '2000-01-02'"]

		assertBuilt "STRING", "<>", "",        		[]
		assertBuilt "NUMBER", "<>", "",       		[]
		assertBuilt "DATE",   "<>", "", 			[]
	}

	def "test less than"() {
		expect:
		assertBuilt "STRING", "<", "abc",        	["table1.column1 < 'abc'"]
		assertBuilt "NUMBER", "<", "123",        	["table1.column1 < 123"]
		assertBuilt "DATE",   "<", "2000-01-02", 	["table1.column1 < '2000-01-02'"]

		assertBuilt "STRING", "<", "",				[]
		assertBuilt "NUMBER", "<", "",				[]
		assertBuilt "DATE",   "<", "",				[]
	}

	def "test greater than"() {
		expect:
		assertBuilt "STRING", ">", "abc",        	["table1.column1 > 'abc'"]
		assertBuilt "NUMBER", ">", "123",        	["table1.column1 > 123"]
		assertBuilt "DATE",   ">", "2000-01-02", 	["table1.column1 > '2000-01-02'"]

		assertBuilt "STRING", ">", "",        		[]
		assertBuilt "NUMBER", ">", "",       		[]
		assertBuilt "DATE",   ">", "", 				[]
	}

	def "test less than or equal to"() {
		expect:
		assertBuilt "STRING", "<=", "abc",        	["table1.column1 <= 'abc'"]
		assertBuilt "NUMBER", "<=", "123",        	["table1.column1 <= 123"]
		assertBuilt "DATE",   "<=", "2000-01-02", 	["table1.column1 <= '2000-01-02'"]

		assertBuilt "STRING", "<=", "",				[]
		assertBuilt "NUMBER", "<=", "",				[]
		assertBuilt "DATE",   "<=", "",				[]
	}

	def "test greater than or equal to"() {
		expect:
		assertBuilt "STRING", ">=", "abc",        	["table1.column1 >= 'abc'"]
		assertBuilt "NUMBER", ">=", "123",        	["table1.column1 >= 123"]
		assertBuilt "DATE",   ">=", "2000-01-02", 	["table1.column1 >= '2000-01-02'"]

		assertBuilt "STRING", ">=", "",        		[]
		assertBuilt "NUMBER", ">=", "",       		[]
		assertBuilt "DATE",   ">=", "", 			[]
	}

	def "test begins with"() {
		expect:
		assertBuilt "STRING", "begins with", "abc",        	["table1.column1 like 'abc%'"]
		assertBuilt "NUMBER", "begins with", "123",        	["table1.column1 like '123%'"]
		assertBuilt "DATE",   "begins with", "2000-01-02", 	["table1.column1 like '2000-01-02%'"]

		assertBuilt "STRING", "begins with", "",        	["table1.column1 like '%'"]
		assertBuilt "NUMBER", "begins with", "",       		["table1.column1 like '%'"]
		assertBuilt "DATE",   "begins with", "", 			["table1.column1 like '%'"]
	}

	def "test not begins with"() {
		expect:
		assertBuilt "STRING", "not begins with", "abc",        	["table1.column1 not like 'abc%'"]
		assertBuilt "NUMBER", "not begins with", "123",        	["table1.column1 not like '123%'"]
		assertBuilt "DATE",   "not begins with", "2000-01-02", 	["table1.column1 not like '2000-01-02%'"]

		assertBuilt "STRING", "not begins with", "",        	["table1.column1 not like '%'"]
		assertBuilt "NUMBER", "not begins with", "",       		["table1.column1 not like '%'"]
		assertBuilt "DATE",   "not begins with", "", 			["table1.column1 not like '%'"]
	}

	def "test ends with"() {
		expect:
		assertBuilt "STRING", "ends with", "abc",        ["table1.column1 like '%abc'"]
		assertBuilt "NUMBER", "ends with", "123",        ["table1.column1 like '%123'"]
		assertBuilt "DATE",   "ends with", "2000-01-02", ["table1.column1 like '%2000-01-02'"]

		assertBuilt "STRING", "ends with", "",        	["table1.column1 like '%'"]
		assertBuilt "NUMBER", "ends with", "",       	["table1.column1 like '%'"]
		assertBuilt "DATE",   "ends with", "", 			["table1.column1 like '%'"]
	}

	def "test not ends with"() {
		expect:
		assertBuilt "STRING", "not ends with", "abc",        ["table1.column1 not like '%abc'"]
		assertBuilt "NUMBER", "not ends with", "123",        ["table1.column1 not like '%123'"]
		assertBuilt "DATE",   "not ends with", "2000-01-02", ["table1.column1 not like '%2000-01-02'"]

		assertBuilt "STRING", "not ends with", "",        	["table1.column1 not like '%'"]
		assertBuilt "NUMBER", "not ends with", "",       	["table1.column1 not like '%'"]
		assertBuilt "DATE",   "not ends with", "", 			["table1.column1 not like '%'"]
	}

	def "test contains"() {
		expect:
		assertBuilt "STRING", "contains", "abc",        	["table1.column1 like '%abc%'"]
		assertBuilt "NUMBER", "contains", "123",        	["table1.column1 like '%123%'"]
		assertBuilt "DATE",   "contains", "2000-01-02", 	["table1.column1 like '%2000-01-02%'"]

		assertBuilt "STRING", "contains", "",        	["table1.column1 like '%%'"]
		assertBuilt "NUMBER", "contains", "",       	["table1.column1 like '%%'"]
		assertBuilt "DATE",   "contains", "", 			["table1.column1 like '%%'"]
	}

	def "test does not contain"() {
		expect:
		assertBuilt "STRING", "does not contain", "abc",        ["table1.column1 not like '%abc%'"]
		assertBuilt "NUMBER", "does not contain", "123",        ["table1.column1 not like '%123%'"]
		assertBuilt "DATE",   "does not contain", "2000-01-02", ["table1.column1 not like '%2000-01-02%'"]

		assertBuilt "STRING", "does not contain", "",        	["table1.column1 not like '%%'"]
		assertBuilt "NUMBER", "does not contain", "",       	["table1.column1 not like '%%'"]
		assertBuilt "DATE",   "does not contain", "", 			["table1.column1 not like '%%'"]
	}

	def "test is null"() {
		expect:
		assertBuilt "STRING", "is null", "abc",        ["table1.column1 is null"]
		assertBuilt "NUMBER", "is null", "123",        ["table1.column1 is null"]
		assertBuilt "DATE",   "is null", "2000-01-02", ["table1.column1 is null"]

		assertBuilt "STRING", "is null", "",        	["table1.column1 is null"]
		assertBuilt "NUMBER", "is null", "",       		["table1.column1 is null"]
		assertBuilt "DATE",   "is null", "", 			["table1.column1 is null"]
	}

	def "test is not null"() {
		expect:
		assertBuilt "STRING", "is not null", "abc",        ["table1.column1 is not null"]
		assertBuilt "NUMBER", "is not null", "123",        ["table1.column1 is not null"]
		assertBuilt "DATE",   "is not null", "2000-01-02", ["table1.column1 is not null"]

		assertBuilt "STRING", "is not null", "",        	["table1.column1 is not null"]
		assertBuilt "NUMBER", "is not null", "",       		["table1.column1 is not null"]
		assertBuilt "DATE",   "is not null", "", 			["table1.column1 is not null"]
	}

	def "test is in list"() {
		expect:
		assertBuilt "STRING", "in list", "\na\nb\nc\n\n",        ["table1.column1 in ('a','b','c')"]
		assertBuilt "NUMBER", "in list", "\n1\n2\n3\n\n",        ["table1.column1 in (1,2,3)"]
		assertBuilt "DATE",   "in list", "\n2000-01-02\n2010-03-04\n\n", ["table1.column1 in ('2000-01-02','2010-03-04')"]

		assertBuilt "STRING", "in list", "",        	[]
		assertBuilt "NUMBER", "in list", "",       		[]
		assertBuilt "DATE",   "in list", "", 			[]
	}

	def "test is not in list"() {
		expect:
		assertBuilt "STRING", "not in list", "\na\nb\nc\n\n",        ["table1.column1 not in ('a','b','c')"]
		assertBuilt "NUMBER", "not in list", "\n1\n2\n3\n\n",        ["table1.column1 not in (1,2,3)"]
		assertBuilt "DATE",   "not in list", "\n2000-01-02\n2010-03-04\n\n", ["table1.column1 not in ('2000-01-02','2010-03-04')"]

		assertBuilt "STRING", "not in list", "",        	[]
		assertBuilt "NUMBER", "not in list", "",       		[]
		assertBuilt "DATE",   "not in list", "", 			[]
	}

	def "if content is not a number, then it is changed to negative 1"() {
		expect:
		assertBuilt "NUMBER", "=", "1\n2\n3\n",        ["table1.column1 = -1"]
	}

}

