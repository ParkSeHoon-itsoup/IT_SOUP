package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FindPwDAO{
    private Connection conn;
    private ResultSet rs;
    
    public FindPwDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/IT_SOUP";
            String dbID = "root";
            String dbPassword = "tt7546";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String findPw(String Id, String Email) {
        String SQL = "SELECT convert(AES_DECRYPT(unhex(PASSWORD), 'PASSWORD') using UTF8) AS PASSWORD FROM TB_EMP WHERE ID = ? AND EMAIL = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1, Id);
            pstmt.setString(2, Email);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                String result = rs.getString(1);
                
                return result;
            } 
        } catch(Exception e) {
            System.out.println("비밀번호 찾기 실패 : " + e);
        }
        return "";
    }
    
    public String tmpPw() {
        int index = 0;
        
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        
        StringBuffer sb = new StringBuffer();
        
        for(int i=0; i<15; i++) {
            index = (int)(charSet.length * Math.random());
            sb.append(charSet[index]);
        }
        
        return sb.toString();
    }
    
    public int updateTmpPw(String tmpPw, String Id, String Email) {
        String SQL = "UPDATE TB_EMP SET PASWORD = ? WHERE ID = ? AND EMAIL = ?;";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1, tmpPw);
            pstmt.setString(2, Id);
            pstmt.setString(3, Email);
            
            return pstmt.executeUpdate();
        } catch(Exception e) {
            System.out.println("비밀번호 업데이트 실패 : " + e);
        }
        return -1;
    }
    
    public int sendMail(String tmpPw) {
        String host = "smtp.naver.com";
        String user = "chulgisibjo@naver.com";
        String password = "ttff~~7546";
        
        Properties props = new Properties();
        props.put("mail..smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("1182836@daum.net"));
            
            //메일 제목
            message.setSubject("임시비밀번호가 발급되었습니다.");
            
            //메일 내용
            message.setText(password);
            
            Transport.send(message);
            
            return 1;
        } catch(MessagingException  e) {
            e.printStackTrace();
        }
        return -1;
    }
}
