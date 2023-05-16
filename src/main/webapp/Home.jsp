<%@page import="dao.UtilisateurDao"%>
<%@page import="dao.CommentaireDao"%>
<%@page import="model.Commentaire"%>
<%@page import="java.util.List"%>
<%@page import="model.Like"%>
<%@page import="connection.BD"%>
<%@page import="dao.LikeDao"%>
<%@page import="model.Post"%>
<%@page import="java.util.Map"%>
<%@page import="model.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Utilisateur UtilisateurActuel = (Utilisateur) request.getSession().getAttribute("user");
String photo = (String) request.getSession().getAttribute("photo");
Map<Utilisateur, Post> UserPost = (Map<Utilisateur, Post>) session.getAttribute("allPosts");
int userId = UtilisateurActuel.getId();
int postId = 0;
Post post = new Post();
UtilisateurDao udao = new UtilisateurDao(BD.getConnection());
LikeDao ldao = new LikeDao(BD.getConnection());
Like like = null;
/*for (Map.Entry<Utilisateur, Post> entry : UserPost.entrySet()) {
	out.println(entry.getKey().getDisplayableImage());
}*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InstaFace</title>
<%@include file="../include/head.jsp"%>
</head>
<body>
	<%@include file="../include/navbar.jsp"%>
	<div class="container">
		<%
		for (Map.Entry<Utilisateur, Post> entry : UserPost.entrySet()) {
			postId = entry.getValue().getId();
			CommentaireDao cdao = new CommentaireDao(BD.getConnection());
			List<Commentaire> commentaires = cdao.getComments(postId);
			like = ldao.findLike(postId, userId);
			String color = like == null ? "btn-light" : "btn-primary";
			int user = entry.getValue().getUtilisateur();
		%>
		<div class="card m-3" style="width: 100%;">
			<div class="card-body">
				<h5 class="card-title">
					<img class="card-img-top"
						src="data:image/jpeg;base64,<%=entry.getKey().getDisplayableImage()%>"
						alt="Card image cap" style="width: 50px; height: 50px"> <a
						href="utilisateur?id=<%=entry.getKey().getId()%>"><%=entry.getKey().getNom()%>
						<%=entry.getKey().getPrénom()%></a>
				</h5>
				<p class="card-text"><%=entry.getValue().getContenu()%></p>
			</div>
			<img class="card-img-top"
				src="data:image/jpeg;base64,<%=entry.getValue().getDisplayableImage()%>"
				alt="Card image cap">
			<div class="card-body">

				<%--<a id="like" href="like?postId=<%=postId%>&&userId=<%=userId%>" class="btn btn-light" onclick="like()">J'aime</a>--%>
				<a id="like" class="btn <%=color%>"
					onclick="like(<%=userId%>,<%=postId%>)">J'aime</a> <a href="#"
					class="btn btn-primary">Commentaires</a>

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
		<form id=<%="id"+postId%> method="post" class="commentForm" data-user-id=<%=userId %> data-post-id=<%=postId%>>
			<input class="form-control" type="hidden"
				value="<%=UtilisateurActuel.getId()%>" name="userId" /> <input
				type="hidden" value="<%=postId%>" name="postId" />
			<div class="d-flex">
				<img class="card-img-top mx-3" src="data:image/jpeg;base64,<%=photo%>"
					alt="Card image cap" style="width: 30px; height: 30px"> <input
					class="form-control w-100" name="comment" id="comment" />
				<button id="commenter" type="submit" value="Comment"
					class="btn h-50 align-self-center btn-primary px-3 mx-3" >Comment</button>
			</div>
		</form>
		<%
		}
		%>
	</div>
	<script type="text/javascript">
		/**
		 * 
		 */
		var postId = '<%=postId%>';
		var userId = '<%=userId%>';
		function like(user,post) {
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
			var params = "postId=" + encodeURIComponent(post) + "&userId="
					+ encodeURIComponent(user);
			xhr.open("GET", url + "?" + params, true);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					// Handle the response here
					var response = JSON.parse(xhr.responseText); // If the response is in JSON format
					// Access the parameters of the request from the response object
					var param1Value = response.post;
					var param2Value = response.user;
				}
			};
			xhr.send();
		}
		function commenter(userId,postId){
			comment.value="";
			event.preventDefault();
			console.log(userId);
			console.log(postId);
			const comment=document.getElementById("comment");
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
			comment.value="";
		}
		function commenter(){
			event.preventDefault();
			console.log(userId);
			console.log(postId);
			const comment=document.getElementById("comment");
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
			comment.value="";
		}
		var id='id'+'<%=postId%>';
		document.querySelector("#"+id).addEventListener("submit", function(event) {
		    event.preventDefault(); 
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
				    console.log("Some error occured")
				  }
				};
			xhr.send('comment=' + encodeURIComponent(comment) + '&userId=' + encodeURIComponent(userId) + '&postId=' + encodeURIComponent(postId));
			document.getElementById("comment").value = "";
		});
		function chercher(){
			var name = document.getElementById('searchName').value;
		      var xhr = new XMLHttpRequest();
		      xhr.onreadystatechange = function() {
		        if (xhr.readyState === 4 && xhr.status === 200) {
		          var results = JSON.parse(xhr.responseText);
		          console.log(results);
		          var list = '<ul>';
		          results.forEach(function(user){
		        	  list += '<li><a href="utilisateur?id=' + encodeURIComponent(user.id) + '">' + user.nom+ '</a></li>';
		          })
		          list += '</ul>';
		          var searchResults = document.getElementById('searchResults');
		          searchResults.innerHTML = list;
		          var ul = searchResults.getElementsByTagName('ul');
		          ul.style.display = 'none';
		          searchResults.addEventListener('click', function() {
		            ul.style.display = ul.style.display === 'none' ? 'block' : 'none';
		          });
		        }
		      };
		      xhr.open('POST', 'search', true);
		      xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		      console.log(name);
		      xhr.send('searchName=' + name);
		}
	</script>
	<%@include file="../include/footer.jsp"%>
</body>
</html>