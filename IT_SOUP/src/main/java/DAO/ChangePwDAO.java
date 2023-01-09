package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangePwDAO {
    private static Connection conn;
    
    public ChangePwDAO() {
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
    
    public static int change(String password, String id) {
        String SQL = "UPDATE TB_EMP"
                + "                  SET PASSWORD = hex(aes_encrypt(?, 'PASSWORD'))"
                + "                       , CHG_PW_YN = 'Y'"
                + "                       , MOD_DD = CURRENT_TIMESTAMP"
                + "            WHERE ID=?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, password);
            pstmt.setString(2, id);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("비밀번호 변경 실패 : " + e);
        }
        return -1;
    }
}
