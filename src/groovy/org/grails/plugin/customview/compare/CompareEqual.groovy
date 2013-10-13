package org.grails.plugin.customview.compare

import customview.*

class CompareEqual extends Compare {

	CompareEqual() {
		symbol = Symbols.EQUALS.symbol
	}

	void execute() {
		simple()
	}

}

