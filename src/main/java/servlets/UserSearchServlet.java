package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;

import connection.BD;
import dao.UtilisateurDao;
import model.Utilisateur;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/search")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchName=request.getParameter("searchName");
		System.out.println(searchName);
		List<String>resultNames=new ArrayList<>();
		try {
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			List<Utilisateur> results=udao.search(searchName);
			for(Utilisateur u:results) {
				JSONObject userJson=new JSONObject();
				userJson.put("id", u.getId());
				userJson.put("nom", u.getNom());
				userJson.put("prenom", u.getPrénom());
			}
			System.out.println("this just a test "+results);
			results.forEach(user->resultNames.add(user.getNom()));
			JSONArray jsonArray=new JSONArray(results);
		    response.setContentType("application/json");
		    PrintWriter out = response.getWriter();
		    out.print(jsonArray);
		    out.flush();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
