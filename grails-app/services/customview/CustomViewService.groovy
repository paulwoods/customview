package customview

class CustomViewService {

	def queryBuilder = new QueryBuilder()
	def bodyBuilder = new BodyBuilder()

	Map fetch(View view, Integer offset, Long userId, database) {
		Query query = buildQuery(view, offset, userId)
		List records = fetchRecords(query, database)
		String html = recordsToHTML(view, records, userId)
		[
			offset: offset + records.size(),
			html: html,
			moreData: (records) ? true : false
		]
	}

	Query buildQuery(View view, Integer offset, Long userId) {
		queryBuilder.build(view, offset, userId)
	}

	List fetchRecords(Query query, database) {
		query.run(database)
	}

	String recordsToHTML(View view, List records, Long userId) {
		bodyBuilder.build view, records, userId
	}
}

