/*! 
 * customize.js
 */

(function(window, $, undefined) {

	"use strict";

	function Customize(options) {
		this.el = '';
		this.sortURL = '';
		this.userId = '';

		$.extend(this, options);
		this.$el = $(this.el);
		this.$el.data('customize', this);
		
		var self = this;
		$(this.$el).on('change', 'select.sort', function(e) { self.changeSort(e); });
	}

	Customize.prototype.changeSort = function(e) {
		var $this = $(e.target);

		var data = {
			userId: this.userId,
			settingId: $this.closest('tr').attr('id'),
			sort: $this.val()
		};

		var self = this;
		
		$.ajax({
			url: this.sortURL,
			data: data, 
			dataType: 'json', 
			type: 'post'
		})
		.done(function(json) { self.changeSortSuccess(json); })
		.fail(function(header) { self.error(header); });
	};

	Customize.prototype.changeSortSuccess = function(json) {
		$(this.$el).find('select.sort').val('');
		$(this.$el).find('#' + json.id + " .sort").val(json.sort);
	};

	Customize.prototype.error = function(header) {
		try {
			alert($.parseJSON(header.responseText).message);
		} catch(e) {
			$(document.documentElement).html(function(){
				return header.responseText;
			});
		}
	};

	window.Customize = Customize;

})(window, jQuery);
