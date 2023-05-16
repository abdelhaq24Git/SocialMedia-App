package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Post;
import model.Utilisateur;

public class PostDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	public PostDao(Connection con) {
		this.con = con;
	}
	public void publierPost(Post p) {
		query="INSERT INTO posts (contenu,image,utilisateur) VALUES (?, ?,?)";
		try {
			pst=con.prepareStatement(query);
			pst.setString(1, p.getContenu());
			pst.setBlob(2, p.getImage());
			pst.setInt(3,p.getUtilisateur());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Post> getPosts(Utilisateur user) {
		ArrayList<Post> posts=new ArrayList<>();
		query="SELECT * FROM posts WHERE utilisateur=? ORDER BY date_publication DESC";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, user.getId());
			rs=pst.executeQuery();
			while(rs.next()) {
				Post post=new Post();
				post.setId(rs.getInt("id"));
				post.setContenu(rs.getString("contenu"));
				post.setImage(rs.getBinaryStream("image"));
				post.setUtilisateur(rs.getInt("utilisateur"));
				posts.add(post);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return posts;
	}
	
	public List<Post> getAllPosts() {
		ArrayList<Post> posts=new ArrayList<>();
		query="SELECT * FROM posts";
		try {
			pst=con.prepareStatement(query);
			rs=pst.executeQuery();
			while(rs.next()) {
				Post post=new Post();
				post.setId(rs.getInt("id"));
				post.setContenu(rs.getString("contenu"));
				post.setImage(rs.getBinaryStream("image"));
				post.setUtilisateur(rs.getInt("utilisateur"));
				post.setTimestamp(rs.getTimestamp("date_publication").toLocalDateTime());
				posts.add(post);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return posts;
	}
	public List<Post> getAllPostsByLikes() {
		ArrayList<Post> posts=new ArrayList<>();
		query="SELECT * FROM posts ORDER BY likes DESC";
		try {
			pst=con.prepareStatement(query);
			rs=pst.executeQuery();
			while(rs.next()) {
				Post post=new Post();
				post.setId(rs.getInt("id"));
				post.setContenu(rs.getString("contenu"));
				post.setImage(rs.getBinaryStream("image"));
				post.setUtilisateur(rs.getInt("utilisateur"));
				post.setTimestamp(rs.getTimestamp("date_publication").toLocalDateTime());
				posts.add(post);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return posts;
	}
	public void like(int post) {
		query="UPDATE posts SET likes=likes+1 WHERE id=?";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, post);
			pst.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}
	public void unlike(int post) {
		query="UPDATE posts SET likes=likes-1 WHERE id=?";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, post);
			pst.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}
}
