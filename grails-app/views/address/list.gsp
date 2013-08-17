<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Addresses</title>
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
	
		<h2>Addresses</h2>

		<input id="userid" type="text" name="userid" value="${session.userid}"/>
		<input id="submit" type="button" value="submit"/>

		<specteam:customView name="addresses"/>

	</div>

<script>

$(document).on('click', '#submit', function() {
	var url = '<g:createLink action="changeuser" absolute="true"/>';
	url += '/' + $('#userid').val();
	location.href = url;
});

</script>

</body>
</html>

