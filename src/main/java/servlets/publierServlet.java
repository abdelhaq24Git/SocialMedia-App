package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import connection.BD;
import dao.PostDao;
import model.Post;
import model.Utilisateur;

/**
 * Servlet implementation class publierServlet
 */
@WebServlet("/publication")
public class publierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public publierServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the content and image from the form
		/*
		 * response.setContentType("text/html"); Utilisateur
		 * user=(Utilisateur)request.getSession().getAttribute("user"); String contenu =
		 * request.getParameter("contenu"); String test = request.getParameter("test");
		 * PrintWriter out=response.getWriter(); out.println(test); Part imagePart =
		 * request.getPart("image");
		 * 
		 * // Get the binary data of the image InputStream imageStream =
		 * imagePart.getInputStream(); ByteArrayOutputStream imageBytes = new
		 * ByteArrayOutputStream(); byte[] buffer = null; int length;
		 */
		String contenu = "";
		Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
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
					break;
				} else {
					String name = item.getFieldName();
					String value = item.getString();
					contenu=value;
						
				}
			}

			
			InputStream imageData = imageFileItem.getInputStream();
			PostDao pdao = new PostDao(BD.getConnection());
			Post p = new Post(contenu, imageData, user.getId());
			pdao.publierPost(p);
			request.getRequestDispatcher("ProfileServlet").forward(request, response);

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
