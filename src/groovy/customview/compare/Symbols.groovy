package customview.compare

enum Symbols {
	EQUALS("="),
	NOT_EQUALS("<>"),
	LESS_THAN("<"),
	GREATER_THAN(">"),
	LESS_THAN_OR_EQUAL_TO("<="),
	GREATER_THAN_OR_EQUAL_TO(">="),
	
	BEGINS_WITH("begins with"),
	NOT_BEGINS_WITH("not begins with"),

	ENDS_WITH("ends with"),
	NOT_ENDS_WITH("not ends with"),
	
	CONTAINS("contains"),
	NOT_CONTAIN("does not contain"),

	IS_NULL("is null"),
	IS_NOT_NULL("is not null"),
	
	IN_LIST("in list"),
	NOT_IN_LIST("not in list"),

	private final String symbol

	Symbols(String symbol) {
		this.symbol = symbol
	}

	String getSymbol() {
		symbol
	}

}

