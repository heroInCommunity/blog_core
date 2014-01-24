(function ( $ ) {
	$.fn.progressbar = function(progressBar, progressbarNav) {
		var that = this;
		
		var startYPosition = 0;
		var movedY = 0;
		that.bind('touchstart', function(event) {
			var scrolledObj = event.originalEvent.changedTouches[0];
			startYPosition = parseInt(scrolledObj.clientY);		
		});
		
		
		var scrollableDiv = that.find('div:first-child');
		var scrolled = 0;
		scrollableDiv.css('top', '0px');
		
		that.bind('mousewheel touchend touchmove DOMMouseScroll', function(event) {
			var scrollBarSize = scrollableDiv.height();
			
			if (scrolled <= event.delegateTarget.offsetTop) {
				scrolled = event.delegateTarget.offsetTop;
			}
			
			if (scrolled >= scrollBarSize - event.delegateTarget.offsetTop) {
				if (getDeltaY(event) > 0) {				
					scrolled = scrollBarSize;
					progressBar.css('width', '100%');			
				}
				else {
					scrolled -= event.delegateTarget.offsetTop;
					progressBar.css('width', (scrolled / scrollBarSize * 100) + '%');
				}
			}
			else {
				var scrollMove = getDeltaY(event);
				scrolled += scrollMove;
				
				if (scrolled <= event.delegateTarget.offsetTop) {
					scrollableDiv.css('top', '0px');
					progressBar.css('width', '0%');
				}
				else {
					scrollableDiv.css('top', parseInt(scrollableDiv.css('top')) - scrollMove);
					progressBar.css('width', (scrolled / scrollBarSize * 100) + '%');	
				}
			}			
			
			event.preventDefault();
			return false;
		});
		
		function getDeltaY(scrollEvent) {
			if(typeof scrollEvent.originalEvent.deltaY != 'undefined') {
				return scrollEvent.originalEvent.deltaY;
			}
			if(typeof scrollEvent.originalEvent.wheelDeltaY != 'undefined') {
				return -scrollEvent.originalEvent.wheelDeltaY;
			}
			else if(typeof scrollEvent.originalEvent.detail != 'undefined' && scrollEvent.originalEvent.detail != 0) {
				return scrollEvent.originalEvent.detail * 30;
			}
			else if(typeof scrollEvent.originalEvent.changedTouches[0] != 'undefined') {
				var scrolledObj = scrollEvent.originalEvent.changedTouches[0];
				movedY = parseInt(scrolledObj.clientY) - startYPosition;
				return movedY;
			}
			return getMovedY(scrollEvent);
		}	
		
		$('#progressbar_nav').click(function(event) {
			var scrollBarSize = scrollableDiv.height();
			var progressbarWidth = progressbarNav.width();
			var scrollableHeight = that.height();
			
			var percent = getX(event) / progressbarWidth * 100;
			progressBar.css('width', percent + '%');
			
			scrolled = percent * scrollBarSize / 100;
			if (scrolled <= scrollableHeight) {
				scrollableDiv.css('top', '0px');
			}
			else if (scrolled >= scrollBarSize - scrollableHeight) {
				scrollableDiv.css('top', (-scrollBarSize + scrollableHeight) + 'px');
			}
			else {
				scrollableDiv.css('top', -scrolled + 'px');	
			}
		});
		
		function getX(clickEvent) {
			if(typeof clickEvent.offsetX != 'undefined') {
				return clickEvent.offsetX;
			}
			else if(typeof clickEvent.originalEvent.layerX != 'undefined') {
				return clickEvent.originalEvent.layerX;
			}
			
			return undefined;
		}
	};
}( jQuery ));