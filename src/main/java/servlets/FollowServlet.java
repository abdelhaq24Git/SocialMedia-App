package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.BD;
import dao.FollowDao;
import model.Follow;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/follow")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int follower=Integer.parseInt(request.getParameter("actuel"));
		int followed=Integer.parseInt(request.getParameter("user"));
		try {
			FollowDao fdao=new FollowDao(BD.getConnection());
			Follow f=fdao.find(follower, followed);
			if(f==null) {
				fdao.follow(follower, followed);
			}else {
				fdao.unfollow(follower, followed);
			}
			
			request.getRequestDispatcher("utilisateur?id="+followed).forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
