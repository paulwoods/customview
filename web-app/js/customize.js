/*! 
 * customize.js
 */

(function(window, $, undefined) {
	"use strict";

	function Customize(options) {
		this.el = '';
		this.sortURL = '';
		this.visibleURL = '';
		this.compareURL = '';
		this.valueURL = '';
		this.orderURL = '';

		$.extend(this, options);
		this.$el = $(this.el);
		this.$el.data('customize', this);
		
		var self = this;
		this.$el.on('change', 'select.sort', function(e) { self.changeSort(e); });
		this.$el.on('click', 'input.visible', function(e) { self.changeVisible(e); });
		this.$el.on('change', 'select.compare', function(e) { self.changeCompare(e); });
		this.$el.on('keyup', 'textarea.value', function(e) { self.changeValue(e); });
		this.$el.on('click', 'input.save', function(e) { self.saveValue(e); });
		this.$el.on('click', 'input.reset', function(e) { self.reset(e); });

		this.$el.find('input.save').hide();
		$(document).ajaxStart( function() { self.show(); });
		$(document).ajaxStop( function() { self.hide(); });

		$("#customize tbody")
			.sortable({placeholder: "ui-state-highlight"})
			.on("sortstop", function(e,ui) { self.order(e,ui); });

		this.hide();
	}

	Customize.prototype.show = function() {
		$('#waiting').show();
	};

	Customize.prototype.hide = function() {
		$('#waiting').hide();
	};

	Customize.prototype.changeVisible = function(e) {
		var $this = $(e.target);

		var data = {
			settingId: $this.closest('tr').data('id'),
			visible: $this.prop('checked')
		};

		var self = this;
		$.ajax({
			url: this.visibleURL,
			data: data, 
			dataType: 'json', 
			type: 'post'
		})
		.done(function(json) { self.changeVisibleSuccess(json); })
		.fail(function(header) { self.error(header); });
	};
	
	Customize.prototype.changeVisibleSuccess = function(/*json*/) {
	};

	Customize.prototype.changeSort = function(e) {
		var $this = $(e.target);

		var data = {
			settingId: $this.closest('tr').data('id'),
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
		this.$el.find('tr[data-id="' + json.id + '"] select.sort').val(json.sort);
	};

	Customize.prototype.changeCompare = function(e) {
		var $this = $(e.target);

		var data = {
			settingId: $this.closest('tr').data('id'),
			compare: $this.val()
		};

		var self = this;

		$.ajax({
			url: this.compareURL,
			data: data,
			dataType: 'json',
			type: 'post'
		})
		.done(function(json) { self.changeCompareSuccess(json); })
		.fail(function(header) { self.error(header); });
	};

	Customize.prototype.changeCompareSuccess = function(/*json*/) {
	};

	Customize.prototype.changeValue = function(e) {
		var $this = $(e.target);
		$this.closest('tr').find('input.save').show();
	};

	Customize.prototype.saveValue = function(e) {
		var $this = $(e.target);

		var data = {
			settingId: $this.closest('tr').data('id'),
			value: $this.closest('tr').find('textarea.value').val()
		};

		var self = this;

		$.ajax({
			url: this.valueURL,
			data: data,
			dataType: 'json',
			type: 'post'
		})
		.done(function(json) { self.changeValueSuccess(json); })
		.fail(function(header) { self.error(header); });
	};

	Customize.prototype.changeValueSuccess = function(json) {
		this.$el.find('tr[data-id="' + json.id + '"] input.save').hide();
	};

	Customize.prototype.reset = function(e) {
		var $this = $(e.target);

		var data = {
			settingId: $this.closest('tr').data('id')
		};

		var self = this;

		$.ajax({
			url: this.resetURL,
			data: data,
			dataType: 'json',
			type: 'post'
		})
		.done(function(json) { self.resetSuccess(json); })
		.fail(function(header) { self.error(header); });
	};

	Customize.prototype.resetSuccess = function(json) {
		var $tr = this.$el.find('tr[data-id="' + json.id + '"]');
		$tr.find('input.visible').prop('checked', json.visible);
		$tr.find('select.sort').val(json.sort);
		$tr.find('select.compare').val(json.compare);
		$tr.find('textarea.value').val(json.value);
		$tr.find('input.save').hide();
	};

	Customize.prototype.order = function(/*e, ui*/) {
		var data = '';

		var first = true;

		this.$el.find("tbody tr").each(function(i) {
			if(first) {
				first = false;
			} else {
				data += "&";
			}

			data += 'id' + $(this).data("id") + "=" + i;
		});

		var self = this;

		$.ajax({
			url: this.orderURL,
			data: data,
			dataType: 'json',
			type: 'post'
		})
		.done(function(json) { self.orderSuccess(json); })
		.fail(function(header) { self.error(header); });
	};

	Customize.prototype.orderSuccess = function(/*json*/) {
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


