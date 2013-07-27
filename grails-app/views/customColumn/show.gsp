<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Column ${column.name}</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">List</g:link></li>
		<li><g:link action="edit" id="${column.id}">Edit</g:link></li>
		<li><g:link action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customViewView" action="list">Views</g:link></li>
		<li><g:link controller="customViewTable" action="list">Tables</g:link></li>
		<li><g:link controller="customViewColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	


	<div class="span9">
		<h2>Column</h2>
		<table class="table">
		<tbody>
		<tr>
			<td>Name</td>
			<td>${column.name}</td>
		</tr>
		<tr>
			<td>SQL</td>
			<td>${column.sql}</td>
		</tr>
		<tr>
			<td>Sequence</td>
			<td>${column.sequence}</td>
		</tr>
		<tr>
			<td>Type</td>
			<td>${column.type}</td>
		</tr>
		<tr>
			<td>Class Head</td>
			<td>${column.classHead}</td>
		</tr>
		<tr>
			<td>Class Body</td>
			<td>${column.classBody}</td>
		</tr>
		<tr>
			<td>TD</td>
			<td>${column.td}</td>
		</tr>
		<tr>
			<td>TH</td>
			<td>${column.th}</td>
		</tr>

		</tbody>
		</table>
	</div>

</body>
</html>

