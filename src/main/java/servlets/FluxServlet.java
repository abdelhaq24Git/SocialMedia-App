package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.FollowDao;
import dao.PostDao;
import dao.UtilisateurDao;
import model.Follow;
import model.Post;
import model.Utilisateur;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/flux")
public class FluxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateurActuel=(Utilisateur) request.getSession().getAttribute("user");
		if(utilisateurActuel==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		Map<Integer, Post> lastPostByUser = new HashMap<>();
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
			Map<Utilisateur, Post> userPostMap = new HashMap<>();
			for (Entry<Integer, Post> entry : lastPostByUser.entrySet()) {
			    //int userId = entry.getValue().getUtilisateur();
			    int userId = entry.getKey();
			    System.out.println(userId);
			    FollowDao fdao=new FollowDao(BD.getConnection());
			    Follow f=fdao.find(utilisateurActuel.getId(), userId);
			    if(f!=null && f.getFollowed()==userId) {
			    	Utilisateur user = udao.getUserById(userId);
				    if(!user.getEmail().equals(utilisateurActuel.getEmail())) {
				    	Post post = entry.getValue();
					    System.out.println(user.toString());
					    System.out.println(post.toString());
					    userPostMap.put(user, post);
				    }
			    }
			    
			    
			}
			request.getSession().setAttribute("allPosts", userPostMap);
			request.getRequestDispatcher("flux.jsp").forward(request, response);
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
