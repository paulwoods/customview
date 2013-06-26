<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Customize: ${view.name}</title>
	<g:javascript src="jquery-1.10.0.min.js"/>
	<g:javascript src="customize.js"/>
</head>
<body>
	<h1>
		Customize: ${view.name}
		<img id="waiting" src="${resource(dir: 'images', file: 'spinner.gif')}">
	</h1>
	

	<table id="customize" class="classview-modify">
	<caption><a href="${returnURL}">Return</a></caption>
	<thead>
	<tr>
		<th>Order</th>
		<th>Title</th>
		<th>Visible</th>
		<th>Sort</th>
		<th>Compare</th>
		<th>Value</th>
		<th>&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${view.columns}" var="column">
	<g:set var="setting" value="${column.getSetting(userId)}"/>
	<g:set var="placeholder" value="${'DATE' == column.type ? 'yyyy-mm-dd' : ''}"/>
	<tr data-id="${setting.id}">
		<td>==</td>
		<td>${column.name}</td>
		<td><g:checkBox class="visible" name="visible" checked="${setting.visible}"/></td>
		<td><g:select class="sort" from="${['','ASC','DESC']}" name="sort" value="${setting.sort}"/></td>
		<td><g:select class="compare" from="${[
			'', '=', '<>', '<', '>', '<=', '>=', 'begins with', 'contains', 'does not contain',
			'ends with', 'is null', 'is not null', 'in list', 'not in list',
			]}" name="compare" value="${setting.compare}"/></td>
		<td>
			<textarea class="value" rows="1" cols="15" placeholder="${placeholder}">${setting.value}</textarea>
			<input type="button" class="save" value="save"/>
		</td>
		<td>
			<input type="button" class="reset" value="reset"/>
			
		</td>
	</tr>
	</g:each>
	</tbody>
	</table>

	<script>

	$(function() {
		new Customize({
			el: '#customize',
			sortURL: '<g:createLink action="sort" absolute="true"/>',
			visibleURL: '<g:createLink action="visible" absolute="true"/>',
			compareURL: '<g:createLink action="compare" absolute="true"/>',
			valueURL: '<g:createLink action="value" absolute="true"/>',
			resetURL: '<g:createLink action="reset" absolute="true"/>'
		});
	});

	</script>

</body>
</html>

