package model;

import java.time.LocalDateTime;

public class Like {
	private int id;
	private int post;
	private int utilisateur;
	private LocalDateTime timestamp;
	
	public Like() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
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
	
}
