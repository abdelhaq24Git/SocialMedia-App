<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InstaFace</title>
<%@include file="../include/head.jsp"%>
</head>
<body>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">Création de compte</div>
			<div class="card-body">
				<form action="signup-servlet2" method="POST"
					enctype="multipart/form-data">
					<div class="form-group">
						<div class="form-group">
							<label for="image">Votre Photo:</label> <input
								class="form-control-file" type="file" name="photo" id="photo" />
						</div>
						<div class="form-group">
							<label for id="nom">Nom : </label> <input type="text"
								class="form-control" placeholder="Entrez votre nom" name="nom"
								required>
						</div>
					</div>
					<div class="form-group">
						<label for id="prenom">Prénom:</label> <input type="text"
							class="form-control" placeholder="Entrez votre prénom"
							name="prenom" required>
					</div>
					<div class="form-group">
						<label for id="email">L'addresse email:</label> <input
							type="email" class="form-control"
							placeholder="Entrez votre email" name="email" required>
					</div>
					<div class="form-group">
						<label for id="mdp">Mot de passe:</label> <input type="password"
							class="form-control" placeholder="Entrez votre mot de passe"
							name="mdp" required>
					</div>
					<div>
						<button type="submit" class="btn btn-primary">S'inscrire</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</body>
</html>