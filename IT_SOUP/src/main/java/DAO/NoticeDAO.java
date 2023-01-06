package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.NoticeDTO;

public class NoticeDAO {
    private Connection conn;
    private ResultSet rs;
    
    public NoticeDAO() {
        try {
            String URL = "jdbc:mysql://localhost:3306/IT_SOUP";
            String ID = "root";
            String Password = "tt7546";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, ID, Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int findNo(String ID) {
        String SQL = "SELECT NO FROM TB_EMP WHERE ID = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("사원번호 찾기 실패 : " + e);
        }
        return -1;
    }
    
    public String getDate() {
        String SQL = "SELECT NOW()";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";  //데이터베이스 오류
    }
    
    public int write(int NO, String N_TITLE, String NAME, String N_CONTENT) {
        String SQL = "INSERT INTO TB_NOTICE (N_NO, NO, NAME, N_TITLE, N_CONTENT, N_DATE) VALUES ((SELECT IFNULL(MAX(N_NO) + 1, 1) FROM TB_NOTICE B), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?))";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, NO);
            pstmt.setString(2,NAME);
            pstmt.setString(3,  N_TITLE);
            pstmt.setString(4, N_CONTENT);
            pstmt.setString(5, getDate());
            
            pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("공지사항 글 작성 실패 : " + e);
        }
        return -1;
    }
}
