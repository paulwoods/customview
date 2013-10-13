package org.grails.plugin.customview

class TableController {

	def index() {
		redirect action:"list"
	}

	def list() {
		def tables = Table.list()
		[tables:tables]
	}

	def create() {
		[table: new Table()]
	}

	def save() {
		def table = new Table(params)
		if(table.save())
			redirect action:"show", id:table.id
		else
			render view:"create", model:[table:table]
	}

	def show() {
		withTable { table ->
			[table:table]
		}
	}

	def edit() {
		withTable { table ->
			[table:table]
		}
	}

	def update() {
		withTable { table ->
			table.properties = params
			if(table.save())
				redirect action:"show", id:table.id
			else
				render view:"edit", model:[table:table]
		}
	}

	def delete() {
		withTable { table ->
			table.delete()
			flash.message = "The table has been deleted."
			redirect action:"list"
		}
	}

	private def withTable(Closure closure) {
		def table = Table.get(params.id)
		if(!table) {
			flash.message = "The table was not found."
			return redirect(action:"list")
		}

		closure.call table
	}
	
}
