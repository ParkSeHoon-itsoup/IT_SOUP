package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.UserDTO;

public class LoginDAO {
    private Connection conn;
    private ResultSet rs;
    
    public LoginDAO() {
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
    
    public int login(String id, String password) {
        String SQL = "SELECT convert(AES_DECRYPT(unhex(password), 'PASSWORD') using UTF8) as PASSWORD"
                + "                        , NO"
                + "                        , NAME "
                + "              FROM TB_EMP"
                + "            WHERE ID = ?";
        
        try {
            
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                if(rs.getString(1).contentEquals(password)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch(Exception e) {
            System.out.println("로그인 실패 : " + e);
        }
        return -1;
    }
    
    public String naming(String string) {
        String SQL = "SELECT NAME, ID FROM TB_EMP WHERE ID = trim(?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1, string);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getString(1);
            } 
        } catch(Exception e) {
            System.out.println("이름찾기실패 : " + e);
        }
        return null;
    }
    
    public UserDTO  getEMP(String ID) {
        String SQL = "SELECT NAME"
                + "                        , CHG_PW_YN"
                + "                        , LEVEL"
                + "                        , MOD_DD"
                + "                        , NO"
                + "              FROM TB_EMP "
                + "            WHERE ID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
             if(rs.next()) {
                 UserDTO userDTO = new UserDTO();
                 userDTO.setNAME(rs.getString(1));
                 userDTO.setCHG_PW_YN(rs.getString(2));
                 userDTO.setLEVEL(rs.getString(3));
                 userDTO.setMOD_DD(rs.getString(4));
                 userDTO.setNO(rs.getInt(5));
                 return userDTO;
             }
         }catch(Exception e) {
             System.out.println("사원이름 찾기실패 : " + e);
         }
        return null;
    }
    
    public String  getLevel(String ID) {
        String SQL = "SELECT LEVEL"
                + "              FROM TB_EMP "
                + "            WHERE ID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
             if(rs.next()) {
                return rs.getString(1);
             }
         }catch(Exception e) {
             System.out.println("관리자권한 찾기실패 : " + e);
         }
        return null;
    }
    
    public UserDTO setID(String ID) {
        String SQL = "SELECT convert(AES_DECRYPT(unhex(password), 'PASSWORD') using UTF8) as PASSWORD"
            + "                            , NO"
            + "                            , NAME "
            + "                  FROM TB_EMP"
            + "                WHERE ID = ?";
        
        try {
            PreparedStatement pstmt= conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                UserDTO userDTO = new UserDTO();
                userDTO.setPASSWORD(rs.getString(1));
                userDTO.setNO(rs.getInt(2));
                userDTO.setNAME(rs.getString(3));
                return userDTO;
            }
        } catch(Exception e) {
            System.out.println("직원정보 검색 실패 : " + e);
        }
        return null;
    }
}
