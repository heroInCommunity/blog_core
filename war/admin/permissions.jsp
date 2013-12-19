<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<html class="no-js">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>My projects</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->

		<link rel="stylesheet" href="../css/bootstrap.css">
		<link rel="stylesheet" href="../css/jquery-ui-1.8.13.custom.css">
		<link rel="stylesheet" href="../css/main.css">
		<script src="../js/vendor/modernizr-2.7.0.min.js"></script>
	</head>
	<body>
		<!--[if lt IE 8]>
		<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
		<![endif]-->
		
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="../main.html">Joe's blog</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href="../main.html">Home</a>
						</li>
						<li>
							<a href="../projects.html">Projects</a>
						</li>
						<li>
							<a href="../about.html">About</a>
						</li>						
						<li class="active">
							<a href="admin/index.html">Admin</a>
						</li>
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-md-3 well">
					<div class="bs-sidebar hidden-print affix-top" role="complementary">
						<ul class="nav bs-sidenav">
							<%
							Map<MenuItem, List<MenuItem>> menus = (Map) request.getAttribute("menus");
							if (menus != null)
							for (MenuItem topMenuItem : menus.keySet()) {							
							%>						
							<li class="<% if (((Integer) topMenuItem.getId()).equals(request.getAttribute("topLevelMenuId"))) out.print("active"); %>">
								<a href="<%=topMenuItem.getUrl()%>"><%=topMenuItem.getName() %></a>
								<ul class="nav">
									<%
									for (MenuItem subMenuItem : menus.get(topMenuItem)) {
									%>								
									<li class="<% if (((Integer) subMenuItem.getId()).equals(request.getAttribute("subLevelMenuId"))) out.print("active"); %>">
										<a href="<%=subMenuItem.getUrl()%>"><%=subMenuItem.getName() %></a>
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
						<div class="col-xs-12 col-sm-9 well">
							
						</div>
					</div>
				</div>
			</div>

			<footer>
				<p>
					&copy; Joe 2013
				</p>
			</footer>

		</div><!--/.container-->

		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script>
			window.jQuery || document.write('<script src="../js/vendor/jquery-1.10.2.min.js"><\/script>')
		</script>
		<script src="../js/plugins.js"></script>
		<script src="../js/bootstrap.min.js"></script>
		<script src="../js/jquery-ui-1.8.13.custom.min.js"></script>
		<script src="../js/main.js"></script>
	</body>
</html>