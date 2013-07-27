import customview.*

class BootStrap {

	def dataSource
	def servletContext

	def init = { servletContext ->

		def userId = 1

		def sequence = 0

		View view = new View(
			name:"addresses", 
			fetchSize:50,
			url:"jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000", 
			username:"sa", 
			password:"", 
			driver:"org.h2.Driver")

		view.addToTables name:"addresses"

		view.addToColumns name:"ID", sql:"addresses.id", type:"NUMBER", sequence: sequence++
		view.addToColumns name:"Street", sql:"addresses.street", type:"STRING", sequence: sequence++
		view.addToColumns name:"City", sql:"addresses.city", type:"STRING", sequence: sequence++
		view.addToColumns name:"State", sql:"addresses.state", type:"STRING", sequence: sequence++
		view.addToColumns name:"Zip", sql:"addresses.zip", type:"STRING", sequence: sequence++
		view.addToColumns name:"Date Created", sql:"addresses.created_at", type:"DATE", sequence: sequence++
		
		view.save()

		def database = new groovy.sql.Sql(dataSource)

		def query = """
		create table addresses(id int primary key,
		   zip varchar(20), city varchar(50), street varchar(200), state varchar(50), created_at datetime);
		"""

		database.executeUpdate query

		query = """
			insert into addresses (id, zip, city, street, state, created_at)
			values (:id, :zip, :city, :street, :state, :createdAt)
		"""

		new File(servletContext.getRealPath("addresses.csv")).eachLine { String line ->

			def fields = line.split(",")

			database.executeInsert(query, [
				zip: 		fields[0],
				city: 		fields[1],
				street: 	fields[2],
				id: 		fields[3],
				state: 		fields[4],
				createdAt: 	new Date(fields[5])
			])
		}
	}

	def destroy = {
	}

}

