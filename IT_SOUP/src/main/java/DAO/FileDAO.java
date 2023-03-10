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
                +            ",F_NO"
                +            ")"
                +            "VALUES"
                +            "(?"
                +            ",?"
                +            ",?"
                +            ",(SELECT IFNULL(MAX(B.F_NO), 0) + 1"
                +            "    FROM TB_ATTACH B"
                +            "   WHERE B.N_NO = ?)"
                +            ")";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            pstmt.setString(2, F_NAME);
            pstmt.setString(3, F_REALNAME);
            pstmt.setInt(4, N_NO);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("파일첨부 실패 : " + e);
        }
        return -1;
    }
    
    public ArrayList<FileDTO> getList(int N_NO){
        String SQL = "SELECT F_REALNAME"
                + "        , F_NAME"
                + "        , F_NO"
                + "     FROM TB_ATTACH "
                + "    WHERE N_NO = ?";
        
        try {
            ArrayList<FileDTO> list = new ArrayList<FileDTO>();
            
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setF_REALNAME(rs.getString(1));
                fileDTO.setF_NAME(rs.getString(2));
                fileDTO.setF_NO(rs.getInt(3));
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
                + "     FROM TB_ATTACH"
                + "    WHERE N_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("첨부파일 삭제 실패 : " + e);
        }
        return -1;
    }
    
    public int updateDeleteAttach(int N_NO, int idx) {
        String SQL= "DELETE "
                + "    FROM TB_ATTACH "
                + "   WHERE N_NO = ? "
                + "     AND F_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            pstmt.setInt(2, idx);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("첨부파일 수정삭제 실패 : " +e);
        }
        return -1;
    }
}










