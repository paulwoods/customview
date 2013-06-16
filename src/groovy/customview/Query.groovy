package customview

class Query {
	
	def customViewFactory

	List<String> selects = []
	List<String> froms = []
	List<String> orders = []
	Integer fetchSize
	Integer offset

	List run() {
		Runner runner = customViewFactory.createRunner()
		runner.run this
	}

	void addSelect(String name) {
		selects << name
	}

	void addFrom(String name) {
		froms << name
	}

	void addOrder(String name) {
		orders << name
	}

	String toSQL() {
		StringBuilder sb = new StringBuilder()
		sb << "select "
		sb << selects.join(", ")
		
		sb << " from "
		sb << froms.join(", ")

		if(orders) {
			sb << " order by "
			sb << orders.join(", ")
		}

		sb << " limit $offset, $fetchSize"
		
		sb.toString()
	}


}

