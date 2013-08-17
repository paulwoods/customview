package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CustomQueryController)
@Mock([View,Setting,Column])
class CustomQueryControllerSpec extends Specification {

	def view1
	def column1
	def setting1

	def setup() {
		controller.session.userid = "paul.woods"

		view1 = new View(name:"view1").save()
		assert null != view1.save()

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:1).save()
		assert null != column1

		setting1 = new Setting(column:column1, userid:1, sequence:1).save()
		assert null != setting1

		controller.metaClass.cache = { }
	}

	def "index redirects to home"() {
		when:
		controller.index()

		then:
		true
	}

	def "fetch with invalid view return status code 500"() {
		given:
		view1.metaClass.fetch = { Integer o -> [a:1]}

		when:
		controller.fetch("missing-view", 0)

		then:
		500 == response.status
		"view not found" == response.json.message
	}

	def "fetch returns a block of data"() {
		given:
		def customViewService = mockFor(CustomViewService)
		customViewService.demand.fetch { View v, Integer o, String u ->
			assert v == view1
			assert o == 111
			new Result(view:view1, offset:123, records:[[a:1]], moreData:true)
		}
		controller.customViewService = customViewService.createMock()

		when:
		controller.fetch "view1", 111
		
		then:
		123 == response.json.offset
		"<tr><td></td></tr>" == response.json.html
		true == response.json.moreData

		cleanup:
		customViewService.verify()
	}

	def "fetch handles exceptions"() {
		given:
		def customViewService = mockFor(CustomViewService)
		customViewService.demand.fetch { View v, Integer o, String u ->
			throw new RuntimeException("Boom!")
		}
		controller.customViewService = customViewService.createMock()

		when:
		controller.fetch("view1", 111)

		then:
		500 == response.status
		"Boom!" == response.json.message

		cleanup:
		customViewService.verify()
	}

	def "customize builds the model"() {
		when:
		def model = controller.customize(view1.name, "www.example.com")
		println model
		then:
		view1 == model.view
		"paul.woods" == model.userid
		"www.example.com" == model.returnURL
	}

	def "customize handles view not found"() {
		when:
		controller.customize("xyz", "www.example.com")

		then:
		500 == response.status
		"The view was not found: xyz" == response.contentAsString
	}

	def "sort handles invalid settingsId"() {
		when:
		controller.sort(null,"")

		then:
		500 == response.status
		"The setting was not found." == response.json.message
	}

	def "sort can be set to ASC"() {
		given:
		setting1.sort = 'DESC'
		assert setting1.save()

		when:
		controller.sort(setting1.id, "ASC")

		then:
		"ASC" == setting1.sort

		and:
		200 == response.status
		setting1.id == response.json.id
		"ASC" == response.json.sort
	}

	def "sort can be set to DESC"() {
		given:
		setting1.sort = 'ASC'
		assert setting1.save()

		when:
		controller.sort(setting1.id, "DESC")

		then:
		"DESC" == setting1.sort

		and:
		200 == response.status
		setting1.id == response.json.id
		"DESC" == response.json.sort
	}

	def "sort can be cleared"() {
		given:
		setting1.sort = 'ASC'
		assert setting1.save()

		when:
		controller.sort(setting1.id, "")

		then:
		"" == setting1.sort

		and:
		200 == response.status
		setting1.id == response.json.id
		"" == response.json.sort
	}

	def "sort handles invalid contents"() {
		given:
		setting1.sort = 'ASC'
		assert setting1.save()

		when:
		controller.sort(setting1.id, "INVALID VALUE")

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

	def "sort calls clearUserSorts"() {
		given:
		boolean called = false
		setting1.metaClass.clearUserSorts = { -> called = true }

		when:
		controller.sort(setting1.id, "ASC")

		then:
		true == called
	}

	def "visible handles invalid settingsId"() {
		when:
		controller.visible(null,false)

		then:
		500 == response.status
		"The setting was not found." == response.json.message
	}

	def "visible can be set to true"() {
		given:
		setting1.visible = false
		assert setting1.save()

		when:
		controller.visible(setting1.id, true)

		then:
		true == setting1.visible

		and:
		200 == response.status
		setting1.id == response.json.id
		true == response.json.visible
	}

	def "visible can be set to false"() {
		given:
		setting1.visible = true
		assert setting1.save()

		when:
		controller.visible(setting1.id, false)

		then:
		false == setting1.visible

		and:
		200 == response.status
		setting1.id == response.json.id
		false == response.json.visible
	}

	def "visible handles invalid contents"() {
		given:
		setting1.visible = true
		assert setting1.save()

		when:
		controller.visible(setting1.id, null)

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

	def "compare handles invalid settingsId"() {
		when:
		controller.compare(null,"")

		then:
		500 == response.status
		"The setting was not found." == response.json.message
	}

	def "compare can be set to ="() {
		given:
		assert setting1.save()

		when:
		controller.compare(setting1.id, "=")

		then:
		"=" == setting1.compare

		and:
		200 == response.status
		setting1.id == response.json.id
		"=" == response.json.compare
	}

	def "compare can be set to in list"() {
		given:
		assert setting1.save()

		when:
		controller.compare(setting1.id, "in list")

		then:
		"in list" == setting1.compare

		and:
		200 == response.status
		setting1.id == response.json.id
		"in list" == response.json.compare
	}

	def "compare handles invalid contents"() {
		given:
		assert setting1.save()

		when:
		controller.compare(setting1.id, "invalid content")

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

	def "content handles invalid settingsId"() {
		when:
		controller.content(null,"")

		then:
		500 == response.status
		"The setting was not found." == response.json.message
	}

	def "content can be set to 123456"() {
		given:
		assert setting1.save()

		when:
		controller.content(setting1.id, "123456")

		then:
		"123456" == setting1.content

		and:
		200 == response.status
		setting1.id == response.json.id
		"123456" == response.json.content
	}

	def "content can be set to blank"() {
		given:
		assert setting1.save()

		when:
		controller.content(setting1.id, "")

		then:
		"" == setting1.content

		and:
		200 == response.status
		setting1.id == response.json.id
		"" == response.json.content
	}

	def "content handles invalid contents"() {
		given:
		assert setting1.save()

		when:
		controller.content(setting1.id, null)

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

	def "reset sets the setting back to the default contents"() {
		given:
		setting1.visible = false
		setting1.sort = Setting.SORTS[1]
		setting1.compare = Setting.COMPARES[1]
		setting1.content = "abc"
		assert setting1.save()

		params.settingId = setting1.id

		when:
		controller.reset()

		then:
		setting1.visible == true
		setting1.sort == Setting.SORTS[0]
		setting1.compare == Setting.COMPARES[0]
		setting1.content == ""

		response.json.visible == true
		response.json.sort == Setting.SORTS[0]
		response.json.compare == Setting.COMPARES[0]
		response.json.content == ""
	}

}
