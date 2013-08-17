package customview.compare

import customview.*

class CompareNotContains extends Compare {

	CompareNotContains() {
		symbol = Symbols.NOT_CONTAIN.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " not like '%" + setting.content + "%'"
	}

}

