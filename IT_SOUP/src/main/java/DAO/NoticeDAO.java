package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

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
        String SQL = "SELECT NO"
                + "              FROM TB_EMP "
                + "           WHERE ID = ?";
        
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
    
    public int write(int NO, String N_TITLE,  String N_CONTENT) {
        String SQL = "INSERT INTO TB_NOTICE "
                + "          (N_NO"
                + "         , NO"
                + "         , N_TITLE"
                + "         , N_CONTENT"
                + "         , N_DATE"
                + "         , DEL_YN"
                + "           ) "
                + "           VALUES "
                + "          ((SELECT IFNULL(MAX(N_NO), 0) + 1 "
                + "               FROM TB_NOTICE B)"
                + "        , TRIM(?)"
                + "        , TRIM(?)"
                + "        , TRIM(?)"
                + "        , CURRENT_TIMESTAMP()"
                + "        , 'N')"
                               ;
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, NO);
            pstmt.setString(2,  N_TITLE);
            pstmt.setString(3, N_CONTENT);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("공지사항 글 작성 실패 : " + e);
        }
        return -1;
    }
    
    public int listCount() {
        String SQL = "SELECT COUNT(*) "
                + "              FROM TB_NOTICE "
                + "            WHERE DEL_YN = 'N'";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("게시글 수 찾기 실패 : " + e);
        }
        return -1;
    }
    
    public int getNext() {
        String SQL = "SELECT N_NO"
                + "              FROM TB_NOTICE"
                + "             ORDER BY N_NO DESC";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch(Exception e) {
            System.out.println("다음글 번호 찾기 실패 : " + e);
        }
        return -1;
    }
    
    public ArrayList<NoticeDTO> getList(int startRow){
        String SQL = "SELECT A.N_NO"
                + "                       , A.NO"
                + "                       , B.NAME"
                + "                       , A.N_TITLE"
                + "                       , A.N_CONTENT"
                + "                       , A.N_DATE"
                + "             FROM TB_NOTICE A"
                + "                      , TB_EMP B"
                + "          WHERE A.NO = B.NO"
                + "                AND A.DEL_YN = 'N'"
                + "           ORDER BY A.N_NO DESC"
                + "              LIMIT ?"
                + "                     , 10"
                ;
                
        ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, startRow -1);
            
            rs = pstmt.executeQuery();
            
           while(rs.next()) {
               NoticeDTO noticeDTO = new NoticeDTO();
               noticeDTO.setN_NO(rs.getInt(1));
               noticeDTO.setNO(rs.getInt(2));
               noticeDTO.setNAME(rs.getString(3));
               noticeDTO.setN_TITLE(rs.getString(4));
               noticeDTO.setN_CONTENT(rs.getString(5));
               noticeDTO.setN_DATE(rs.getString(6));
               list.add(noticeDTO);
           }
        } catch(Exception e) {
            System.out.println("공지사항 불러오기 실패 : " + e);
        }
        return list;
    }
    
    public boolean nextPage(int pageNumber) {
        String SQL = "SELECT *"
                + "             FROM TB_NOTICE"
                + "          WHERE N_NO < ?"
                + "                AND DEL_YN = 'N'";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);    
            pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
            rs = pstmt.executeQuery();
                    
            if(rs.next()) {
                return true;
            }
        } catch(Exception e) {
            System.out.println("다음 페이지 찾기 실패 : " + e);
        }
        return false;
    }
    
    public NoticeDTO read(int N_NO) {
        String SQL = "SELECT N_TITLE"
                + "                       , N_CONTENT "
                + "             FROM TB_NOTICE "
                + "           WHERE N_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setN_TITLE(rs.getString(1));
                noticeDTO.setN_CONTENT(rs.getString(2));
                return noticeDTO;
            } 
        } catch(Exception e) {
            System.out.println("공지사항 글 찾기 실패 : " + e);
        }
        return null;
    }
    
    public NoticeDTO notice_read(String ID) {
        String SQL = "SELECT A.N_TITLE, B.NAME, A.N_DATE, A.N_CONTENT FROM TB_NOTICE A, TB_EMP B WHERE A.NO = B.NO AND A.N_NO=?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ID);
           
            rs = pstmt.executeQuery();
            if(rs.next()) {
                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setN_TITLE(rs.getString(1));
                noticeDTO.setNAME(rs.getString(2));
                noticeDTO.setN_DATE(rs.getString(3));
                noticeDTO.setN_CONTENT(rs.getString(4));
                
                return noticeDTO;
            }
        } catch(Exception e) {
            System.out.println("공지사항 글 찾기 실패 : " + e);
        }
        return null;
    }
}
