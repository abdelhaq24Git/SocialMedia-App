package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.CommentaireDao;
import model.Commentaire;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HERE IS THE COMMENT SERVLET");
		int utilisateur= Integer.parseInt(request.getParameter("userId"));
		int post=Integer.parseInt(request.getParameter("postId"));
		String commentaire=request.getParameter("comment");
		try {
			CommentaireDao cdao=new CommentaireDao(BD.getConnection());
			Commentaire c=new Commentaire();
			c.setUtilisateur(utilisateur);
			c.setPost(post);
			c.setCommentaire(commentaire);
			cdao.ajouterCommentaire(c);
			request.getRequestDispatcher("Home.jsp").forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
