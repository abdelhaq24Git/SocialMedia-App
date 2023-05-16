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
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String email=request.getParameter("login-email");
		String mdp=request.getParameter("login-password");
		try {
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			Utilisateur user=udao.login(email, mdp);
			if(user!=null) {
				request.getSession().setAttribute("photo", user.getDisplayableImage());
				request.getSession().setAttribute("user", user);
				response.sendRedirect("Home");
			}else {
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
