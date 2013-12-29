<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.model.User" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/jquery-ui-1.8.13.custom.css">
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/elrte.full.css">
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/selectize.css">
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/selectize.bootstrap3.css">
		<div class="container">
		<div class="row">
			<div class="col-md-3 well">
				<div class="bs-sidebar hidden-print affix-top" role="complementary">
					<ul class="nav bs-sidenav">
						<%
							Map<MenuItem, List<MenuItem>> menus = (Map) request
									.getAttribute(AttributeName.MENUS.value());
							if (menus != null)
								for (MenuItem topMenuItem : menus.keySet()) {
						%>
						<li
							class="<%if (((Integer) topMenuItem.getId()).equals(request
							.getAttribute(AttributeName.TOP_LEVEL_MENU_ID.value()))) out.print("active");%>">
							<a href="../<%=topMenuItem.getUrl()%>"><%=topMenuItem.getName()%></a>
							<ul class="nav">
								<%
									for (MenuItem subMenuItem : menus.get(topMenuItem)) {
								%>
								<li
									class="<%if (((Integer) subMenuItem.getId()).equals(request
								.getAttribute(AttributeName.SUB_LEVEL_MENU_ID.value()))) out.print("active");%>">
									<a href="../<%=subMenuItem.getUrl()%>"><%=subMenuItem.getName()%></a>
								</li>
								<%
									}
								%>
							</ul>
						</li>
						<%
							}
						%>
					</ul>
				</div>
			</div>
			<div class="container" role="main">
				<div class="row row-offcanvas row-offcanvas-right">
					<div class="col-xs-12 col-sm-9 well admin-fields-area">
						<div role="form">
							<div class="form-group">
								<label for="postTitle">Title</label> <input
									class="form-control" id=""postTitle""
									placeholder="Enter post title">
							</div>
							<div class="form-group">
								<label for="postBody">Body</label>
								<div id="postBody"></div>
							</div>
							<div class="list-group search-words-group well">
								<input type="text" id="tag_word" placeholder="Input tag name here" class="form-control selectized">
							</div>
							<button type="submit" id="add_post" class="btn btn-default">Add</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />		
		<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/jquery-ui-1.8.13.custom.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
		<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/elrte.full.js"></script>
		<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/i18n/elrte.en.js"></script>
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/vendor/selectize.js"></script>
		<script type="text/javascript">		
		$( document ).ready(function() {
	        var opts = {
	            lang         : 'en',   // set your language
	            styleWithCSS : false,
	            height       : 600,
	            toolbar      : 'maxi'
	        };
	        $('#tag_word').selectize({
	        	valueField: 'name',
	            labelField: 'name',
	            searchField: 'name',
	            options: [],
	            delimiter: ',',
			    persist: false,
			 	plugins: ['restore_on_backspace', 'remove_button'],
			    create: false,
			    render: {
			        option: function(item, escape) {
			            return '<div>' +
			                '<span class="title">' +
			                    '<span class="by">' + escape(item.name) + '</span>' +
			                '</span>' +
			            '</div>';
			        }
			    },
		     	maxItems: 12,
		     	load: function(query, callback) {
		         	if (!query.length) return callback();
		         	$.ajax({
	             		url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/tags/search",
			            type: 'POST',
			            data: {tagName: query},
			            error: function() {
			                callback();
			            },
			            success: function(res) {
			                callback(res);
			            }
			         });
			     }
			});
	        // create editor
	        $('#postBody').elrte(opts);
	        $("#add_post").click(function() {
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/posts/add_post",
					data: {userId: selectedUserId, commentText: $("#commentText").val()}
				});
			});
	    });
		</script>
	