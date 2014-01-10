<%@ page import="name.heroin.community.constants.AttributeName" %>			
			<footer>
				<p>
					&copy; Joe 2013
				</p>
			</footer>

		</div><!--/.container-->

		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script>
			window.jQuery || document.write('<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/vendor/jquery-1.10.2.min.js"><\/script>')
		</script>
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/plugins.js"></script>
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/bootstrap.min.js"></script>
		<script src="<%=request.getAttribute(AttributeName.BASE_URL.value()) %>js/main.js"></script>
	</body>
</html>