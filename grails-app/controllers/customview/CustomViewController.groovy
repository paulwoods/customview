package customview

class CustomViewController {

	def index() {
		redirect action:"list"
	}

	def list() {
		def views = View.list()
		[views:views]
	}

	def create() {
		[view:new View()]
	}

	def save() {
		def view = new View(params)
		if(view.save())
			redirect action:"show", id:view.id
		else
			render view:"create", model:[view:view]
	}

	def show() {
		withView { view ->
			[view:view]
		}
	}

	def edit() {
		withView { view ->
			[view:view]
		}
	}

	def update() {
		withView { view ->
			view.properties = params
			if(view.save())
				redirect action:"show", id:view.id
			else
				render view:"edit", model:[view:view]
		}
	}

	def delete() {
		withView { view ->
			view.delete()
			flash.message = "The view has been deleted."
			redirect action:"list"
		}
	}

	private def withView(Closure closure) {
		def view = View.get(params.id)
		if(!view) {
			flash.message = "The view was not found."
			return redirect(action:"list")
		}

		closure.call view
	}

}
