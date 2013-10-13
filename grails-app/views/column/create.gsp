<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Create Column</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">List</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customView" action="list">Views</g:link></li>
		<li><g:link controller="customTable" action="list">Tables</g:link></li>
		<li><g:link controller="customColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>Create Column</h2>
		<g:render template="form"/>
	</div>

</body>
</html>

