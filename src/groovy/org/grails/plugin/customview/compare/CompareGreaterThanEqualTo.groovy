package org.grails.plugin.customview.compare

import customview.*

class CompareGreaterThanEqualTo extends Compare {

	CompareGreaterThanEqualTo() {
		symbol = Symbols.GREATER_THAN_OR_EQUAL_TO.symbol
	}

	void execute() {
		simple()
	}

}
