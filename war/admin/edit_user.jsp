<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.model.Role" %>
<%@ page import="name.heroin.community.model.User" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" >
	<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
</jsp:include>
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
								<label for="userName">User name</label> <input
									class="form-control" id="userName"
									value="<%=( (User) request.getAttribute(AttributeName.USER.value()) ).getName()%>">
							</div>
							<div class="form-group">
								<label for="userEmail">Email</label> <input
									class="form-control" id="userEmail"
									value="<%=( (User) request.getAttribute(AttributeName.USER.value()) ).getEmail()%>">
							</div>
							<div class="form-group">
								<label for="userRole">User role</label> <select
									class="form-control" id="userRole">
									<%
									for(Role role : (List<Role>) request.getAttribute(AttributeName.LIST_OF_ROLES.value()) ) {
									%>
									<option value="<%=role.getId()%>" 
									<%if(role.getId() == ( (User) request.getAttribute(AttributeName.USER.value()) ).getRole().getId()) {%>selected="selected"<% } %>
									><%=role.getName()%></option>
									<%
									}
									%>
								</select>
							</div>
							<button type="submit" id="edit_user" class="btn btn-default">Update</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" >
			<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
		</jsp:include>
		<script type="text/javascript">
		$.ajaxSetup({
			type: "POST",
			dataType: "json",
			mimeType: "application/json"
		});
		
		$( document ).ready(function() {
			$("#edit_user").click(function() {
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/users/edit_user",
					data: {
						id: "<%=( (User) request.getAttribute(AttributeName.USER.value()) ).getId()%>",
						userName: $("#userName").val(), 
						userEmail: $("#userEmail").val(), userRole: $("#userRole").val()
					}
				});
			});
		});
		</script>
