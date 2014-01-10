<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
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
						<li>
							<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %><%=topMenuItem.getUrl()%>"><%=topMenuItem.getName()%></a>
							<ul class="nav">
								<%
									for (MenuItem subMenuItem : menus.get(topMenuItem)) {
								%>
								<li>
									<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %><%=subMenuItem.getUrl()%>"><%=subMenuItem.getName()%></a>
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
		</div>
		<jsp:include page="footer.jsp" >
			<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
		</jsp:include>
