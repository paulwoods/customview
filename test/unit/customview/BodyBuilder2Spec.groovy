package customview

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([View,Column,Setting])
class BodyBuilder2Spec extends Specification {

	BodyBuilder builder
	View view1
	Column column1,column2
	Setting setting1,setting2

	def setup() {
		builder = new BodyBuilder()

		view1 = new View(name:"view1", fetchSize:50).save()
		assert view1
		
		column1 = new Column(view:view1, name:"column1", code:"table1.column1", sequence: 0, type:"STRING").save()
		assert column1

		column2 = new Column(view:view1, name:"column2", code:"table1.column2", sequence: 1, type:"STRING").save()
		assert column2

		setting1 = new Setting(column:column1, userid: "paul.woods", sequence: 0).save()
		assert setting1

		setting2 = new Setting(column:column2, userid: "paul.woods", sequence: 1).save()
		assert setting2
	}

	void assertBody(View view, List records, String expected) {
		def actual = builder.build(view, records, "paul.woods")
		assert expected == actual
	}

	def "dual column records returns the two cells"() {
		expect:
		assertBody view1, [[column1:"abc", column2:"def"]], 
			"<tr><td>abc</td><td>def</td></tr>"
	}

	def "dual column records returns the two cells"() {
		expect:
		assertBody view1, [[column1:"abc", column2:"def"],[column1:"ghi", column2:"jkl"]], 
			"<tr><td>abc</td><td>def</td></tr><tr><td>ghi</td><td>jkl</td></tr>"
	}

	def "columns output in setting sequence order"() {
		given:
		setting1.sequence = 1
		assert setting1.save()
		
		setting2.sequence = 0
		assert setting2.save()

		expect:
		assertBody view1, [[column1:"abc", column2:"def"]], 
			"<tr><td>def</td><td>abc</td></tr>"
	}


}
