package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Utilisateur;

public class UtilisateurDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	public UtilisateurDao(Connection con) {
		this.con = con;
	}
	public void ajouterUtilisateur(Utilisateur user) {
		String nom=user.getNom();
		String prenom=user.getPrénom();
		String email=user.getEmail();
		String mdp=user.getMdp();
		InputStream photo=user.getPhoto();
		try {
			query="INSERT INTO utilisateurs(nom,prenom,email,motDePasse,photo) values(?,?,?,?,?)";
			pst=this.con.prepareStatement(query);
			pst.setString(1, nom);
			pst.setString(2, prenom);
			pst.setString(3, email);
			pst.setString(4, mdp);
			pst.setBlob(5, photo);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	public Utilisateur login(String email,String mdp) {
		Utilisateur user=null;
		try {
			query="SELECT * FROM utilisateurs WHERE email=? AND motDePasse=?";
			pst=this.con.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, mdp);
			rs=pst.executeQuery();
			if(rs.next()) {
				user=new Utilisateur();
				user.setId(rs.getInt("id"));
				user.setNom(rs.getString("nom"));
				user.setPrénom(rs.getString("prenom"));
				user.setEmail(rs.getString("email"));
				user.setMdp(rs.getString("motDePasse"));
				user.setPhoto(rs.getBinaryStream("photo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	public Utilisateur getUserById(int id) {
		Utilisateur user=null;
		try {
			query="SELECT * FROM utilisateurs WHERE id=?";
			pst=this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs=pst.executeQuery();
			while(rs.next()) {
				user=new Utilisateur();
				user.setId(rs.getInt("id"));
				user.setNom(rs.getString("nom"));
				user.setPrénom(rs.getString("prenom"));
				user.setEmail(rs.getString("email"));
				user.setMdp(rs.getString("motDePasse"));
				user.setPhoto(rs.getBinaryStream("photo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	public void modifier(Utilisateur nouveau,Utilisateur ancien) {
		query="UPDATE utilisateurs SET nom=?,prenom=?,email=?,motDePasse=?,photo=? WHERE id=?";
		try {
			pst=con.prepareStatement(query);
			pst.setString(1, nouveau.getNom());
			pst.setString(2, nouveau.getPrénom());
			pst.setString(3, nouveau.getEmail());
			pst.setString(4, nouveau.getMdp());
			pst.setBlob(5, nouveau.getPhoto());
			pst.setInt(6, ancien.getId());
			pst.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public List<Utilisateur> search(String nom){
		Utilisateur user=null;
		ArrayList<Utilisateur> results=new ArrayList<>();
		String pattern="%"+nom+"%";
		query="SELECT * FROM utilisateurs WHERE nom like ? OR prenom like ?";
		try {
			pst=con.prepareStatement(query);
			pst.setString(1, pattern);
			pst.setString(2, pattern);
			System.out.println(query);
			rs=pst.executeQuery();
			while(rs.next()) {
				user=new Utilisateur();
				user.setId(rs.getInt("id"));
				user.setNom(rs.getString("nom"));
				user.setPrénom(rs.getString("prenom"));
				user.setEmail(rs.getString("email"));
				user.setMdp(rs.getString("motDePasse"));
				user.setPhoto(rs.getBinaryStream("photo"));
				results.add(user);			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
}
