package dataprovider.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSource {

	public static Connection conn;

	public static Connection getConnection() {
		if (conn != null) {
			return conn;
		} else {
			return create();
		}
	}
	private static Connection create() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/techwolf";
			String username = "root";
			String password = "";
			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException se) {
				System.out.println("数据库连接失败！");
				se.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

}
