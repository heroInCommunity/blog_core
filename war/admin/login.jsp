<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="name.heroin.community.model.MenuItem" %>
<%@ page import="name.heroin.community.constants.MenuName" %>
<%@ page import="name.heroin.community.constants.AttributeName" %>
<link rel="stylesheet" href="../css/signin.css">
<jsp:include page="header.jsp" >
	<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
</jsp:include>
		<div class="container">
			<div class="form-signin" role="form">
				<h2 class="form-signin-heading">Please sign in</h2>
				<input type="text" id="email" class="form-control" placeholder="Email address" required autofocus> 
				<input type="password" id ="password" class="form-control" placeholder="Password" required>
				<label class="checkbox"> 
					<input id="rememberme" type="checkbox" value="remember-me"> Remember me
				</label>
				<button id="login" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			</div>
		</div>
		<div class="container">
		<jsp:include page="footer.jsp" >
			<jsp:param name="base_url" value="${request['AttributeName.BASE_URL.value()'] }" />
		</jsp:include>
		<script type="text/javascript">
		$.ajaxSetup({
			type: 'POST',
			dataType: 'json',
			mimeType: 'application/json'
		});
		
		$( document ).ready(function() {
			$('#login').click(function() {
				$.ajax({
					url: "<%=request.getAttribute(AttributeName.BASE_URL.value()) %>" + "login",
					data: {
						email: $('#email').val(),
						password: $('#password').val(),
						rememberme: $('#rememberme').is(':checked'),
					}
				});
				$( document ).ajaxComplete(function() {
					console.log("efw");
	            	window.location.href = "<%=request.getAttribute(AttributeName.BASE_URL.value()) + MenuName.ADMIN_INDEX.value() %>";
				});
			});
		});
		</script>
