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
    
    public int findN_NO() {
        String SQL = "SELECT IFNULL(MAX(N_NO), 0) + 1"
                +"              FROM TB_NOTICE";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);   
            }
        } catch(Exception e) {
            System.out.println("공지사항 번호 찾기 에러 : " + e);
        }
        return -1;
    }
    
    public int write(NoticeDTO noticeDTO) {
        String SQL = "INSERT INTO TB_NOTICE "
                + "          (N_NO"
                + "         , NO"
                + "         , N_TITLE"
                + "         , N_CONTENT"
                + "         , N_DATE"
                + "         , REPLY"
                + "         , RE_NO"
                + "           ) "
                + "           VALUES "
                + "          (TRIM(?)"
                + "        , TRIM(?)"
                + "        , TRIM(?)"
                + "        , TRIM(?)"
                + "        , CURRENT_DATE()"
                + "        , 0"
                + "        , 0)"
                               ;
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, noticeDTO.getN_NO());
            pstmt.setInt(2, noticeDTO.getNO());
            pstmt.setString(3,noticeDTO.getN_TITLE());
            pstmt.setString(4, noticeDTO.getN_CONTENT());
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("공지사항 글 작성 실패 : " + e);
        }
        return -1;
    }
    
    public int listCount() {
        String SQL = "SELECT COUNT(*) "
                + "              FROM TB_NOTICE ";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("공지사항 수 찾기 실패 : " + e);
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
                + "                        , A.NO"
                + "                        , B.NAME"
                + "                        , A.N_TITLE"
                + "                        , A.N_CONTENT"
                + "                        , A.N_DATE"
                + "                        , B.ID"
                + "                        , A.REPLY"
                + "                        , A.RE_NO"
                + "             FROM TB_NOTICE A"
                + "                       , TB_EMP B"
                + "          WHERE A.NO = B.NO"
                + "                AND A.REPLY = 0"
                + "               AND A.RE_NO = 0"
                + "          ORDER BY A.N_NO DESC"
                + "                             , A.RE_NO"
                + "                             , A.REPLY"
                + "              LIMIT ?"
                + "                     , 10";
            
        ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, (startRow -1));

            rs = pstmt.executeQuery();

           while(rs.next()) {
               NoticeDTO noticeDTO = new NoticeDTO();
               noticeDTO.setN_NO(rs.getInt(1));
               noticeDTO.setNO(rs.getInt(2));
               noticeDTO.setNAME(rs.getString(3));
               noticeDTO.setN_TITLE(rs.getString(4));
               noticeDTO.setN_CONTENT(rs.getString(5));
               noticeDTO.setN_DATE(rs.getString(6));
               noticeDTO.setID(rs.getString(7));
               noticeDTO.setREPLY(rs.getInt(8));
               noticeDTO.setRE_NO(rs.getInt(9));
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
    
    public NoticeDTO notice_read(int N_NO, int REPLY,  int RE_NO) {
        
        System.out.println("DAO.N_NO = " + N_NO);
        System.out.println("DAO.REPLY = " + REPLY);
        System.out.println("DAO.RE_NO = " + RE_NO);
        
        
        
        String SQL = "SELECT A.N_TITLE"
                + "                        , B.NAME"
                + "                        , A.N_DATE"
                + "                        , A.N_CONTENT"
                + "                        , A.NO"
                + "                        , B.ID"
                + "                        , A.REPLY"
                + "                        , A.RE_NO"
                + "             FROM TB_NOTICE A"
                + "                       , TB_EMP B "
                + "          WHERE A.NO = B.NO"
                + "                AND A.N_NO = ?"
                + "                AND A.REPLY = ?"
                + "                AND A.RE_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            pstmt.setInt(2, REPLY);
            pstmt.setInt(3, RE_NO);
           
            rs = pstmt.executeQuery();
            if(rs.next()) {
                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setN_TITLE(rs.getString(1));
                noticeDTO.setNAME(rs.getString(2));
                noticeDTO.setN_DATE(rs.getString(3));
                noticeDTO.setN_CONTENT(rs.getString(4));
                noticeDTO.setNO(rs.getInt(5));
                noticeDTO.setID(rs.getString(6));
                noticeDTO.setREPLY(rs.getInt(7));
                noticeDTO.setRE_NO(rs.getInt(8));
                
                return noticeDTO;
            }
        } catch(Exception e) {
            System.out.println("공지사항 글 찾기 실패 : " + e);
        }
        return null;
    }
    
    public int mod_notice_write(int n_NO, String N_TITLE, String N_CONTENT, int NO) {

        System.out.println("N_TITLE = " + N_TITLE);
        System.out.println("N_CONTENT = " + N_CONTENT);
        System.out.println("NO = " + NO);
        System.out.println("n_NO = " + n_NO);
        
        String SQL = "UPDATE TB_NOTICE "
                + "                   SET N_TITLE = ?"
                + "                        , N_CONTENT = ?"
                + "                        , MOD_DATE = current_date()"
                + "                        , MOD_EMPNO = ?"
                + "           WHERE N_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, N_TITLE);
            pstmt.setString(2, N_CONTENT);
            pstmt.setInt(3, NO);
            pstmt.setInt(4, n_NO);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("공지사항 글 수정 실패 : " + e);
        }
        return -1;
    }
    
    public int delete_notice_write(int N_NO) {
        String SQL = "DELETE"
                + "              FROM TB_NOTICE"
                + "            WHERE N_NO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, N_NO);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("공지사항 글삭제 실패 : " + e);
        }
        return -1;
    }
    
    public ArrayList<NoticeDTO> getsearchList(String searchField, String searchText, int startRow){
        ArrayList<NoticeDTO> searchList = new ArrayList<NoticeDTO>();
        
        String SQL = "SELECT A.NO"
                + "                        , A.N_TITLE"
                + "                        , B.NAME"
                + "                        , B.ID"
                + "                        , A.N_DATE"
                + "             FROM TB_NOTICE A"
                + "                       , TB_EMP B"
                + "           WHERE A.NO = B.NO"
                + "                 AND (('N_TITLE' = ? AND N_TITLE LIKE ?)"
                + "                            OR"
                + "                           ('N_CONTENT' = ? AND N_CONTENT LIKE ?)"
                + "                            OR"
                + "                           ('TOTAL' = ? AND (N_TITLE LIKE ? OR N_CONTENT LIKE ?))"
                + "                          )"
                + "      ORDER BY A.N_NO DESC "
                + "                        , A.REPLY"
                + "                        , A.RE_NO DESC"
                + "              LIMIT ? "
                + "                      , 10";
            try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, searchField);
                pstmt.setString(2, "%" +searchText + "%");
                pstmt.setString(3, searchField);
                pstmt.setString(4, "'%" + searchText + "%'");
                pstmt.setString(5, searchField);
                pstmt.setString(6, "'%" + searchText + "%'");
                pstmt.setString(7, "'%" + searchText + "%'");
                pstmt.setInt(8, startRow -1);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                NoticeDTO list = new NoticeDTO();
                list.setN_NO(rs.getInt(1));
                list.setN_TITLE(rs.getString(2));
                list.setNAME(rs.getString(3));
                list.setID(rs.getString(4));
                list.setN_DATE(rs.getString(5));
                searchList.add(list);
            }
        } catch(Exception e) {
            System.out.println("공지사항 검색 실패 : " + e);
        }
        return searchList;
    }
    
    public int reply_write(int NO,int N_NO, int REPLY,  int RE_NO, String R_TITLE, String R_CONTENT, String P_ID) {
        String SQL = "INSERT INTO TB_NOTICE "
                +            " (N_NO"
                +            ", REPLY"
                +            ", RE_NO"
                +            ", NO"
                +            ", N_TITLE"
                +            ", N_CONTENT"
                +            ", N_DATE"
                +            ", P_ID"
                +            " )"
                +            " VALUES"
                +            "(?"
                +            ",(SELECT B.REPLY + 1"
                +            "     FROM TB_NOTICE B"
                +            "  WHERE N_NO = ?"
                +            "        AND B.REPLY = ?"
                +            "   )"
                +            " , (SELECT MAX(X.RE_NO) + 1"
                +            "      FROM TB_NOTICE X"
                +            "     WHERE X.N_NO = ?"
                +            "       AND X.REPLY = ? + 1)"
                +            ", ?"
                +            ", '?'"
                +            ", '?'"
                +            ", current_date()"
                +            ", ?)";
         
         try {
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             pstmt.setInt(1,N_NO);
             pstmt.setInt(2, N_NO);
             pstmt.setInt(3, REPLY);
             pstmt.setInt(4, RE_NO);
             pstmt.setInt(5, N_NO);
             pstmt.setInt(6, REPLY);
             pstmt.setInt(7, NO);
             pstmt.setString(8, R_TITLE);
             pstmt.setString(9, R_CONTENT);
             pstmt.setString(10, P_ID);
             
             return pstmt.executeUpdate();
         } catch(Exception e) {
             System.out.println("댓글 작성 실패 : " + e);
         }
         return -1;
    }
}
