		<g:hasErrors bean="${column}">
		<div class="errors">
			<g:renderErrors bean="${column}" as="list" />
		</div>
		</g:hasErrors>

		<g:form class="form-horizontal" id="${column.id}">

		<div class="control-group">
			<label class="control-label" for="name">Name</label>
			<div class="controls">
				<input name="name" type="text" id="name" placeHolder="name" value="${column.name}" required autofocus>
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
					value="${column.view?.id}"
					noSelection="['':'']"
					required="required"
				/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="code">Code</label>
			<div class="controls">
				<input name="code" type="text" id="code" placeHolder="code" value="${column.code}" required>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="sequence">Sequence</label>
			<div class="controls">
				<input name="sequence" type="text" id="sequence" placeHolder="sequence" value="${column.sequence}" required>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="type">Type</label>
			<div class="controls">
				<g:select 
					from="${customview.Column.TYPES}"
					name="type"
					value="${column.type}"
					noSelection="['':'']"
					required="required"
				/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="classHead">Class Head</label>
			<div class="controls">
				<input name="classHead" type="text" id="classHead" placeHolder="classHead" value="${column.classHead}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="classBody">Class Body</label>
			<div class="controls">
				<input name="classBody" type="text" id="classBody" placeHolder="classBody" value="${column.classBody}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="td">TD</label>
			<div class="controls">
				<input name="td" type="text" id="td" placeHolder="td" value="${column.td}">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="th">TH</label>
			<div class="controls">
				<input name="th" type="text" id="th" placeHolder="th" value="${column.th}">
			</div>
		</div>

		<div class="form-actions">
			<g:if test="${column.id}">
			<g:actionSubmit class="btn btn-primary" action="update" value="Update"/>
			<g:actionSubmit class="btn" action="delete" value="Delete" onclick="return confirm('Are you sure?');"/>
			</g:if>
			<g:else>
			<g:actionSubmit class="btn btn-primary" action="save" value="Save"/>
			</g:else>
		</div>

		</g:form>

