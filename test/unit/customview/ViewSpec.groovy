package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(View)
@Mock([View,Column,Setting])
class ViewSpec extends Specification {

	def userId = 123456
	def view1
	def column1, column2
	def setting1, setting2

	def setup() {
		view1 = new View(name:"view1", fetchSize:333).save()
		assert view1

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:0).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", sql:"table2.column2", sequence:1).save()
		assert column2

		setting1 = new Setting(column:column1, userId:userId, sequence:0).save()
		assert setting1

		setting2 = new Setting(column:column2, userId:userId, sequence:1).save()
		assert setting2
	}

	def "strings are trimmed in beforeValidate"() {
		given:
		def view = new View(name:" name1 ").save()
		assert null != view

		when:
		view.beforeValidate()

		then:
		"name1" == view.name
	}

	def "toString returns debug text"() {
		expect:
		"View[1] view1" == view1.toString()
	}

	def "name can't be null"() {
		expect:
		null == new View(name:null).save()
	}

	def "name can't be blank"() {
		expect:
		null == new View(name:"").save()
	}

	def "if no setting has sort, return null"() {
		expect:
		null == view1.getSortSetting(userId)
	}

	def "get the setting that has a sort set"() {
		given:
		setting1.sort = "ASC"
		assert setting1.save()

		expect:
		setting1 == view1.getSortSetting(userId)
	}

	def "get the setting that has a sort set 2"() {
		given:
		setting2.sort = "ASC"
		assert setting2.save()

		expect:
		setting2 == view1.getSortSetting(userId)
	}

	def "only get sort setting for this user"() {
		given:
		setting2.sort = "ASC"
		setting2.userId = 99999
		assert setting2.save()

		expect:
		null == view1.getSortSetting(userId)
	}

	def "the users sorts are cleared"() {
		given:
		setting2.sort = "ASC"
		assert setting2.save()

		when:
		view1.clearUserSorts userId

		then:
		"" == setting1.sort
		"" == setting2.sort
	}

	def "fetch is sent to the service"() {
		given:
		def service = Mock(CustomViewService)
		view1.customViewService = service

		when:
		view1.fetch(1, 123456, "db")

		then:
		1 * service.fetch(view1, 1, 123456, "db") >> [:]
	}

}

