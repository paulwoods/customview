package org.grails.plugin.customview.compare

import customview.*

class CompareContains extends Compare {

	CompareContains() {
		symbol = Symbols.CONTAINS.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " like '%" + setting.content + "%'"
	}

}

