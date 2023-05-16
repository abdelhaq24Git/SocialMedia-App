package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import connection.BD;
import dao.UtilisateurDao;
import model.Utilisateur;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur ancien=(Utilisateur) request.getSession().getAttribute("user");
		String nom="";
		String prenom="";
		String email="";
		String mdp="";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			// Parse the request
			@SuppressWarnings("unchecked")
			java.util.List<FileItem> items = upload.parseRequest(request);

			// Get the uploaded file item
			FileItem imageFileItem = null;
			for (FileItem item : items) {
				if (!item.isFormField()) {
					imageFileItem = item;
					//break;
				} else {
					String name = item.getFieldName();
					String value = item.getString();
					System.out.println(name);
					if(name.equals("nom")) {
						nom=value;
					}else if (name.equals("prenom")) {
						prenom=value;
					}else if (name.equals("mdp")) {
						mdp=value;
					}else if (name.equals("email")) {
						email=value;
					}
						
				}
			}

			// Get the image data as an input stream
			InputStream imageData = imageFileItem.getInputStream();
			/*PostDao pdao = new PostDao(BD.getConnection());
			Post p = new Post(contenu, imageData, user.getId());*/
			UtilisateurDao udao=new UtilisateurDao(BD.getConnection());
			System.out.println(nom);
			System.out.println(prenom);
			System.out.println(email);
			System.out.println(mdp);
			Utilisateur user=new Utilisateur(nom, prenom, imageData, email, mdp);
			udao.modifier(user,ancien);
			// Redirect to a success page
			user=udao.login(email, mdp);
			HttpSession session=request.getSession();
			session.setAttribute("user",user);
			session.setAttribute("photo", user.getDisplayableImage());
			//response.sendRedirect("index.jsp");
			request.getRequestDispatcher("Home").forward(request, response);

		} catch (FileUploadException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while processing the file upload.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while loading the database driver.");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while inserting the image into the database.");
		}
	}

}
