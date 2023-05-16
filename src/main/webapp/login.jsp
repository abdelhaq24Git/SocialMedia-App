<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InstaFace</title>
<%@include file="../include/head.jsp" %>
</head>
<body>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="login" method="POST">
					<div class="form-group">
						<label>L'addresse email</label> <input type="email"
							class="form-control" placeholder="Enter your email"
							name="login-email" required>
					</div>
					<div class="form-group">
						<label>Mot de passe</label> <input type="password"
							class="form-control" placeholder="Enter your password"
							name="login-password" required>
					</div>
					<div>
						<button type="submit" class="btn btn-primary">Login</button>
						<a href="Signup.jsp" class="btn btn-primary">S'inscrire</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp" %>
</body>
</html>