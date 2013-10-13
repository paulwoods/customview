<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Table ${table.name}</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">List</g:link></li>
		<li><g:link action="edit" id="${table.id}">Edit</g:link></li>
		<li><g:link action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customView" action="list">Views</g:link></li>
		<li><g:link controller="customTable" action="list">Tables</g:link></li>
		<li><g:link controller="customColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Table</h2>
		<table class="table">
		<tbody>
		<tr>
			<td>Table Name</td>
			<td>${table.name}</td>
		</tr>
		<tr>
			<td>View Name</td>
			<td>${table.view.name}</td>
		</tr>
		</tbody>
		</table>
	</div>

</body>
</html>

