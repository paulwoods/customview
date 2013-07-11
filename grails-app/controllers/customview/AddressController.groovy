package customview

class AddressController {

	def index() {
		cache false
		redirect action:"list"
	}

	def list() {
		[:] // used to shut-up codenarc
	}

}
