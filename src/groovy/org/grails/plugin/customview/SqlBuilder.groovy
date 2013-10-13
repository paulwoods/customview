package org.grails.plugin.customview

class SqlBuilder {
	
	String build(Query query) {

		StringBuilder sb = new StringBuilder()

		if(query.selects && query.froms) {
			buildSelects query, sb
			buildFroms query, sb
			buildWheres query, sb
			buildOrders query, sb
			buildLimits query, sb
		}

		sb.toString()
	}

	private buildSelects(Query query, StringBuilder sb) {
		sb << "select distinct "
		sb << query.selects.join(", ")
	}

	private buildFroms(Query query, StringBuilder sb) {
		sb << " from "
		sb << query.froms.join(", ")
	}

	private buildWheres(Query query, StringBuilder sb) {
		if(query.wheres) {
			sb << " where "
			sb << query.wheres.join(" and ")
		}
	}

	private buildOrders(Query query, StringBuilder sb) {
		if(query.orders) {
			sb << " order by "
			sb << query.orders.join(", ")
		}
	}
		
	private buildLimits(Query query, StringBuilder sb) {
		boolean hasOffset = (null != query.offset)
		boolean hasCount = (null != query.count)

		if(hasCount && hasOffset) {
			sb << " limit $query.count offset $query.offset"
		} else if(hasCount && !hasOffset) {
			sb << " limit $query.count"
		} else if(!hasCount && hasOffset) {
			sb << " limit 18446744073709551615 offset $query.offset"
		}
	}

}

