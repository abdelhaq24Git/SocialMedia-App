<%@page import="model.Post"%>
<%@page import="java.util.Map"%>
<%@page import="model.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Utilisateur UtilisateurActuel = (Utilisateur) request.getSession().getAttribute("user");
out.println(UtilisateurActuel);
Map<Utilisateur, Post> UserPost = (Map<Utilisateur, Post>) session.getAttribute("allPosts");
out.println(UserPost);
if (UtilisateurActuel == null || UserPost==null) {
	response.sendRedirect("login.jsp");
	return;
}else{
	response.sendRedirect("Home");
}
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
		%>
		<div class="card m-3" style="width: 100%;">
			<div class="card-body">
				<h5 class="card-title">
					<img class="card-img-top"
						src="data:image/jpeg;base64,<%=entry.getKey().getDisplayableImage()%>"
						alt="Card image cap" style="width: 50px; height: 50px">
					<%=entry.getKey().getNom()%></h5>
				<p class="card-text"><%=entry.getValue().getContenu()%></p>
			</div>
			<img class="card-img-top"
				src="data:image/jpeg;base64,<%=entry.getValue().getDisplayableImage()%>"
				alt="Card image cap">
			<div class="card-body">
				<button href="#" class="btn btn-light">J'aime</button>
				<button href="#" class="btn btn-primary">Commenter</button>
			</div>
		</div>
		<%
		}
		%>
	</div>
	<%@include file="../include/footer.jsp"%>
</body>
</html>