package customview

class FailedToCreateOrderException extends CustomViewException {
	FailedToCreateOrderException(Order order) {
		super("Failed to create the order: $order.name")
	}
}

