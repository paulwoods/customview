<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Modify ${view.name}</title>
	<g:javascript src="jquery-1.10.0.min.js"/>
	<g:javascript src="customize.js"/>
</head>
<body>
	<h1>Modify ${view.name}</h1>
	
	<table id="customize" class="classview-modify">
	<thead>
	<tr>
		<th>Order</th>
		<th>Title</th>
		<th>Sort</th>
		<th>Compare</th>
		<th>Value</th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${view.columns}" var="column">
	<g:set var="setting" value="${column.getSetting(userId)}"/>
	<tr id="${setting.id}">
		<td>==</td>
		<td>${column.name}</td>
		<td><g:select class="sort" from="${['','ASC','DESC']}" name="sort" value="${setting.sort}"/></td>
		<td><g:select class="compare" from="${[
			'', '=', '<>', '<', '>', '<=', '>=', 'begins with', 'contains', 'does not contain',
			'ends with', 'is null', 'is not null', 'in list', 'not in list',
			]}" name="compare" /></td>
		<td><textarea rows="1" cols="15"></textarea></td>
	</tr>
	</g:each>
	</tbody>
	</table>

	<script>
$(function() {
	new Customize({
		el:'#customize',
		userId: '${userId}',
		sortURL: '<g:createLink action="sort" absolute="true"/>'
	});
});
	</script>

</body>
</html>

