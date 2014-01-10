<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<jsp:include page="header.jsp" >
	<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
</jsp:include>
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/start/jquery-ui-1.10.3.custom.min.css">
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/jquery.dataTables.css" />
<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/admin.css" />
		<div class="container">
			<div class="row">
				<div class="col-md-3 well">
					<div class="bs-sidebar hidden-print affix-top" role="complementary">
						<ul class="nav bs-sidenav">
							<%
							Map<MenuItem, List<MenuItem>> menus = (Map) request.getAttribute(AttributeName.MENUS.value());
							if (menus != null)
							for (MenuItem topMenuItem : menus.keySet()) {							
							%>						
							<li class="<% if (((Integer) topMenuItem.getId()).equals(request.getAttribute(AttributeName.TOP_LEVEL_MENU_ID.value()))) out.print("active"); %>">
								<a href="../<%=topMenuItem.getUrl()%>"><%=topMenuItem.getName() %></a>
								<ul class="nav">
									<%
									for (MenuItem subMenuItem : menus.get(topMenuItem)) {
									%>								
									<li class="<% if (((Integer) subMenuItem.getId()).equals(request.getAttribute(AttributeName.SUB_LEVEL_MENU_ID.value()))) out.print("active"); %>">
										<a href="../<%=subMenuItem.getUrl()%>"><%=subMenuItem.getName() %></a>
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
							<table cellpadding="0" cellspacing="0" id="data_table" class="wide" style="display:none;">
								<thead>								
									<tr>
										<th align="center" valign="middle"><input type="checkbox" class="ids_all" /></th>
										<th align="left">Name</th>
										<th align="left">Email</th>
										<th align="left">Role</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
<jsp:include page="footer.jsp" >
	<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
</jsp:include>	
<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
var idsAll = $('.ids_all');
var dataTable = $('#data_table');
var tableObj;
var showLength = false;
var displayLength = 10;

function initComplete(oSettings) {
	idsAll.parent().css('margin-left', '10px');
	if(oSettings.fnRecordsTotal() > displayLength) {
		$('#data_table_length').show();
		showLength = true;
	}
	tableObj.show();
}

function tableDraw(oSettings) {
	if (oSettings.fnRecordsDisplay() == 0) {
		dataTable.find('tbody tr td').html('No data returned for this view');
		$('#data_table_length').hide();
		$('#data_table_paginate').hide();
		dataTable.find('thead').hide();
	} else {
		if (oSettings.fnRecordsDisplay() <= oSettings._iDisplayLength) {
			$('#data_table_paginate').hide();
		} else {
			$('#data_table_paginate').show();
		}
		if (showLength) {
			$('#data_table_length').show();
		} else {
			$('#data_table_length').hide();
		}
		dataTable.find('thead').show();
	}
	idsAll.removeAttr('checked');
}

$(document).ready(function() {
	tableObj = dataTable.dataTable({
		'bJQueryUI': true,
		'sPaginationType': 'full_numbers',
		'aaSorting': [ [ 1, 'asc' ] ],
		'sDom': '<"H"flpr>t<"F"i>',
		'aoColumnDefs': [
         	{ 'sClass': 'center width_percent_10', 'aTargets': [ 0 ] },
         	{ 'sClass': 'left width_percent_30', 'aTargets': [ 1 ] },
         	{ 'sClass': 'left width_percent_30', 'aTargets': [ 2 ] },
         	{ 'sClass': 'left width_percent_30', 'aTargets': [ 3 ] },
         	{ 'bSortable': false, 'aTargets': [ 0, 1, 2, 3 ] }
         ],
         'bServerSide': true,
         'bProcessing': true,
         'sAjaxSource': "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "api/users/get_users",
         'fnInitComplete': initComplete,
         'fnDrawCallback': tableDraw
	});
	$('.dataTables_filter input').unbind('keypress keyup').bind('keypress keyup', function(event) {
		if ($(this).val().length < 3 && event.keyCode != 13) return;
		tableObj.fnFilter($(this).val());
	});
	idsAll.click(function() {
		var value = idsAll.is(':checked');
		$('.ids').each(function() {
			this.checked = value;
		});
	});
});
</script>