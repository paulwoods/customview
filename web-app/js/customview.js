/*! customview.js
 *
 */

function CustomView(options) {

	var defaults = {
		name: '',
		fetchURL: '',
		offset: 0,
		fetchSize: 50,
		$el: null
	};

	this.settings = $.extend(this, defaults, options);

	this.$el = $('#' + this.name);

	this.$el.data('customView', this);

	this.fetch();
}

// fetch a block of records and append to the table's tbody
CustomView.prototype.fetch = function() {
	var self = this;

	var data = {
		name: this.name,
		fetchSize: this.fetchSize,
		offset: this.offset
	};

	$.getJSON(this.fetchURL, data, function(json) {
		self.offset = json.offset;
		self.fetchSize = json.fetchSize;
		self.$el.find('tbody').append(json.html);
	});


}

