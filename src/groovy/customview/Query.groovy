package customview

class Query {
	
	def customViewFactory

	List<String> selects = []
	List<String> froms = []
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

	String toSQL() {
		StringBuilder sb = new StringBuilder()
		sb << "select "
		sb << selects.join(", ")
		sb << " from "
		sb << froms.join(", ")
		sb << " limit $offset, $fetchSize"
		sb.toString()
	}


}

