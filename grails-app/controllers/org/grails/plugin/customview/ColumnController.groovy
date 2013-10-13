package org.grails.plugin.customview

class ColumnController {

	def index() {
		redirect action:"list"
	}

	def list() {
		def columns = Column.list()
		[columns:columns]
	}

	def create() {
		[column: new Column()]
	}

	def save() {
		def column = new Column(params)
		if(column.save()) 
			redirect action:"show", id:column.id
		else
			render view:"create", model:[column:column]
	}

	def show() {
		withColumn { column ->
			[column:column]
		}
	}

	def edit() {
		withColumn { column ->
			[column:column]
		}
	}

	def update() {
		withColumn { column ->
			column.properties = params
			
			if(column.save()){
				println 1
				redirect action:"show", id:column.id
			}
			else{
				println 2
				render view:"edit", model:[column:column]
			}
		}
	}

	def delete() {
		withColumn { column ->
			column.delete()
			flash.message = "The column has been deleted."
			redirect action:"list"
		}
	}

	private def withColumn(Closure closure) {
		def column = Column.get(params.id)
		if(column) {
			closure.call column
		} else {
			flash.message = "The column was not found."
			redirect action:"list"
		}
	}

}
