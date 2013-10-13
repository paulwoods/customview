package org.grails.plugin.customview.compare

import customview.*

class CompareNotEqual extends Compare {

	CompareNotEqual() {
		symbol = Symbols.NOT_EQUALS.symbol
	}

	void execute() {
		simple()
	}

}

