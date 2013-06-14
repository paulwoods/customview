package customview

class FailedToCreateColumnException extends CustomViewException {
	FailedToCreateColumnException(Column column) {
		super("Failed to create the column: $column.name")
	}
}

