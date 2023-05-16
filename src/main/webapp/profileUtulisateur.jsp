<%@page import="dao.CommentaireDao"%>
<%@page import="dao.UtilisateurDao"%>
<%@page import="model.Commentaire"%>
<%@page import="model.Like"%>
<%@page import="dao.LikeDao"%>
<%@page import="model.Follow"%>
<%@page import="dao.FollowDao"%>
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
Utilisateur visited = (Utilisateur) request.getAttribute("visited");
Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
String userPhoto = (String) request.getSession().getAttribute("photo");
int userId = user.getId();
int postId=-1;
FollowDao fdao = new FollowDao(BD.getConnection());
UtilisateurDao udao = new UtilisateurDao(BD.getConnection());
Follow f = fdao.find(user.getId(), visited.getId());
String colorFollowBtn = f == null ? "btn-light" : "btn-primary";
String textFollowBtn = f == null ? "Follow" : "Following";
String profilePhoto = (String) request.getAttribute("profilePhoto");
if (user == null) {
	response.sendRedirect("login.jsp");
}
PostDao pdao = new PostDao(BD.getConnection());
List<Post> posts = pdao.getPosts(visited);
LikeDao ldao = new LikeDao(BD.getConnection());
Like like = null;
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
			<div class="col-4">
				<div class="card" style="width: 18rem;">
					<img class="card-img-top rounded-circle"
						src="data:image/jpeg;base64,<%=profilePhoto%>"
						alt="Card image cap">
					<div class="card-body text-center">
						<h5 class="card-title"><%=visited.getNom()%>
							<%=visited.getPrénom()%></h5>
						<a id="follow" class="btn <%=colorFollowBtn%>" onclick="follow()"><%=textFollowBtn%></a>

					</div>
				</div>
			</div>
			<div class="col-8">


				<%
				for (Post post : posts) {
					postId = post.getId();
					CommentaireDao cdao = new CommentaireDao(BD.getConnection());
					List<Commentaire> commentaires = cdao.getComments(postId);
					BufferedImage image = ImageIO.read(post.getImage());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(image, "jpg", baos);
					byte[] imageData = baos.toByteArray();
					String base64Image = Base64.getEncoder().encodeToString(imageData);
					like = ldao.findLike(postId, userId);
					String color = like == null ? "btn-light" : "btn-primary";
				%>
				<div class="card m-3" style="width: 100%;">
					<div class="card-body">
						<h5 class="card-title">
							<img class="card-img-top"
								src="data:image/jpeg;base64,<%=profilePhoto%>"
								alt="Card image cap" style="width: 50px; height: 50px">
							<%=visited.getNom()%></h5>
						<p class="card-text"><%=post.getContenu()%></p>
					</div>
					<img class="card-img-top"
						src="data:image/jpeg;base64,<%=base64Image%>" alt="Card image cap">
					<div class="card-body">
						<button id="like" href="#" class="btn <%=color%>" onclick="like()">J'aime</button>
					</div>
					<div>
				<%
				for (Commentaire c : commentaires) {
					int utilisateurComment = c.getUtilisateur();
					Utilisateur u = udao.getUserById(utilisateurComment);
				%>
				<div class="d-flex">
					<img class="card-img-top mx-3"
						src="data:image/jpeg;base64,<%=u.getDisplayableImage()%>"
						alt="Card image cap" style="width: 30px; height: 30px">
						<a
						href="utilisateur?id=<%=u.getId()%>"><%=u.getNom()%> 
						<%=u.getPrénom()%></a>
					<p>: <%=c.getCommentaire()%></p>
				</div>

				<%
				}
				%>
			</div>
				</div>
				<form id=<%="id"+postId%> method="post" class="commentForm mb-4"
					data-user-id=<%=userId%> data-post-id=<%=postId%>>
					<input class="form-control" type="hidden" value="<%=user.getId()%>"
						name="userId" /> <input type="hidden" value="<%=postId%>"
						name="postId" />
					<div class="d-flex">
						<img class="card-img-top"
							src="data:image/jpeg;base64,<%=userPhoto%>" alt="Card image cap"
							style="width: 50px; height: 50px"> <input
							class="form-control w-100" name="comment" id="comment" />
						<button id="commenter" type="submit" value="Comment"
							class="btn h-50 align-self-center btn-primary px-3 mx-3">Comment</button>
					</div>
				</form>
				<%
				}
				%>

			</div>
		</div>
	</div>
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
		function follow(){
			const actuel='<%=user.getId()%>';
			const user='<%=visited.getId()%>';
			const text='<%=textFollowBtn%>';
			const button = document.getElementById("follow");
			if (button.classList.contains("btn-light")) {
				button.classList.remove("btn-light");
				button.classList.add("btn-primary");
				button.innerText = "Following";
			} else {
				button.classList.remove("btn-primary");
				button.classList.add("btn-light");
				button.innerText = "Follow";
			}
			var xhr = new XMLHttpRequest();
			var url = "follow"; // Replace with the URL of your servlet
			var params = "actuel=" + encodeURIComponent(actuel) + "&user="
					+ encodeURIComponent(user);
			xhr.open("GET", url + "?" + params, true);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					console.log("OK")
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
		var id='id'+'<%=postId%>';
		document.querySelector("#"+id).addEventListener("submit", function(event) {
		    event.preventDefault(); // Prevent default form submission
			const userId = this.dataset.userId;
		    const postId = this.dataset.postId;
		    console.log(userId);
		    console.log(postId);
			const comment=document.getElementById("comment").value;
			var xhr = new XMLHttpRequest();
			xhr.open('POST', 'comment');
			xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			xhr.onload = function() {
				  if (xhr.status === 200) {
					  var response = JSON.parse(xhr.responseText);
				  } else {
				    // Handle errors
				  }
				};
			xhr.send('comment=' + encodeURIComponent(comment) + '&userId=' + encodeURIComponent(userId) + '&postId=' + encodeURIComponent(postId));
			document.getElementById("comment").value = "";
		});
	</script>
	<%@include file="../include/footer.jsp"%>
</body>
</html>