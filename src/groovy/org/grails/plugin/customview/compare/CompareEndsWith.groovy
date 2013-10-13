package org.grails.plugin.customview.compare

import customview.*

class CompareEndsWith extends Compare {

	CompareEndsWith() {
		symbol = Symbols.ENDS_WITH.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " like '%" + setting.content + "'"
	}

}

