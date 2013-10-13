package org.grails.plugin.customview.compare

import customview.*

class CompareGreaterThan extends Compare {

	CompareGreaterThan() {
		symbol = Symbols.GREATER_THAN.symbol
	}

	void execute() {
		simple()
	}

}
