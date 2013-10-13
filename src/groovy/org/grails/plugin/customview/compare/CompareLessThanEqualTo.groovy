package org.grails.plugin.customview.compare

import customview.*

class CompareLessThanEqualTo extends Compare {

	CompareLessThanEqualTo() {
		symbol = Symbols.LESS_THAN_OR_EQUAL_TO.symbol
	}

	void execute() {
		simple()
	}

}

