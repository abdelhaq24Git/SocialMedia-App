package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Like;

public class LikeDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public LikeDao(Connection con) {
		this.con = con;
	}

	public void ajouterLike(Like like) {
		String query = "INSERT into likes(post,utilisateur) VALUES(?,?)";

		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, like.getPost());
			pst.setInt(2, like.getUtilisateur());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Like findLike(int post,int utilisateur) {
		String query="SELECT * FROM likes WHERE post=? AND utilisateur=?";
		Like like=null;
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, post);
			pst.setInt(2, utilisateur);
			rs=pst.executeQuery();
			while(rs.next()) {
				like=new Like();
				like.setPost(rs.getInt("post"));
				like.setUtilisateur(rs.getInt("utilisateur"));
				like.setId(rs.getInt("id"));
				like.setTimestamp(rs.getTimestamp("date").toLocalDateTime());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return like;
	}
	public void removeLike(int post,int utilisateur) {
		String query="DELETE FROM likes WHERE post=? AND utilisateur=?";
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, post);
			pst.setInt(2, utilisateur);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
