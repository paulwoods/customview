package customview.compare

import customview.*

class CompareNotBeginsWith extends Compare {

	CompareNotBeginsWith() {
		symbol = Symbols.NOT_BEGINS_WITH.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " not like '" + setting.content + "%'"
	}

}

