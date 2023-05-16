package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.PostDao;
import dao.UtilisateurDao;
import model.Post;
import model.Utilisateur;

/**
 * Servlet implementation class JustAservlet
 */
@WebServlet("/JustAservlet")
public class JustAservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<Utilisateur, Post> userPostMap = new HashMap<>();
		try {
			PostDao pdao=new PostDao(BD.getConnection());
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			List<Post> publications=pdao.getAllPosts();
			for(Post p : publications) {
				int userId=p.getUtilisateur();
				Utilisateur user=udao.getUserById(userId);
				userPostMap.put(user, p);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
