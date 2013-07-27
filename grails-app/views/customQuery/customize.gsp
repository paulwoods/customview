<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Customize: ${view.name}</title>
	<g:javascript src="customize.js"/>
	<style>
	.ui-state-highlight { height: 2em; }
	</style>
</head>
<body>

<div class="span2">
	<h3>Actions</h3>
	<ul class="nav nav-list">
	<li class="nav-header">Actions</li>
	<li><a href="#">action 1</a></li>
	<li><a href="#">action 2</a></li>
	<li><a href="#">action 3</a></li>
	<li><a href="#">action 4</a></li>
	<li><a href="#">action 5</a></li>
	</ul>
</div>	

<div class="span9">

	<h2>
		Customize Your View: ${view.name} : userId =${userId}
		<img id="waiting" src="${resource(dir: 'images', file: 'spinner.gif')}" alt="spinner">
	</h2>
	<table id="customize" class="classview-modify">
	<caption><a href="${returnURL}">Return</a></caption>
	<thead>
	<tr>
		<th>Title</th>
		<th>Visible</th>
		<th>Sort</th>
		<th>Compare</th>
		<th>Content</th>
		<th>&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${view.getSettingsInOrder(userId)}" var="setting">
	<g:set var="placeholder" value="${'DATE' == setting.column.type ? 'yyyy-mm-dd' : ''}"/>
	<tr data-id="${setting.id}">
		<td>${setting.column.name}</td>
		<td class="text-center"><g:checkBox id="visible${setting.id}" class="visible" name="visible" checked="${setting.visible}"/></td>
		<td><g:select  id="sort${setting.id}" class="sort" from="${['','ASC','DESC']}" name="sort" value="${setting.sort}"/></td>
		<td><g:select  id="compare${setting.id}" class="compare" from="${customview.Setting.COMPARES}" name="compare" value="${setting.compare}"/></td>
		<td>
			<textarea class="content" rows="${setting.numRows}" placeholder="${placeholder}">${setting.content}</textarea>
			<input type="button" class="save" value="save"/>
		</td>
		<td>
			<input type="button" class="reset" value="reset"/>
		</td>
	</tr>
	</g:each>
	</tbody>
	</table>
</div>

	<script>

	$(function() {
		new Customize({
			el: '#customize',
			sortURL: '<g:createLink action="sort" absolute="true"/>',
			visibleURL: '<g:createLink action="visible" absolute="true"/>',
			compareURL: '<g:createLink action="compare" absolute="true"/>',
			contentURL: '<g:createLink action="content" absolute="true"/>',
			resetURL: '<g:createLink action="reset" absolute="true"/>',
			orderURL: '<g:createLink action="order" absolute="true"/>'
		});
	});

	</script>

</body>
</html>

