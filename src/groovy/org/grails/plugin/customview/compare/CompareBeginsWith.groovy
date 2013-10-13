package org.grails.plugin.customview.compare

import customview.*

class CompareBeginsWith extends Compare {

	CompareBeginsWith() {
		symbol = Symbols.BEGINS_WITH.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " like '" + setting.content + "%'"
	}

}

