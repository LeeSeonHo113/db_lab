package site.metacoding.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// UPDATE
public class DBEx08 {

	public static void main(String[] args) {
		try {
			// 1. connection 연결(세션 생성) port, ip, id, password, protocol
			Connection conn = DriverManager.getConnection
					  ("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "TIGER");
			System.out.println("DB연결완료");
			
			// 2. 버퍼 달아서 통신(ALL : SELECT * FROM emp)
			// id 1의 username을 coding 으로 변경
			String sql = "UPDATE userTbl SET username = ? WHERE id = ?"; // 끝에 세미콜론 필요 없음.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "coding"); // 물음표의 순서, 값
			pstmt.setInt(2, 1); // 물음표의 순서, 값
			
			// 에러 -1, 성공 수행 된(생성, 삭제, 수정) row 개수, 아무 변화가 없으면 0
			int result = pstmt.executeUpdate(); // delete, update, insert (내부에 commit 존재)
			
			if(result > 0) {
				System.out.println("성공"); // 1
			} else {
				System.out.println("실패"); // 0
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}