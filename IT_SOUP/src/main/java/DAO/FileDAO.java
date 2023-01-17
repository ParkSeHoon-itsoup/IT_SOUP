package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.FileDTO;

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
    
    public ArrayList<FileDTO> getList(int N_NO){
        String SQL = "SELECT F_REALNAME "
                + "             FROM TB_ATTACH "
                + "           WHERE N_NO = ?";
        
        try {
            ArrayList<FileDTO> list = new ArrayList<FileDTO>();
            
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setF_REALNAME(rs.getString(1));
                list.add(fileDTO);
            }
            return list;
        } catch(Exception e) {
            System.out.println("첨부파일 불러오기 실패 : " + e);
        }
        return null;
    }
    
    public int deldelAttach(int N_NO) {
        String SQL = "DELETE"
                + "              FROM TB_ATTACH"
                + "            WHERE N_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("첨부파일 삭제 실패 : " + e);
        }
        return -1;
    }
}










