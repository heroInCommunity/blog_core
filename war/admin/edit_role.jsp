<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="name.heroin.community.model.MenuItem"%>
<%@ page import="name.heroin.community.model.Role"%>
<%@ page import="name.heroin.community.model.Permission"%>
<%@ page import="name.heroin.community.constants.AttributeName"%>
<jsp:include page="header.jsp" >
	<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
</jsp:include>
<link rel="stylesheet"
	href="<%=request.getAttribute(AttributeName.BASE_URL.value())%>css/selectize.css">
<link rel="stylesheet"
	href="<%=request.getAttribute(AttributeName.BASE_URL.value())%>css/selectize.bootstrap3.css">
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
								value="<%=( (Role) request.getAttribute(AttributeName.ROLE.value()) ).getName()%>">
						</div>
						<div class="list-group search-words-group well">
							<input type="text" id="permission_word"
								value="<%
								int count = 0;
								for(Permission permission : ( (Role) request.getAttribute(AttributeName.ROLE.value()) ).getPermissions()) {
									if(count != 0) {
										out.print(",");
									}
									out.print(permission.getName());
									count++;
								}
								%>"
								class="form-control selectized">
						</div>
						<button type="submit" id="edit_role" class="btn btn-default">Update</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" >
		<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
	</jsp:include>
	<script
		src="<%=request.getAttribute(AttributeName.BASE_URL.value())%>js/vendor/selectize.js"></script>
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
	            delimiter: ',',
			    persist: false,
			 	plugins: ['restore_on_backspace', 'remove_button'],
			 	preload: false,
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
		         	if (!query.length) callback();
		         	$.ajax({
	             		url: "<%=request.getAttribute(AttributeName.BASE_URL.value())%>" + "api/permissions/search",
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
			$("#edit_role").click(function() {
				var permissionIds = [
					<%
					count = 0;
					for(Permission permission : ( (Role) request.getAttribute(AttributeName.ROLE.value()) ).getPermissions()) {
						if(count != 0) {
							out.print(",");
						}
						out.print(permission.getId());
						count++;
					}
					%>                     
                ];
	        	$('div.selectize-input > div.item').each(function() {
	        		if( ! isNaN($(this).attr('data-value')))
	        			permissionIds.push($(this).attr('data-value'));
	        	});
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value())%>" + "api/roles/edit_role",
					data: {
						id: "<%=( (Role) request.getAttribute(AttributeName.ROLE.value()) ).getId()%>",
						roleName : $("#roleName").val(),
						permissions : permissionIds
					}
				});
			});
		});
	</script>