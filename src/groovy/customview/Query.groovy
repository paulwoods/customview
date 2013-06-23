package customview

class Query {
	
	def runner = new Runner()

	List<String> selects = []
	List<String> froms = []
	List<String> orders = []
	Integer fetchSize
	Integer offset

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

	List run(database) {
		runner.run this, database
	}


}

