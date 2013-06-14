package customview

class FailedToCreateViewException extends CustomViewException {
	FailedToCreateViewException(View view) {
		super("Failed to create the view: $view.name")
	}
}

