<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.model.Role" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" />
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
								<label for="roleName">Role name</label> <input
									class="form-control" id="roleName"
									placeholder="Enter role name">
							</div>
							<div class="list-group search-words-group well">
								<input type="text" id="permission_word" placeholder="Input permission name here" class="form-control selectized">
							</div>
							<button type="submit" id="add_role" class="btn btn-default">Add</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/vendor/selectize.js"></script>
		<script type="text/javascript">
		$.ajaxSetup({
			type: "POST",
			dataType: "json",
			mimeType: "application/json"
		});
		
		$( document ).ready(function() {
	        $('#permission_word').selectize({
	        	valueField: 'id',
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
	             		url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/permissions/search",
			            data: {permissionName: query},
			            error: function() {
			                callback();
			            },
			            success: function(res) {
			                callback(res);
			            }
			         });
			     }
			});
			$("#add_role").click(function() {
				var permissionIds = [];
	        	$('div.selectize-input > div.item').each(function() {
	        		permissionIds.push($(this).attr('data-value'));
	        	});
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/roles/add_role",
					data: {roleName: $("#roleName").val(), permissions: permissionIds}
				});
			});
		});
		</script>
