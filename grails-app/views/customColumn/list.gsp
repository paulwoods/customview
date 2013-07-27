<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Columns</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link controller="customViewColumn" action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customViewView" action="list">Views</g:link></li>
		<li><g:link controller="customViewTable" action="list">Tables</g:link></li>
		<li><g:link controller="customViewColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Columns</h2>
		<table class="table">
		<thead>
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>SQL</th>
			<th>Sequence</th>
			<th>View</th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${columns}" var="column">
		<tr>
			<td><g:link action="show" id="${column.id}">${column.name}</g:link></td>
			<td>${column.type}</td>
			<td>${column.sql}</td>
			<td>${column.sequence}</td>
			<td>${column.view.name}</td>
		</tr>
		</g:each>
		</tbody>
		</table>
	</div>

</body>
</html>
