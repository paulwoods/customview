package org.grails.plugin.customview

class CustomViewService {

	Result fetch(View view, Integer offset, String userid) {
		if(!view)
			throw new CustomViewException("The view is null.")

		if(null == offset)
			throw new CustomViewException("The offset is null.")

		List records = []
		try {
			records = fetchRecords(view, offset, userid)
		} catch(CustomViewException e) {
			throw e
		} catch(e) {
			throw new CustomViewException(e)
		}
		
		def result = new Result()
		result.userid = userid
		result.view = view
		result.records = records
		result.offset = offset + result.records.size()
		result.moreData = (result.records) ? true : false
		
		result
	}

	QueryBuilder queryBuilder = new QueryBuilder()
	SqlBuilder sqlBuilder = new SqlBuilder()
	Runner runner = new Runner()

	List fetchRecords(View view, Integer offset, String userid) {
		Query query = queryBuilder.build(view, offset, userid)
		String sql = sqlBuilder.build(query)
		def connection = getConnection(view)

		try {
			runner.run sql, connection
		} finally {
			connection?.close()
		}
	}

	def getConnection(View view) {
		def connection = view.getConnection()
		if(connection)
			connection
		else
			throw new CustomViewException("Unable to get database connection.")
	}

}
