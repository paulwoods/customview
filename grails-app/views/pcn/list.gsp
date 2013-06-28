<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>PCNs</title>
</head>
<body>

	<div class="span2">
	<h3>Actions</h3>
	<ul class="nav nav-list">
	<li class="nav-header">Actions</li>
	<li><a href="#">action 1</a></li>
	<li><a href="#">action 2</a></li>
	<li><a href="#">action 3</a></li>
	<li><a href="#">action 4</a></li>
	<li><a href="#">action 5</a></li>
	</ul>
	</div>	

	<div class="span9">
	<h2>
		PCNs
		<img id="waiting" src="${resource(dir: 'images', file: 'spinner.gif')}">
	</h2>
	<specteam:customView name="pcns"/>
	</div>

</body>
</html>

