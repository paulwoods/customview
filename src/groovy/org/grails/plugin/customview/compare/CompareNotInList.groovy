package org.grails.plugin.customview.compare

import customview.*

class CompareNotInList extends Compare {

	CompareNotInList() {
		symbol = Symbols.NOT_IN_LIST.symbol
	}

	void execute() {
		String content = multiple()
		if(!content)
			return

		query.addWhere setting.column.code + " not in (" + content + ")"
	}

}

