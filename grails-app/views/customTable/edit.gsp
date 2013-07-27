<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Edit Table ${table.name}</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">list</g:link></li>
		<li><g:link action="show" id="${table.id}">show</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customViewView" action="list">Views</g:link></li>
		<li><g:link controller="customViewTable" action="list">Tables</g:link></li>
		<li><g:link controller="customViewColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Edit Table ${table.name}</h2>
		<g:render template="form"/>
	</div>

</body>
</html>

