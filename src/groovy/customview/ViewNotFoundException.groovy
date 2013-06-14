package customview

class ViewNotFoundException extends CustomViewException {
	ViewNotFoundException(String name) {
		super("The view was not found. name = $name")
	}
}

