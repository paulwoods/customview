package org.grails.plugin.customview

class CustomViewException extends RuntimeException {
	CustomViewException() {
		super()
	}

	CustomViewException(String message) {
		super(message.toString())
	}

	CustomViewException(Throwable cause) {
		super(cause)
	}

	CustomViewException(String message, Throwable cause) {
		super(message, cause)
	}

}

