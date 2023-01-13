package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FileDAO {
    private Connection conn;
    private ResultSet rs;
    
    public FileDAO() {
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

    public int regAttach(int N_NO, String F_NAME, String F_REALNAME) {
        String SQL = "INSERT INTO TB_ATTACH"
                +            "(N_NO"
                +            ",F_NAME"
                +            ",F_REALNAME"
                +            ")"
                +            "VALUES"
                +            "(?"
                +            ",?"
                +            ",?"
                +            ")";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            pstmt.setString(2, F_NAME);
            pstmt.setString(3, F_REALNAME);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("파일첨부 실패 : " + e);
        }
        return -1;
    }
}
