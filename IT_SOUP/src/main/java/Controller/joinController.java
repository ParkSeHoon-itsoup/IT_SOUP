package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.UserDTO;
import DAO.UserDAO;

@WebServlet("/joinController")
public class joinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
	    String ID= request.getParameter("ID");
        String PASSWORD= request.getParameter("PASSWORD");
        String NAME= request.getParameter("NAME");
        String SSN= request.getParameter("SSN");
        String HPNO= request.getParameter("HPNO");
        String ADDR= request.getParameter("ADDR");
        String EMAIL= request.getParameter("EMAIL");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setID(ID);
        userDTO.setPASSWORD(PASSWORD);
        userDTO.setNAME(NAME);
        userDTO.setSSN(SSN);
        userDTO.setHPNO(HPNO);
        userDTO.setADDR(ADDR);
        userDTO.setEMAIL(EMAIL);
        
        if(null == ID || "".equals(ID)) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('아이디는 필수 입력 사항입니다.')>");
            script.println("history.back()");
            script.println("</script>");
        } else if(null == PASSWORD || "".equals(PASSWORD)) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호는 필수 입력 사항입니다.')>");
            script.println("history.back()");
            script.println("</script>");
        } else {
            UserDAO userDAO = new UserDAO();
            int result = userDAO.join(userDTO);
            
            if(result ==-1) {
                System.out.println("회원가입실패");
            } else {
                System.out.println("회원가입성공");
            }
        }
	}
}
