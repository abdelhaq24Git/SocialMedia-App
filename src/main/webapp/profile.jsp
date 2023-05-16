<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="model.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="connection.BD"%>
<%@page import="dao.PostDao"%>
<%@page import="model.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Utilisateur user = (Utilisateur) session.getAttribute("user");
int userId = user.getId();
int postId = -1;
if (user == null) {
	response.sendRedirect("login.jsp");
}
PostDao pdao = new PostDao(BD.getConnection());
List<Post> posts = pdao.getPosts(user);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InstaFace</title>
<%@include file="../include/head.jsp"%>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="#">InstaFace</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="Home">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="ProfileServlet">Profile</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="flux">Flux
						d'actualité</a></li>
				<li class="nav-item"><a class="nav-link" href="trending">Trending</a>
				</li>

			</ul>
			<a class="nav-link mr-0 text-gray" href="logout">Déconnexion</a>
			<form class="form-inline my-2 my-lg-0">
				<input oninput="chercher()" id="searchName" name="searchName"
					class="form-control mr-sm-2" type="search" placeholder="Search"
					aria-label="Search">
				<button onclick="chercher()" id="searchUser"
					class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>

		</div>

	</nav>
	<div class="d-flex justify-content-end">
		<div id="searchResults" class="" style="padding-right: 15rem;"></div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-4 mt-2">
				<div class="card" style="width: 18rem;">
					<img class="card-img-top rounded-circle"
						src="data:image/jpeg;base64,<%=(String) session.getAttribute("photo")%>"
						alt="Card image cap">

					<div class="card-body text-center">
						<h5 class="card-title"><%=user.getNom()%>
							<%=user.getPrénom()%></h5>
						<a href="edit" class="btn btn-primary">Edit</a>

					</div>
				</div>
			</div>
			<div class="col-8">
				<form action="publication" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<input class="form-control" type="hidden"
							value="<%=user.getEmail()%>" name="email" /> <input
							type="hidden" value="<%=user.getMdp()%>" name="mdp" /> <label
							for="contenu">Content:</label>
						<textarea class="form-control" name="contenu" id="contenu"></textarea>
					</div>
					<div class="d-flex">
						<div class="form-group">
							<label for="image">Image:</label> <input
								class="form-control-file" type="file" name="image" id="image" />
						</div>
						<input type="submit" value="Publish"
							class="btn h-50 align-self-center btn-primary" />
					</div>


				</form>

				<%
				for (Post post : posts) {
					postId = post.getId();
					BufferedImage image = ImageIO.read(post.getImage());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(image, "jpg", baos);
					byte[] imageData = baos.toByteArray();
					String base64Image = Base64.getEncoder().encodeToString(imageData);
				%>
				<div class="card m-3" style="width: 100%;">
					<div class="card-body">
						<h5 class="card-title">
							<img class="card-img-top"
								src="data:image/jpeg;base64,<%=(String) session.getAttribute("photo")%>"
								alt="Card image cap" style="width: 50px; height: 50px">
							<%=user.getNom()%></h5>
						<p class="card-text"><%=post.getContenu()%></p>
					</div>
					<img class="card-img-top"
						src="data:image/jpeg;base64,<%=base64Image%>" alt="Card image cap">
					<div class="card-body">
						<button href="#" class="btn btn-light">J'aime</button>
						<button href="#" class="btn btn-primary">Commenter</button>
					</div>
				</div>

				<%
				}
				%>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
	<script type="text/javascript">
		
		
	function like() {
		var postId = '<%=postId%>';
		var userId = '<%=userId%>';
			const button = document.getElementById("like");
			if (button.classList.contains("btn-light")) {
				button.classList.remove("btn-light");
				button.classList.add("btn-primary");
			} else {
				button.classList.remove("btn-primary");
				button.classList.add("btn-light");
			}
			var xhr = new XMLHttpRequest();
			var url = "like"; // Replace with the URL of your servlet
			var params = "postId=" + encodeURIComponent(postId) + "&userId="
					+ encodeURIComponent(userId);
			xhr.open("GET", url + "?" + params, true);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					// Handle the response here
					var response = JSON.parse(xhr.responseText); // If the response is in JSON format
					// Access the parameters of the request from the response object
					var param1Value = response.postId;
					var param2Value = response.userId;
				}
			};
			xhr.send();
		}
		function chercher() {
			var name = document.getElementById('searchName').value;
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					var results = JSON.parse(xhr.responseText);
					console.log(results);
					var list = '<ul>';
					results.forEach(function(user) {
						list += '<li><a href="utilisateur?id='
								+ encodeURIComponent(user.id) + '">' + user.nom
								+ '</a></li>';
					})
					list += '</ul>';
					var searchResults = document
							.getElementById('searchResults');
					searchResults.innerHTML = list;
					var ul = searchResults.getElementsByTagName('ul');
					ul.style.display = 'none';
					searchResults
							.addEventListener(
									'click',
									function() {
										ul.style.display = ul.style.display === 'none' ? 'block'
												: 'none';
									});
				}
			};
			xhr.open('POST', 'search', true);
			xhr.setRequestHeader('Content-type',
					'application/x-www-form-urlencoded');
			console.log(name);
			xhr.send('searchName=' + name);
		}
	</script>
</body>
</html>