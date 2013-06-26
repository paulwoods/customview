/*! 
 * customview.js
 */

(function(window, $, undefined) {

	"use strict";

	function CustomView(options) {

		var defaults = {
			name: '',				// the name of the view
			fetchURL: '',			// the url to the fetch action
			offset: 0,				// the next offset to fetch
			fetchDistance: 150,		// # pixes from the bottom will trigger a fetch
			fetching: false			// flag if currently fetching.
		};

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
			
			//if already fetching, ignore
			if(true == this.settings.fetching) {
				return
			}

			this.settings.fetching = true;

			var data = {
				name: this.settings.name,
				offset: this.settings.offset
			};

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

			this.settings.fetching = false;
			
			if(this.shouldFetch()) {
				this.fetch();
			}
		},

		failure: function(header) {
			this.settings.fetching = false;
			try {
				alert($.parseJSON(header.responseText).message);
			} catch(e) {
				$(document.documentElement).html(function(){
					return header.responseText;
				});
			}
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
			return this.settings.moreData && (this.distance() < this.settings.fetchDistance);
		}

	};

	window.CustomView = CustomView;

})(window, jQuery);
