<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>View ${view.name}</title>
</head>
<body>

	<div class="span2">
		<h3>Actions</h3>
		<ul class="nav nav-list">
		<li><g:link action="list">List</g:link></li>
		<li><g:link action="edit" id="${view.id}">Edit</g:link></li>
		<li><g:link action="create">Create</g:link></li>
		<li><hr/></li>
		<li><g:link controller="customViewView" action="list">Views</g:link></li>
		<li><g:link controller="customViewTable" action="list">Tables</g:link></li>
		<li><g:link controller="customViewColumn" action="list">Columns</g:link></li>
		</ul>
	</div>	

	<div class="span9">
		<h2>View</h2>
		<table class="table">
		<tbody>
		<tr>
			<td>Name</td>
			<td>${view.name}</td>
		</tr>
		<tr>
			<td>Fetch Size</td>
			<td>${view.fetchSize}</td>
		</tr>
		<tr>
			<td>URL</td>
			<td>${view.url}</td>
		</tr>
		<tr>
			<td>Username</td>
			<td>${view.username}</td>
		</tr>
		<tr>
			<td>Password</td>
			<td>${'*' * (view.password?.size() ?: 0)}</td>
		</tr>
		<tr>
			<td>Driver</td>
			<td>${view.driver}</td>
		</tr>

		</tbody>
		</table>
	</div>

</body>
</html>

