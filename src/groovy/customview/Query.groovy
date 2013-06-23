package customview

class Query {
	
	def runner = new Runner()

	List<String> selects = []
	List<String> froms = []
	List<String> orders = []
	List<String> wheres = []

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

	void addWhere(String name) {
		wheres << name
	}

	String toSQL() {
		StringBuilder sb = new StringBuilder()
		sb << "select "
		sb << selects.join(", ")
		
		sb << " from "
		sb << froms.join(", ")

		if(wheres) {
			sb << " where "
			sb << wheres.join(" and ")
		}

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

