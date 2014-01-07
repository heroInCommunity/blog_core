<!DOCTYPE html>
<%@page import="name.heroin.community.utils.std.Utils"%>
<%@page import="name.heroin.community.constants.AttributeName"%>
<%@page import="name.heroin.community.model.SlimPost"%>
<%@page import="name.heroin.community.model.Post"%>
<%@page import="java.util.List"%>
<html class="no-js">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>Blog main page</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->

		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="css/selectize.css">
		<link rel="stylesheet" href="css/selectize.bootstrap3.css">
		<link rel="stylesheet" href="css/syntaxhighlighter/shCoreEclipse.css">
		<link rel="stylesheet" href="css/main.css">
		<script src="js/vendor/modernizr-2.7.0.min.js"></script>
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
					<a class="navbar-brand" href="main">Joe's blog</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="main">Home</a>
						</li>
						<li>
							<a href="projects.html">Projects</a>
						</li>
						<li>
							<a href="about.html">About</a>
						</li>
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>

		<div class="container">

			<div class="row row-offcanvas row-offcanvas-right">
				
				<div class="col-md-3 col-xs-4 page-left-bar" id="page_left_bar">
					<div class="list-group search-words-group well">
						<input type="text" id="search-word" placeholder="Input search keyword here" class="form-control selectized">
					</div>

					<div class="sidebar-offcanvas well sidebar" id="sidebar" role="navigation">
						<div class="list-group article-titles">
							<%
							for(SlimPost post : (List<SlimPost>) request.getAttribute(AttributeName.POST_TITLES.value())) {
							%>
							<a href="#" class="list-group-item article-date-name">
								<div class="article-date"><%=Utils.getDisplayDate(post.getTimestamp()) %>:</div>&nbsp;<%=post.getTitle()%>
							</a>
							<%
							}
							%>
						</div>
					</div><!--/span-->
				</div>

				<div class="col-xs-12 col-sm-9 well article" id="page_central_bar">
					<p class="pull-right visible-xs">
						<button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">
							Toggle nav
						</button>
					</p>
					<div class="jumbotron">
						<h1 class="article-title"><%=( (Post)request.getAttribute(AttributeName.LATEST_POST.value()) ).getTitle() %></h1>
						<p>
							<%=( (Post)request.getAttribute(AttributeName.LATEST_POST.value()) ).getDescription() %>
						</p>
						<p class="fullscreen-mode" id="clicker_full">view in fullscreen mode</p>
						<p class="fullscreen-mode" id="clicker_less" style="display: none;">view in standard mode</p>
					</div>
					<div class="progress" id="progressbar_nav">
						<div class="progress-bar" role="progressbar" id="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
							<span class="sr-only">60% Complete</span>
						</div>
					</div>
					<div class="row article-content" id="scrollable">
						<div class="col-md-12">
							<%=( (Post)request.getAttribute(AttributeName.LATEST_POST.value()) ).getBody() %>
						</div><!--/span-->
					</div><!--/row-->
				</div><!--/span-->
			</div><!--/row-->

			<hr>

			<footer>
				<p>
					&copy; Joe 2013
				</p>
			</footer>

		</div><!--/.container-->

		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script>
			window.jQuery || document.write('<script src="js/vendor/jquery-1.10.2.min.js"><\/script>')
		</script>
		<script src="js/plugins.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/vendor/selectize.js"></script>
		<script src="js/vendor/shCore.js"></script>
		<script src="js/vendor/shAutoloader.js"></script>
		<script src="js/vendor/shBrushJScript.js"></script>
		<script>
			SyntaxHighlighter.all()
		</script>
		<script src="js/main.js"></script>
		<script>
			$( document ).ready(function() {
				$.ajaxSetup({
					type: "POST",
					dataType: "json",
					mimeType: "application/json"
				});
				
				//search words on main page
				$('#search-word').selectize({
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
		             		url: "<%=request.getAttribute(AttributeName.BASE_URL.value())%>" + "api/tags/search",
				            data: {tagName: query},
				            error: function() {
				                callback();
				            },
				            success: function(res) {
				                callback(res);
				                var tagIds = [];
				                for(tagId in tagIds) {
				                	
				                }
				                $.ajax({
				             		url: "<%=request.getAttribute(AttributeName.BASE_URL.value())%>" + "api/posts/get_post_titles",
						            data: {
						            	tagName: query
					            	},
						            error: function() {
						                callback();
						            },
						            success: function(res) {
						                callback(res);
						            }
						         });
				            }
				         });
				     }
				});
		});
		</script>
	</body>
</html>
