$( document ).ready(function() {
		
	//switch between full and less view modes on main page
	if ($(window).width() < 767) $('#clicker_full').hide();
	var centralBarWidth = $("#page_central_bar").outerWidth();
	var leftBarWidth = $("#page_left_bar").outerWidth();
	var scrollableDiv = $('#scrollable > div');
	
	var isClicked = false;
	$('#clicker_full').click(function() {
		if (isClicked) {
			return false;
		}
		isClicked = true;
		var scrollBarSize = scrollableDiv.height();
		
		$(".selectize-input").fadeOut(500);
		$('#page_left_bar').animate(
			{
				width: "5%",
				opacity: .5
			},
			500,
			"linear"
		);
		$("#page_central_bar").animate(
			{
				width: "95%"
			},
			1000,
			"linear",
			function() {
				isClicked = false;
				scrollableDiv.css('top', (parseInt(scrollableDiv.css('top')) * scrollableDiv.height() / scrollBarSize) + 'px');
			}
		);
		$(this).fadeOut(100);
		$('#clicker_less').fadeIn(100);
	});
	
	$('#clicker_less').click(function() {
		if (isClicked) {
			return false;
		}
		isClicked = true;
		var scrollBarSize = scrollableDiv.height();
		
		$(".selectize-input").fadeIn(1000);
		$('#page_left_bar').animate(
			{
				width: leftBarWidth + "px",
				opacity: 1
			},
			1000,
			"linear",
			function() {
				isClicked = false;
				scrollableDiv.css('top', (parseInt(scrollableDiv.css('top')) * scrollableDiv.height() / scrollBarSize) + 'px');
			}
		);
		$("#page_central_bar").animate(
			{
				width: centralBarWidth + "px"
			},
			500,
			"linear"
		);
		
		$(this).fadeOut(100);
		$('#clicker_full').fadeIn(100);
	});
	
	//toogle sidebar
	$('button.btn[data-toggle="offcanvas"]').click(function() {
		if ($('#page_left_bar').is(':visible')) $('#page_left_bar').hide();
		else $('#page_left_bar').show();
	});

	//progress bar functionality on main page
	$('#scrollable').progressbar($('#progressbar'), $('#progressbar_nav'));
	
});

