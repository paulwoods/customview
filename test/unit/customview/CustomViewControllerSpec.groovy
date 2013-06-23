package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CustomViewController)
@Mock([View,Setting,Column])
class CustomViewControllerSpec extends Specification {

	def view1
	def column1
	def setting1

	def setup() {
		controller.customViewPlugin = [
			getCurrentUserId:{ -> 123456 },
			getConnection: { -> null },
		]

		view1 = new View(name:"view1").save()
		assert null != view1.save()

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:1).save()
		assert null != column1

		setting1 = new Setting(column:column1, userId:1, sequence:1).save()
		assert null != setting1
	}

	def cleanup() {
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
		view1.metaClass.fetch = { Integer o, Long u, db -> 
			assert 111 == o
			assert 123456 == u
			[result:"success"]
		}
		
		when:
		controller.fetch("view1", 111)

		then:
		"success" == response.json.result
	}

	def "fetch handles exceptions"() {
		given:
		view1.metaClass.fetch = { Integer o, Long u, db -> 
			throw new RuntimeException("Boom!")
		}
		
		when:
		controller.fetch("view1", 111)

		then:
		500 == response.status
		"Boom!" == response.json.message
	}

	def "customize builds the model"() {
		when:
		def model = controller.customize(view1.name, "www.example.com")
		println model
		then:
		view1 == model.view
		123456 == model.userId
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

	def "sort handles invalid values"() {
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

	def "visible handles invalid values"() {
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

	def "compare handles invalid values"() {
		given:
		assert setting1.save()

		when:
		controller.compare(setting1.id, "invalid value")

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

	def "value handles invalid settingsId"() {
		when:
		controller.value(null,"")

		then:
		500 == response.status
		"The setting was not found." == response.json.message
	}

	def "value can be set to 123456"() {
		given:
		assert setting1.save()

		when:
		controller.value(setting1.id, "123456")

		then:
		"123456" == setting1.value

		and:
		200 == response.status
		setting1.id == response.json.id
		"123456" == response.json.value
	}

	def "value can be set to blank"() {
		given:
		assert setting1.save()

		when:
		controller.value(setting1.id, "")

		then:
		"" == setting1.value

		and:
		200 == response.status
		setting1.id == response.json.id
		"" == response.json.value
	}

	def "value handles invalid values"() {
		given:
		assert setting1.save()

		when:
		controller.value(setting1.id, null)

		then:
		500 == response.status
		"Unable to save the setting." == response.json.message
	}

}
