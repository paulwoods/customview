package customview.compare

import customview.*

class CompareIsNotNull extends Compare {

	CompareIsNotNull() {
		symbol = Symbols.IS_NOT_NULL.symbol
	}

	void execute() {
		query.addWhere setting.column.code + " is not null"
	}

}

