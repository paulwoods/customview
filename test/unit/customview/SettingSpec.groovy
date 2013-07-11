package customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Setting)
@Mock([Column,View,Setting])
class SettingSpec extends Specification {

	def view1
	def column1,column2,column3
	def userId = 123456

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1

		column1 = new Column(view:view1, name:"column1", sql:"table1.column1", sequence:1).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", sql:"table1.column2", sequence:2).save()
		assert column2

		column3 = new Column(view:view1, name:"column3", sql:"table1.column3", sequence:3).save()
		assert column3
	}

	def "test to string"() {
		given:
		def setting1 = new Setting(column:column1, userId:userId, sequence:0).save()
		assert setting1

		expect:
		"Setting[1] $view1 | column1 | $userId | 0" == setting1.toString()
	}

	def "before validate trims strings"() {
		given:
		def setting1 = new Setting(sort: " s ", compare: " c ", value: " v ")

		when:
		setting1.beforeValidate()

		then:
		"s" == setting1.sort
		"c" == setting1.compare
		"v" == setting1.value
	}

	def "clear user sorts delegates to the column"() {
		given:
		def setting1 = new Setting(column:column1, userId:userId, sequence:0).save()
		assert setting1
		
		Boolean called = false
		column1.metaClass.clearUserSorts = { Long u -> called = true }
		
		when:
		setting1.clearUserSorts()

		then:
		called
	}

	def "a setting is created if it doesn't exist"() {
		when:
		def setting1 = Setting.getOrCreateSetting(column1, userId)

		then:
		setting1 instanceof Setting
		column1 == setting1.column
		1 == setting1.sequence
		123456 == setting1.userId
		1 == Setting.count()
	}

	def "an existing setting is returned"() {
		given:
		def setting1 = new Setting(column:column1, userId:userId, sequence:0).save()
		assert setting1
		
		when:
		def setting2 = Setting.getOrCreateSetting(column1, userId)

		then:
		setting2 == setting1
		1 == Setting.count()
	}

	def "exception if unable to create the setting"() {
		when:
		Setting.getOrCreateSetting column1, null

		then:
		def e = thrown(Exception)
		"unable to save the setting." == e.message
	}

	def "get next setting sequence returns 1 if no existing sequences"() {
		expect:
		1 == Setting.getNextSettingSequence(view1, userId)
	}

	def "get next setting returns the highest sequence + 1"() {
		given:
		def setting1 = new Setting(column:column1, userId:userId, sequence:100).save()
		assert setting1

		def setting2 = new Setting(column:column2, userId:userId, sequence:300).save()
		assert setting2

		def setting3 = new Setting(column:column3, userId:userId, sequence:200).save()
		assert setting3

		expect:
		301 == Setting.getNextSettingSequence(view1, userId)
	}

	def "numRows - null value returns 1"() {
		given:
		def setting1 = new Setting(value:null)

		expect:
		1 == setting1.numRows
	}

	def "numRows - blank value returns 1"() {
		given:
		def setting1 = new Setting(value:"")

		expect:
		1 == setting1.numRows
	}

	def "numRows - single row returns 2"() {
		given:
		def setting1 = new Setting(value:"row 1")

		expect:
		2 == setting1.numRows
	}

	def "numRows - two rows returns 3"() {
		given:
		def setting1 = new Setting(value:"row 1\nrow 2")

		expect:
		3 == setting1.numRows
	}

	def "numRows - extra line feeds are respected"() {
		given:
		def setting1 = new Setting(value:"\n row1 \n \n")

		expect:
		4 == setting1.numRows
	}
}

