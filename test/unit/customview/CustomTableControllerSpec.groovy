package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CustomTableController)
@Mock([View,Table])
class CustomTableControllerSpec extends Specification {

	def view1
	def table1

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1

		table1 = new Table(view:view1, name:"table1").save()
		assert null != table1
	}

	void "index redirects to list"() {
		when:
		controller.index()

		then:
		"/customTable/list" == response.redirectedUrl
	}

	void "list returns a list of tables"() {
		when:
		def model = controller.list()

		then:
		[table1] == model.tables
	}

	void "create returns a new table object"() {
		when:
		def model = controller.create()

		then:
		model.table instanceof Table
	}

	void "failed save shows the create view"() {
		given:
		params.name = "table1"

		when:
		controller.save()

		then:
		"/customTable/create" == view
		model.table instanceof Table
	}

	void "save creates the table"() {
		given:
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.save()

		then:
		2 == Table.count()
		def table2 = Table.get(2)
		"1" == table2.name
	}

	void "save redirects to show"() {
		given:
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.save()

		then:
		"/customTable/show/2" == response.redirectedUrl
	}

	void "show with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.show()

		then:
		"/customTable/list" == response.redirectedUrl
		"The table was not found." == flash.message
	}

	void "show returns the table"() {
		given:
		params.id = table1.id

		expect:
		[table:table1] == controller.show()
	}

	void "edit with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.edit()

		then:
		"/customTable/list" == response.redirectedUrl
		"The table was not found." == flash.message
	}

	void "edit returns the table"() {
		given:
		params.id = table1.id

		expect:
		[table:table1] == controller.edit()
	}

	void "failed update shows the edit view"() {
		given:
		params.id = table1.id
		params.name = ""

		when:
		controller.update()

		then:
		"/customTable/edit" == view
		table1 == model.table
	}

	void "update updates the table"() {
		given:
		params.id = table1.id
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.update()

		then:
		1 == Table.count()
		"1" == table1.name
	}

	void "update redirects to show"() {
		given:
		params.id = table1.id
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.update()

		then:
		"/customTable/show/1" == response.redirectedUrl
	}

	void "delete the table"() {
		given:
		params.id = table1.id

		when:
		controller.delete()

		then:
		0 == Table.count()
	}

	void "delete redirects to list"() {
		given:
		params.id = table1.id

		when:
		controller.delete()

		then:
		"/customTable/list" == response.redirectedUrl
	}

}