package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Commentaire;

public class CommentaireDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	public CommentaireDao(Connection con) {
		
		this.con = con;
	}
	public void ajouterCommentaire(Commentaire commentaire) {
		query="INSERT INTO commentaires(utilisateur,post,commentaire) values(?,?,?)";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, commentaire.getUtilisateur());
			pst.setInt(2, commentaire.getPost());
			pst.setString(3, commentaire.getCommentaire());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Commentaire> getComments(int post) {
		query="SELECT * FROM commentaires WHERE post=?";
		ArrayList<Commentaire> commentaires=new ArrayList<>();
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, post);
			rs=pst.executeQuery();
			while(rs.next()) {
				Commentaire commentaire=new Commentaire();
				commentaire.setId(rs.getInt("id"));
				commentaire.setUtilisateur(rs.getInt("utilisateur"));
				commentaire.setPost(post);
				commentaire.setCommentaire(rs.getString("commentaire"));
				commentaires.add(commentaire);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return commentaires;
	}
	
}
