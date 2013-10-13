package org.grails.plugin.customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ColumnController)
@Mock([View,Column])
class ColumnControllerSpec extends Specification {

	def view1
	def column1

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:0).save()
		assert null != column1
	}

	void "index redirects to list"() {
		when:
		controller.index()

		then:
		"/column/list" == response.redirectedUrl
	}

	void "list returns a list of columns"() {
		when:
		def model = controller.list()

		then:
		[column1] == model.columns
	}

	void "create returns a new column object"() {
		when:
		def model = controller.create()

		then:
		model.column instanceof Column
	}

	void "failed save shows the create view"() {
		given:
		params.name = ""

		when:
		controller.save()

		then:
		"/column/create" == view
		model.column instanceof Column
	}

	void "save creates the column"() {
		given:
		params."view.id" = view1.id
		params.name = "1"
		params.code = "2"
		params.sequence = 3
		params.type = "STRING"
		params.classHead = "4"
		params.classBody = "5"
		params.td = "6"
		params.th = "7"

		when:
		controller.save()

		then:
		2 == Column.count()
		def column2 = Column.get(2)
		"1" == column2.name
		"2" == column2.code
		3 == column2.sequence
		"STRING" == column2.type
		"4" == column2.classHead
		"5" == column2.classBody
		"6" == column2.td
		"7" == column2.th
	}

	void "save redirects to show"() {
		given:
		params."view.id" = view1.id
		params.name = "1"
		params.code = "2"
		params.sequence = 3
		params.type = "STRING"
		params.classHead = "4"
		params.classBody = "5"
		params.td = "6"
		params.th = "7"

		when:
		controller.save()

		then:
		"/column/show/2" == response.redirectedUrl
	}

	void "show with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.show()

		then:
		"/column/list" == response.redirectedUrl
		"The column was not found." == flash.message
	}

	void "show returns the column"() {
		given:
		params.id = column1.id

		expect:
		[column:column1] == controller.show()
	}

	void "edit with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.edit()

		then:
		"/column/list" == response.redirectedUrl
		"The column was not found." == flash.message
	}

	void "edit returns the column"() {
		given:
		params.id = column1.id

		expect:
		[column:column1] == controller.edit()
	}

	void "failed update shows the edit view"() {
		given:
		params.id = column1.id
		params.type = "x"

		when:
		controller.update()

		then:
		"/column/edit" == view
		column1 == model.column
	}

	void "update updates the column"() {
		given:
		params.id = column1.id
		params."view.id" = view1.id
		params.name = "1"
		params.code = "2"
		params.sequence = 3
		params.type = "STRING"
		params.classHead = "4"
		params.classBody = "5"
		params.td = "6"
		params.th = "7"

		when:
		controller.update()

		then:
		1 == Column.count()
		"1" == column1.name
		"2" == column1.code
		3 == column1.sequence
		"STRING" == column1.type
		"4" == column1.classHead
		"5" == column1.classBody
		"6" == column1.td
		"7" == column1.th
	}

	void "update redirects to show"() {
		given:
		params.id = column1.id
		params."view.id" = view1.id
		params.name = "1"
		params.code = "2"
		params.sequence = 3
		params.type = "STRING"
		params.classHead = "4"
		params.classBody = "5"
		params.td = "6"
		params.th = "7"

		when:
		controller.update()

		then:
		"/column/show/1" == response.redirectedUrl
	}

	void "delete the column"() {
		given:
		params.id = column1.id

		when:
		controller.delete()

		then:
		0 == Column.count()
	}

	void "delete redirects to list"() {
		given:
		params.id = column1.id

		when:
		controller.delete()

		then:
		"/column/list" == response.redirectedUrl
	}

}

