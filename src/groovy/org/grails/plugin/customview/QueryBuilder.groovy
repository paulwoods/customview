package org.grails.plugin.customview

import org.grails.plugin.customview.compare.*

class QueryBuilder {
	
	def compares = [
		new CompareEqual(),
		new CompareNotEqual(),
		new CompareLessThan(),
		new CompareGreaterThan(),
		new CompareLessThanEqualTo(),
		new CompareGreaterThanEqualTo(),
		new CompareBeginsWith(),
		new CompareEndsWith(),
		new CompareContains(),
		new CompareNotContains(),
		new CompareIsNull(),
		new CompareIsNotNull(),
		new CompareInList(),
		new CompareNotInList(),
		new CompareNotBeginsWith(),
		new CompareNotEndsWith(),
	]

	Query build(View view, Integer offset, String userid) {
		def query = new Query()

		if(null == view) {
			log.warn "view is null"
			return query
		}

		query.count = view.fetchSize
		query.offset = offset

		addSelects view, query
		addFroms view, query
		addWheres view, userid, query

		def setting = view.getSortSetting(userid)
		if(setting)
			query.addOrder setting.column.code + " " + setting.sort

		query
	}

	private void addSelects(View view, Query query) {
		view.columns.each { Column column ->
			query.addSelect column.code + " \"$column.name\""
		}
	}
	
	private void addFroms(View view, Query query) {
		view.tables.each { Table table ->
			query.addFrom table.name
		}
	}

	private void addWheres (View view, String userid, Query query) {
		view.getCompareSettings(userid).each { Setting setting ->
			def compare = compares.find { it.symbol == setting.compare }
			assert compare

			compare.view = view
			compare.userid = userid
			compare.query = query
			compare.setting = setting
			compare.execute()
		}
	}

}

