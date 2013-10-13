package org.grails.plugin.customview

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(View)
@Mock([View,Column,Setting])
class ViewSpec extends Specification {

	def userid = "paul.woods"
	def view1
	def column1, column2
	def setting1, setting2

	def setup() {
		view1 = new View(name:"view1", fetchSize:333).save()
		assert view1

		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence:0).save()
		assert column1

		column2 = new Column(view:view1, name:"column2", code:"table2.column2", sequence:1).save()
		assert column2

		setting1 = new Setting(column:column1, userid:userid, sequence:0).save()
		assert setting1

		setting2 = new Setting(column:column2, userid:userid, sequence:1).save()
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
		null == view1.getSortSetting(userid)
	}

	def "get the setting that has a sort set"() {
		given:
		setting1.sort = "ASC"
		assert setting1.save()

		expect:
		setting1 == view1.getSortSetting(userid)
	}

	def "get the setting that has a sort set 2"() {
		given:
		setting2.sort = "ASC"
		assert setting2.save()

		expect:
		setting2 == view1.getSortSetting(userid)
	}

	def "only get sort setting for this user"() {
		given:
		setting2.sort = "ASC"
		setting2.userid = 99999
		assert setting2.save()

		expect:
		null == view1.getSortSetting(userid)
	}

	def "the users sorts are cleared"() {
		given:
		setting2.sort = "ASC"
		assert setting2.save()

		when:
		view1.clearUserSorts userid

		then:
		"" == setting1.sort
		"" == setting2.sort
	}

	def "view can store connection information"() {
		given:
		def view2 = new View(name:"view2", fetchSize:50,
			url:"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000", 
			username:"sa", password:"abcdefg", driver:"org.h2.Driver").save()
		assert view2

		expect:
		"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000" == view2.url
		"sa" == view2.username
		"abcdefg" == view2.password
		"org.h2.Driver" == view2.driver
	}

	def "if url is invalid getconnection returns null"() {
		given:
		def view2 = new View(name:"view2",
			url:"", 
			username:"sa", 
			password:"", 
			driver:"org.h2.Driver")

		assert view2.save()
		
		expect:
		!view2.connection
	}

	def "if username is invalid getconnection returns null"() {
		given:
		def view2 = new View(name:"view2",
			url:"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000", 
			username:"", 
			password:"", 
			driver:"org.h2.Driver")

		assert view2.save()
		
		expect:
		!view2.connection
	}

	def "if driver is null getconnection returns null"() {
		given:
		def view2 = new View(name:"view2",
			url:"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000", 
			username:"sa", 
			password:"abcdefg", 
			driver:"")

		assert view2.save()
		
		expect:
		!view2.connection
	}

}
