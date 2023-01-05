package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindDAO {
    private Connection conn;
    private ResultSet rs;
    
    public FindDAO() {
        try {
            String URL = "jdbc:mysql://localhost:3306/IT_SOUP";
            String ID = "root";
            String Password = "tt7546";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, ID, Password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public String find(String name, String ssn) {
        String SQL = "SELECT ID FROM TB_EMP WHERE NAME = ? AND convert(AES_DECRYPT(unhex(SSN), 'SSN') using UTF8) = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setString(2, ssn);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getString(1);
            } 
        } catch(Exception e) {
            System.out.println("아이디찾기 실패 : " + e);
        }
        return null;
    }
}
