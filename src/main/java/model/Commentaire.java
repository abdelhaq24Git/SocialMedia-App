package model;

public class Commentaire {
	private int id;
	private int utilisateur;
	private int post;
	private String commentaire;
	
	public Commentaire() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(int utilisateur) {
		this.utilisateur = utilisateur;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
}
