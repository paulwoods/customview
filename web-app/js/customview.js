/*! customview.js
 *
 */

(function(window, $, undefined) {

	"use strict";

	function CustomView(options) {

		var defaults = {
			name: '',
			fetchURL: '',
			offset: 0,
			fetchDistance: 150
		};

		// settings
		// $el
		// 

		this.settings = $.extend({}, defaults, options);
		this.$el = $('#' + this.settings.name);
		this.$el.data('customView', this);
		this.fetch();
		
		var self = this;

		$(window).on("scroll", function(e) { self.scroll(e); });
	}

	CustomView.prototype = {

		// fetch a block of records and append to the table's tbody
		fetch: function() {

			var data = {
				name: this.settings.name,
				offset: this.settings.offset
			};

			console.log("fetch", data);

			var self = this;

			$.getJSON(this.settings.fetchURL, data)
			.done(function(json) { self.fetchSuccess(json) })
			.fail(function(header) { self.failure(header) });
		},

		fetchSuccess: function(json) {
			this.settings.offset = json.offset;
			this.$el.find('tbody').append(json.html);
			this.settings.offset = json.offset;
			this.settings.moreData = json.moreData;

			if(!this.settings.moreData) {
				$(window).off("scroll", this.scroll);
			}

		},

		failure: function() {

		},

		// the user has adjusted the scroll bar
		scroll:function() {

			if(this.shouldFetch()) {
				this.fetch();
			}

		},

		// returns the number of pixels to the bottom of the view
		distance: function() {
			return $(document).height() - $(window).height() - $(window).scrollTop();
		},

		// returns flag indicating if fetch should be called again
		// should be called if there is more data, and we are close to the bottom of the scroll.
		shouldFetch: function() {
			console.log("this.settings.moreData", this.settings.moreData);
			console.log("this.distance", this.distance());
			console.log("this.settings.fetchDistance", this.settings.fetchDistance);

			return this.settings.moreData && (this.distance() < this.settings.fetchDistance);
		}


	};


	window.CustomView = CustomView;

})(window, jQuery);
