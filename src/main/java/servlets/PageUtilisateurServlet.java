package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.UtilisateurDao;
import model.Utilisateur;

/**
 * Servlet implementation class PageUtilisateurServlet
 */
@WebServlet("/utilisateur")
public class PageUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		try {
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			Utilisateur visited=udao.getUserById(id);
			System.out.println(visited);
			String profilePhoto=visited.getDisplayableImage();
			System.out.println(profilePhoto);
			request.setAttribute("visited", visited);
			request.setAttribute("profilePhoto", profilePhoto);
			request.getRequestDispatcher("profileUtulisateur.jsp").forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
