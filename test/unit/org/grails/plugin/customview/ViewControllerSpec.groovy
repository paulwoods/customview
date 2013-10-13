package org.grails.plugin.customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ViewController)
@Mock([View])
class ViewControllerSpec extends Specification {

	def view1

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1
	}

	void "index redirects to list"() {
		when:
		controller.index()

		then:
		"/view/list" == response.redirectedUrl
	}

	void "list returns a list of views"() {
		when:
		def model = controller.list()

		then:
		[view1] == model.views
	}

	void "create returns a new view object"() {
		when:
		def model = controller.create()

		then:
		model.view instanceof View
	}

	void "failed save shows the create view"() {
		given:
		params.name = ""

		when:
		controller.save()

		then:
		"/view/create" == view
		model.view instanceof View
	}

	void "save creates the view"() {
		given:
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.save()

		then:
		2 == View.count()
		def view2 = View.get(2)
		"1" == view2.name
	}

	void "save redirects to show"() {
		given:
		params."view.id" = view1.id
		params.name = "1"

		when:
		controller.save()

		then:
		"/view/show/2" == response.redirectedUrl
	}

	void "show with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.show()

		then:
		"/view/list" == response.redirectedUrl
		"The view was not found." == flash.message
	}

	void "show returns the view"() {
		given:
		params.id = view1.id

		expect:
		[view:view1] == controller.show()
	}

	void "edit with invalid id redirects to list"() {
		given:
		params.id = null

		when:
		controller.edit()

		then:
		"/view/list" == response.redirectedUrl
		"The view was not found." == flash.message
	}

	void "edit returns the view"() {
		given:
		params.id = view1.id

		expect:
		[view:view1] == controller.edit()
	}

	void "failed update shows the edit view"() {
		given:
		params.id = view1.id
		params.name = ""

		when:
		controller.update()

		then:
		"/view/edit" == view
		view1 == model.view
	}

	void "update updates the view"() {
		given:
		params.id = view1.id
		params.name = "1"

		when:
		controller.update()

		then:
		1 == View.count()
		"1" == view1.name
	}

	void "update redirects to show"() {
		given:
		params.id = view1.id
		params.name = "1"

		when:
		controller.update()

		then:
		"/view/show/1" == response.redirectedUrl
	}

	void "delete the view"() {
		given:
		params.id = view1.id

		when:
		controller.delete()

		then:
		0 == View.count()
	}

	void "delete redirects to list"() {
		given:
		params.id = view1.id

		when:
		controller.delete()

		then:
		"/view/list" == response.redirectedUrl
	}

}