package customview.compare

import customview.*

class CompareInList extends Compare {

	CompareInList() {
		symbol = Symbols.IN_LIST.symbol
	}

	void execute() {
		String content = multiple()
		if(!content)
			return

		query.addWhere setting.column.code + " in (" + content + ")"
	}

}

