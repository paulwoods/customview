package customview

class CustomViewService {

	def fetch(View view, Integer offset, Long userId, database) {
		def result = new Result()

		if(!view) {
			log.warn "view is null"
			return result
		}

		result.view = view
		result.offset = offset
		result.userId = userId
		result.database = database
		result.fetchRecords()
		result.createHTML()
		result
	}

}
