package org.grails.plugin.customview.compare

import customview.*

class CompareNotEndsWith extends Compare {

	CompareNotEndsWith() {
		symbol = Symbols.NOT_ENDS_WITH.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " not like '%" + setting.content + "'"
	}

}

