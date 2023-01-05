package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.UserDTO;

public class UserDAO {
    private Connection conn;
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
    
    public int join(UserDTO userDTO) {
        String SQL = "INSERT INTO TB_EMP (NO, ID, PASSWORD, NAME, HPNO, SSN, EMAIL, LEVEL, CHG_PW_YN, ADDR, ADDR2, JOIN_DD) VALUES  ((SELECT IFNULL(MAX(NO) + 1, 1) FROM TB_EMP B), TRIM(?), TRIM(hex(aes_encrypt(?, 'PASSWORD')))"
                + "                                                                                                                                                                                                                                                , TRIM(?), TRIM(?), TRIM(hex(aes_encrypt(?, 'SSN'))), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?))";
                
                try {
                    PreparedStatement pstmt = conn.prepareStatement(SQL);
                                                            
                    pstmt.setString(1, userDTO.getID());
                    pstmt.setString(2, userDTO.getPASSWORD());
                    pstmt.setString(3, userDTO.getNAME());
                    pstmt.setString(4, userDTO.getHPNO());
                    pstmt.setString(5, userDTO.getSSN());
                    pstmt.setString(6, userDTO.getEMAIL());
                    pstmt.setString(7, "03");
                    pstmt.setString(8, "N");
                    pstmt.setString(9, userDTO.getADDR());
                    pstmt.setString(10, userDTO.getADDR2());
                    pstmt.setString(11, userDTO.getJOIN_DD());
                                        
                    return pstmt.executeUpdate();
                } catch(Exception e) {
                    System.out.println("회원가입 실패 : " + e);
                }
        return -1;
    }
    
    public int chkID(String id) {
        int cnt = 0;
        String SQL = "SELECT COUNT(ID) FROM TB_EMP WHERE ID = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, id);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                cnt = rs.getInt(1);
                return cnt;
            }
        } catch(Exception e) {
            System.out.println("아이디 중복 확인 실패 : " + e);
        }
        return -1;
    }
}
