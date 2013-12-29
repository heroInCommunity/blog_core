<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.model.User" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/jquery-ui-1.10.3.custom.min.css">
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
								<label for="userEmail">User email</label> <input
									class="form-control" id="userEmail"
									placeholder="Enter user email">
							</div>
							<div class="form-group">
								<label for="commentText">Comment text</label> <input
									class="form-control" id="commentText"
									placeholder="Enter comment text">
							</div>
							<button type="submit" id="add_comment" class="btn btn-default">Add</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />		
		<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/jquery-ui-1.10.3.custom.min.js"></script>		
		<script type="text/javascript">		
		$( document ).ready(function() {
			var selectedUserId;
			$("#userEmail").autocomplete({
				source: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/users/search",
				minLength: 2,
				select: function( event, ui ) {
					$("#userEmail").val(ui.item.name);
					selectedUserId = ui.item.id;
				}
			}).data( "ui-autocomplete" ).
			_renderItem = function( ul, item ) {
				return $( "<li>" )
				.attr( "data-value", item.id )
				.append( $( "<a>" ).text( item.name ) )
				.appendTo( ul );
			};
			$.ajaxSetup({
				type: "POST",
				dataType: "json",
				mimeType: "application/json"
			});
			$("#add_comment").click(function() {
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/comments/add_comment",
					data: {userId: selectedUserId, commentText: $("#commentText").val()}
				});
			});
		});
		</script>
	