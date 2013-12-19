<!DOCTYPE html>
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
		<link rel="stylesheet" href="../css/elrte.full.css">
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
							<li class="">
								<a href="#download">Download Bootstrap</a>
								<ul class="nav">
									<li class="">
										<a href="#download-compiled">Compiled CSS, JS, and fonts</a>
									</li>
								</ul>
							</li>
							<li class="active">
								<a href="admin/permissions">Permissions</a>
								<ul class="nav">
									<li class="active">
										<a href="admin/permissions">View permissions</a>
									</li>
									<li class="">
										<a href="admin/add_permission">Add permission</a>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<div class="container" role="main">
					<div class="row row-offcanvas row-offcanvas-right">
						<div class="col-xs-12 col-sm-9">
							<div id="elrte_here"></div>
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
		<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
		<script src="../js/elrte.full.js"></script>
		<script src="../js/i18n/elrte.en.js"></script>
		<script src="../js/main.js"></script>
		
		<script type="text/javascript" charset="utf-8">
		    $( document ).ready(function() {
		        var opts = {
		            lang         : 'en',   // set your language
		            styleWithCSS : false,
		            height       : 600,
		            toolbar      : 'maxi'
		        };
		        // create editor
		        $('#elrte_here').elrte(opts);
		
		        // or this way
		        // var editor = new elRTE(document.getElementById('our-element'), opts);
		    });
		</script>
	</body>
</html>