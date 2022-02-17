package site.metacoding.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// PrepareStatement ���� ���ε��ϱ�
public class DBEx03 {

	public static void login(String username, String password) {
		try {
			Connection conn = DriverManager.getConnection
					  ("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "TIGER");
			System.out.println("DB����Ϸ�");
			
			String sql = "SELECT * FROM userTbl WHERE username = ? AND password = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// ?�� ���۹����� 1�̴�.
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Session.isLogin = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		login("ssar", "1234");
		
		System.out.println(Session.isLogin);
	}
}