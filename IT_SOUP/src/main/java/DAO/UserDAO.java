package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.UserDTO;

public class UserDAO {
    private Connection conn;
    //private PreparedStatement pstmt;
    private ResultSet rs;
    
    public UserDAO() {
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
    
    public int getNo() {
        String SQL = "SELECT NO FROM TB_EMP WHERE NO = (SELECT MAX(NO) FROM TB_EMP A WHERE NO = A.NO)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int join(UserDTO userDTO) {
        String SQL = "INSERT INTO TB_EMP (NO , ID , PASSWORD , NAME, HPNO, SSN, EMAIL, LEVEL, CHG_PW_YN, ADDR) VALUES (?, ?, hex(aes_encrypt(?, 'PASSWORD')), ?, ?, hex(aes_encrypt(?, 'SSN')), ?, ?, ?, ?)";
                
                try {
                    PreparedStatement pstmt = conn.prepareStatement(SQL);
                                                            
                    pstmt.setInt(1, getNo() + 1);
                    pstmt.setString(2, userDTO.getID());
                    pstmt.setString(3, userDTO.getPASSWORD());
                    pstmt.setString(4, userDTO.getNAME());
                    pstmt.setString(5, userDTO.getHPNO());
                    pstmt.setString(6, userDTO.getSSN());
                    pstmt.setString(7, userDTO.getEMAIL());
                    pstmt.setString(8, "3");
                    pstmt.setString(9, "N");
                    pstmt.setString(10, userDTO.getADDR());
                                        
                    return pstmt.executeUpdate();
                } catch(Exception e) {
                    e.printStackTrace();
                }
        return -1;
    }
}
