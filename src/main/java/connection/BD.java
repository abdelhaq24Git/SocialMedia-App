package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BD {
	private static Connection conncetion = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conncetion == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conncetion = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet_jee", "root", "");
			System.out.println("connected");

		}
		return conncetion;
	}
}
