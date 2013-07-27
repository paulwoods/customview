<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Views</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link controller="customView" action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customView" action="list">Views</g:link></li>
		<li><g:link controller="customTable" action="list">Tables</g:link></li>
		<li><g:link controller="customColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Views</h2>
		<table class="table">
		<thead>
		<tr>
			<th>Name</th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${views}" var="view">
		<tr>
			<td><g:link action="show" id="${view.id}">${view.name}</g:link></td>
		</tr>
		</g:each>
		</tbody>
		</table>
	</div>

</body>
</html>

