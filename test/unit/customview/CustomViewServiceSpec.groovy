package customview

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([View,Column,Table])
class CustomViewServiceSpec extends Specification {

	def setup() {
		service.customViewFactory = new CustomViewFactory()
	}

	def cleanup() {
	}

	def "create view creates a new view"() {
		when:
		def view = service.createView([name:"viewname"])

		then:
		"viewname" == view.name
	}

	def "create view stores the view in the database"() {
		when:
		service.createView([name:"viewname"])

		then:
		1 == View.count()
		"viewname" == View.get(1).name
	}

	def "create view handles exceptions"() {
		when:
		service.createView(null)

		then:
		FailedToCreateViewException e = thrown()
		"Failed to create the view: null" == e.message
	}

	def "create a column"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column = service.createColumn(view, [name:"column1", sql:"table1.column1"])
		assert null != column

		then:
		view == column.view
		"column1" == column.name
		"table1.column1" == column.sql
	}

	def "created column is stored in database"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		service.createColumn(view, [name:"column1", sql:"table1.column1"])

		then:
		1 == Column.count()
		def column = Column.get(1)
		view == column.view
		"column1" == column.name
		"table1.column1" == column.sql
	}

	def "first column has sequence 1"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column = service.createColumn(view, [name:"column1", sql:"table1.column1"])

		then:
		1 == column.sequence
	}

	def "second column has sequence 2"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column1 = service.createColumn(view, [name:"column1", sql:"table1.column1"])
		def column2 = service.createColumn(view, [name:"column2", sql:"table1.column2"])

		then:
		2 == column2.sequence
	}

	def "created column default type is string"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column = service.createColumn(view, [name:"column1", sql:"table1.column1"])
		assert null != column

		then:
		"string" == column.type
	}

	def "column type change be set"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column = service.createColumn(view, [name:"column1", sql:"table1.column1", type:"date"])
		assert null != column

		then:
		"date" == column.type
	}

	def "invalid column types not allowed"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def column = service.createColumn(view, [name:"column1", sql:"table1.column1", type:"--invalid--"])

		then:
		FailedToCreateColumnException e = thrown()
		"Failed to create the column: column1" == e.message
	}

	def "create table created a new table"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		def table1 = service.createTable(view, [name:"table1"])

		then:
		"table1" == table1.name
	}

	def "create table stores the view in the database"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		service.createTable(view, [name:"table1"])
		
		then:
		1 == Table.count()
		"table1" == Table.get(1).name

	}

	def "create table handles exceptions"() {
		given:
		def view = service.createView([name:"viewname"])
		assert null != view

		when:
		service.createTable(view, null)

		then:
		FailedToCreateTableException e = thrown()
		"Failed to create the table: null" == e.message

	}

}

/*Table createTable(View view, Map params) {
	Table table = customViewFactory.createTable()
	table.properties = params
	view.addToTables table

	if(view.save()) {
		table 
	} else {
		log.warn view?.dump()
		log.warn table?.dump()
		throw new FailedToCreateTableException(table)
	}
	
}
Column createOrder(View view, Map params) {
	Order order = customViewFactory.createOrder()
	order.properties = params
	view.addToOrders order

	if(view.save()) {
		order 
	} else {
		log.warn "unable to save the view $view"
		log.warn view?.dump()
		log.warn order?.dump()
		throw new FailedToCreateOrderException(order)
	}
}


*/
