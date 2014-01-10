<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.model.Role" %>
<%@ page import="name.heroin.community.model.Permission" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" />
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
								<label for="permissionName">Permission name</label> <input
									class="form-control" id="permissionName"
									value="<%=( (Permission) request.getAttribute(AttributeName.PERMISSION.value()) ).getName() %>">
							</div>
							<button type="submit" id="edit_permission" class="btn btn-default">Update</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />		
		<script type="text/javascript">
		$.ajaxSetup({
			type: "POST",
			dataType: "json",
			mimeType: "application/json"
		});
		
		$( document ).ready(function() {
			$("#edit_permission").click(function() {
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/permissions/edit_permission",
					data: {id: "<%=( (Permission) request.getAttribute(AttributeName.PERMISSION.value()) ).getId() %>", permissionName: $("#permissionName").val()}
				});
			});
		});
		</script>
	