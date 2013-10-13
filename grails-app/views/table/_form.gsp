		<g:hasErrors bean="${table}">
		<div class="errors">
			<g:renderErrors bean="${table}" as="list" />
		</div>
		</g:hasErrors>

		<g:form class="form-horizontal" id="${table.id}">

		<fieldset>
		
		<legend>Table</legend>

		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<input 
					name="name" 
					type="text" 
					id="name" 
					placeHolder="name" 
					value="${table.name}" 
					required 
					autofocus/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="fetchSize">View</label>
			<div class="controls">
				<g:select 
					from="${customview.View.list()}"
					name="view.id"
					optionKey="id"
					optionValue="name"
					value="${table.view?.id}"
					noSelection="['':'']"
					required="required"
				/>
			</div>
		</div>
		
		</fieldset>

		<div class="form-actions">
			<g:if test="${table.id}">
			<g:actionSubmit class="btn btn-primary" action="update" value="Update"/>
			<g:actionSubmit class="btn" action="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
			</g:if>
			<g:else>
			<g:actionSubmit class="btn btn-primary" action="save" value="Save"/>
			</g:else>
		</div>

		</g:form>

