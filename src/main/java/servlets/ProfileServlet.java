package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utilisateur;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur user= (Utilisateur) request.getSession().getAttribute("user");
		System.out.println(user.getPrénom()+" prenom");
		System.out.println(user.getPhoto());
		String photo=(String) request.getSession().getAttribute("photo");
		System.out.println(photo);
		if(photo==null) {
			 photo=user.getDisplayableImage();
		}
		
		request.getSession().setAttribute("photo", photo);
		//response.sendRedirect("profile.jsp");
		request.getRequestDispatcher("profile.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
