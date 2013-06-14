package customview

class FailedToCreateTableException extends CustomViewException {
	FailedToCreateTableException(Table table) {
		super("Failed to create the table: $table.name")
	}
}

