package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Utilisateur {
	private int id;
	private String nom;
	private String prénom;
	private InputStream photo;
	public InputStream getPhoto() {
		return photo;
	}

	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}

	private String email;
	private String mdp;
	
	public Utilisateur() {
		
	}

	public Utilisateur(String nom, String prénom, String email, String mdp) {
		this.nom = nom;
		this.prénom = prénom;
		this.email = email;
		this.mdp = mdp;
	}
	
	
	public Utilisateur(String nom, String prénom, InputStream photo, String email, String mdp) {
		this.nom = nom;
		this.prénom = prénom;
		this.photo = photo;
		this.email = email;
		this.mdp = mdp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrénom() {
		return prénom;
	}

	public void setPrénom(String prénom) {
		this.prénom = prénom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getDisplayableImage() throws IOException {
		BufferedImage photo = ImageIO.read(this.getPhoto());
		ByteArrayOutputStream baosPhoto = new ByteArrayOutputStream();
		ImageIO.write(photo, "jpg", baosPhoto);
		byte[] imageDataUser = baosPhoto.toByteArray();
		String UserPhoto = Base64.getEncoder().encodeToString(imageDataUser);
		return UserPhoto;
	}
	
}
