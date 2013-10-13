		<g:hasErrors bean="${view}">
		<div class="errors">
			<g:renderErrors bean="${view}" as="list" />
		</div>
		</g:hasErrors>

		<g:form class="form-horizontal" id="${view.id}">

		<fieldset>
		
		<legend>View</legend>

		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<input name="name" type="text" id="name" placeHolder="name" value="${view.name}" required autofocus>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="fetchSize">Fetch Size</label>
			<div class="controls">
				<input name="fetchSize" type="number" id="fetchSize" min="1" placeHolder="fetch size" value="${view.fetchSize}" required>
			</div>
		</div>
		
		</fieldset>

		<fieldset>
		
		<legend>Database Connection</legend>

		<div class="control-group">
			<label class="control-label" for="url">URL</label>
			<div class="controls">
				<input name="url" type="url" id="url" placeHolder="url" value="${view.url}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="username">Username</label>
			<div class="controls">
				<input name="username" type="text" id="username" placeHolder="username" value="${view.username}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<input name="password" type="password" id="password" placeHolder="password" value="${view.password}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="driver">Driver</label>
			<div class="controls">
				<input name="driver" type="text" id="driver" placeHolder="driver" value="${view.driver}">
			</div>
		</div>

		</fieldset>

		<div class="form-actions">
			<g:if test="${view.id}">
			<g:actionSubmit class="btn btn-primary" action="update" value="Update"/>
			<g:actionSubmit class="btn" action="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
			</g:if>
			<g:else>
			<g:actionSubmit class="btn btn-primary" action="save" value="Save"/>
			</g:else>
		</div>

		</g:form>

