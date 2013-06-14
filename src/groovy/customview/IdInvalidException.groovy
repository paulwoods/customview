package customview

class IdInvalidException extends CustomViewException {
	IdInvalidException(def tagLib, Map attrs) {
		super("invalid custom view. name=$attrs.name controller=$tagLib.controllerName action=$tagLib.actionName")
	}
}

