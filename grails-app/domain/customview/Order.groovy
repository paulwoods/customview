package customview

class Order {

	def customViewFactory

	static belongsTo = [view: View]

	String name

	static mapping = {
		table "customview_order"
		sort "name"
	}

	static constraints = {
		name blank:false, maxSize:60, unique:true
	}

	String toString() {
		"Order[$id] $name"
	}

	def beforeValidate() {
		name = name?.trim()
	}

}
