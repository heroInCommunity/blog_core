<%@ page import="name.heroin.community.constants.AttributeName" %>
<!DOCTYPE html>
<html class="no-js">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>My projects</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->

		<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/bootstrap.css">
		<link rel="stylesheet" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>css/main.css">
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/vendor/modernizr-2.7.0.min.js"></script>
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
					<a class="navbar-brand" href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>main.html">Joe's blog</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>main.html">Home</a>
						</li>
						<li>
							<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>projects.html">Projects</a>
						</li>
						<li>
							<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>about.html">About</a>
						</li>						
						<li class="active">
							<a href="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>admin/index.html">Admin</a>
						</li>
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>