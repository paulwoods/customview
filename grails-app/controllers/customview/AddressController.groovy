package customview

class AddressController {

	def index() {
		cache false
		redirect action:"list"
	}

	def list() {
	}

}
