package org.grails.plugin.customview

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
	def userid = "paul.woods"

	def setup() {
		view1 = new View(name:"view1").save()
		assert view1

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:1).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", code:"table1.column2", sequence:2).save()
		assert column2

		column3 = new Column(view:view1, name:"column3", code:"table1.column3", sequence:3).save()
		assert column3
	}

	def "test to string"() {
		given:
		def setting1 = new Setting(column:column1, userid:userid, sequence:0, visible:true).save()
		assert setting1

		expect:
		"Setting[1] $view1 | column1 | $userid | 0 | true" == setting1.toString()
	}

	def "before validate trims strings"() {
		given:
		def setting1 = new Setting(sort: " s ", compare: " c ", content: " v ")

		when:
		setting1.beforeValidate()

		then:
		"s" == setting1.sort
		"c" == setting1.compare
		"v" == setting1.content
	}

	def "clear user sorts delegates to the column"() {
		given:
		def setting1 = new Setting(column:column1, userid:userid, sequence:0).save()
		assert setting1
		
		Boolean called = false
		column1.metaClass.clearUserSorts = { String u -> called = true }
		
		when:
		setting1.clearUserSorts()

		then:
		called
	}

	def "a setting is created if it doesn't exist"() {
		when:
		def setting1 = Setting.getOrCreateSetting(column1, userid)

		then:
		setting1 instanceof Setting
		column1 == setting1.column
		1 == setting1.sequence
		userid == setting1.userid
		1 == Setting.count()
	}

	def "an existing setting is returned"() {
		given:
		def setting1 = new Setting(column:column1, userid:userid, sequence:0).save()
		assert setting1
		
		when:
		def setting2 = Setting.getOrCreateSetting(column1, userid)

		then:
		setting2 == setting1
		1 == Setting.count()
	}

	def "get next setting sequence returns 1 if no existing sequences"() {
		expect:
		1 == Setting.getNextSettingSequence(view1, userid)
	}

	def "get next setting returns the highest sequence + 1"() {
		given:
		def setting1 = new Setting(column:column1, userid:userid, sequence:100).save()
		assert setting1

		def setting2 = new Setting(column:column2, userid:userid, sequence:300).save()
		assert setting2

		def setting3 = new Setting(column:column3, userid:userid, sequence:200).save()
		assert setting3

		expect:
		301 == Setting.getNextSettingSequence(view1, userid)
	}

	def "numRows - null content returns 1"() {
		given:
		def setting1 = new Setting(content:null)

		expect:
		1 == setting1.numRows
	}

	def "numRows - blank content returns 1"() {
		given:
		def setting1 = new Setting(content:"")

		expect:
		1 == setting1.numRows
	}

	def "numRows - single row returns 2"() {
		given:
		def setting1 = new Setting(content:"row 1")

		expect:
		2 == setting1.numRows
	}

	def "numRows - two rows returns 3"() {
		given:
		def setting1 = new Setting(content:"row 1\nrow 2")

		expect:
		3 == setting1.numRows
	}

	def "numRows - extra line feeds are respected"() {
		given:
		def setting1 = new Setting(content:"\n row1 \n \n")

		expect:
		4 == setting1.numRows
	}
}

