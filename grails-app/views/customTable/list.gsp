<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Tables</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link controller="customViewTable" action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customViewView" action="list">Views</g:link></li>
		<li><g:link controller="customViewTable" action="list">Tables</g:link></li>
		<li><g:link controller="customViewColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Tables</h2>
		<table class="table">
		<thead>
		<tr>
			<th>Table</th>
			<th>View</th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${tables}" var="table">
		<tr>
			<td><g:link action="show" id="${table.id}">${table.name}</g:link></td>
			<td><g:link controller="customViewView" action="show" id="${table.view.id}">${table.view.name}</g:link></td>
		</tr>
		</g:each>
		</tbody>
		</table>
	</div>

</body>
</html>

