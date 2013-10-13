<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Edit Column ${column.name}</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">list</g:link></li>
		<li><g:link action="show" id="${column.id}">show</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customView" action="list">Views</g:link></li>
		<li><g:link controller="customTable" action="list">Tables</g:link></li>
		<li><g:link controller="customColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Edit Column ${column.name}</h2>
		<g:render template="form"/>
	</div>

</body>
</html>

