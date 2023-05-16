package model;

public class Follow {
	private int id;
	private int follower;
	private int followed;
	
	public Follow() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	public int getFollowed() {
		return followed;
	}
	public void setFollowed(int followed) {
		this.followed = followed;
	}
	
}
