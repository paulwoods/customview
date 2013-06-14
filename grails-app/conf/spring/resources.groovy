// Place your Spring DSL code here
beans = {

	customViewFactory(customview.CustomViewFactory) { bean ->
		customViewPlugin = ref("customViewPlugin")
		customViewService = ref("customViewService")
	}

	customViewPlugin(customview.CustomViewPlugin) { bean ->
	}

}
