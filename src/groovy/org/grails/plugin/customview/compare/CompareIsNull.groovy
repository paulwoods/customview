package org.grails.plugin.customview.compare

import customview.*

class CompareIsNull extends Compare {

	CompareIsNull() {
		symbol = Symbols.IS_NULL.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " is null"
	}

}

