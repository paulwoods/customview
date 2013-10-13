package org.grails.plugin.customview

class Query {
	
	def runner = new Runner()

	List<String> selects = []
	List<String> froms = []
	List<String> orders = []
	List<String> wheres = []

	Long count
	Long offset

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

}

