package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.CommentaireDao;
import dao.PostDao;
import dao.UtilisateurDao;
import model.Commentaire;
import model.Post;
import model.Utilisateur;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateurActuel=(Utilisateur) request.getSession().getAttribute("user");
		if(utilisateurActuel==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		System.out.println("This is the actual user"+utilisateurActuel);
		Map<Integer, Post> lastPostByUser = new LinkedHashMap<>();
		try {
			PostDao pdao=new PostDao(BD.getConnection());
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			List<Post> allPosts=pdao.getAllPosts();
			for(Post post:allPosts) {
				int userId=post.getUtilisateur();
				 if (!lastPostByUser.containsKey(userId) || post.getTimestamp().compareTo(lastPostByUser.get(userId).getTimestamp()) > 0) {
			            lastPostByUser.put(userId, post);
			        }
			}
			Map<Utilisateur, Post> userPostMap = new LinkedHashMap<>();
			for (Entry<Integer, Post> entry : lastPostByUser.entrySet()) {
			    //int userId = entry.getValue().getUtilisateur();
			    int userId = entry.getKey();
			    System.out.println(userId);
			    Utilisateur user = udao.getUserById(userId);
			    if(!user.getEmail().equals(utilisateurActuel.getEmail())) {
			    	Post post = entry.getValue();
				    System.out.println(user.toString());
				    System.out.println(post.toString());
				    userPostMap.put(user, post);
			    }
			    
			}
			request.getSession().setAttribute("allPosts", userPostMap);
			request.getRequestDispatcher("Home.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
