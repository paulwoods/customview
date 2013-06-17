<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Modify ${view.name}</title>
	<g:javascript src="jquery-1.10.0.min.js"/>
</head>
<body>
	<h1>Modify ${view.name}</h1>
	
	<table class="classview-modify">
	<thead>
	<tr>
		<th>Order</th>
		<th>Title</th>
		<th>Sort</th>
		<th>Compare</th>
		<th>Value</th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${view.columns}" var="column">
	<tr>
		<td>X</td>
		<td>${column.name}</td>
		<td>Sort</td>
		<td>Compare</td>
		<td>Value</td>
	</tr>
	</g:each>
	</tbody>
	</table>
</body>
</html>

