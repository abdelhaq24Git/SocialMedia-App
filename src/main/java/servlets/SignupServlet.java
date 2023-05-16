package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.BD;
import dao.UtilisateurDao;
import model.Utilisateur;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String nom=request.getParameter("nom");
		String prénom=request.getParameter("prenom");
		String email=request.getParameter("email");
		String mdp=request.getParameter("mdp");
		Utilisateur user=new Utilisateur(nom,prénom,email,mdp);
		HttpSession session=request.getSession();
		session.setAttribute("user",user);
		try {
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			udao.ajouterUtilisateur(user);
			response.sendRedirect("index.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
