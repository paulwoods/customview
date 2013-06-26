package customview

class Result {
	def queryBuilder = new QueryBuilder()
	def bodyBuilder = new BodyBuilder()
	def sqlBuilder = new SqlBuilder()
	def runner = new Runner()

	View view
	Integer offset = 0
	Long userId = 0
	List records = []
	String html = ""
	Boolean moreData = false
	def database
	Query query
	String sql

	void fetchRecords() {
		if(!view) return

		query = queryBuilder.build(view, offset, userId)
		sql = sqlBuilder.build(query)
		records = runner.run(sql, database)
		offset += records.size()
		moreData = (records) ? true : false
	}

	void createHTML() {
		html = bodyBuilder.build(view, records, userId)
	}

}

