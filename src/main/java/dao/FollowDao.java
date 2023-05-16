package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Follow;

public class FollowDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	public FollowDao(Connection con) {
		this.con = con;
	}
	public void follow(int follower, int followed) {
		query="INSERT INTO follow(follower,followed) VALUES(?,?)";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, follower);
			pst.setInt(2, followed);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Follow find(int follower , int followed) {
		query="SELECT * FROM follow WHERE follower=? AND followed=?";
		Follow f=null;
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, follower);
			pst.setInt(2, followed);
			rs=pst.executeQuery();
			while(rs.next()) {
				f=new Follow();
				f.setFollower(rs.getInt("follower"));
				f.setFollowed(rs.getInt("followed"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return f ;
	}
	public void unfollow(int follower , int followed) {
		query="DELETE FROM follow WHERE follower=? AND followed=?";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, follower);
			pst.setInt(2, followed);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
