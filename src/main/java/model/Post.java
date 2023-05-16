package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Post {
	private int id;
	private String contenu;
	private InputStream image;
	private int utilisateur;
	private LocalDateTime timestamp;
	public Post() {
		
	}

	public Post(String contenu, InputStream image, int utilisateur) {
		this.contenu = contenu;
		this.image = image;
		this.utilisateur = utilisateur;
	}

	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public int getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(int utilisateur) {
		this.utilisateur = utilisateur;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getDisplayableImage() {
		BufferedImage image;
		String base64Image="";
		try {
			image = ImageIO.read(this.getImage());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] imageData = baos.toByteArray();
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return base64Image;
	}
	
}
