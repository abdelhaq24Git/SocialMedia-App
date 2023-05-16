package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.LikeDao;
import dao.PostDao;
import model.Like;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("postId"));
		int utilisateur = Integer.parseInt(request.getParameter("userId"));
		int post = Integer.parseInt(request.getParameter("postId"));
		try {
			LikeDao ldao = new LikeDao(BD.getConnection());
			PostDao pdao=new PostDao(BD.getConnection());
			Like like = ldao.findLike(post, utilisateur);
			if (like == null) {
				like = new Like();
				like.setPost(post);
				like.setUtilisateur(utilisateur);
				ldao.ajouterLike(like);
				pdao.like(post);
				response.sendRedirect("Home");
			} else {
				ldao.removeLike(post, utilisateur);
				pdao.unlike(post);
				response.sendRedirect("Home");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
