package org.grails.plugin.customview.compare

import customview.*

class CompareLessThan extends Compare {

	CompareLessThan() {
		symbol = Symbols.LESS_THAN.symbol
	}

	void execute() {
		simple()
	}

}
