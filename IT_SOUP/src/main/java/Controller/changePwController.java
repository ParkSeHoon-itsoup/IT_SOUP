package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ChangePwDAO;
import DTO.UserDTO;

@WebServlet("/changePwController")
public class changePwController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
	    HttpSession session = request.getSession();
        String ID = (String)session.getAttribute("ID");
	    String PASSWORD = (String)session.getAttribute("PASSWORD");
	    
	    String afterPw = request.getParameter("afterPw").replace(" ", "");
	    String confirmPw = request.getParameter("confirmPw").replace(" ", ""); 

        System.out.println("ID = " + ID);
	    System.out.println("PASSWORD = " + "(" + PASSWORD + ")");
        System.out.println("afterPw = " + "(" + afterPw +")");
        System.out.println("confirmPw = " + "(" + confirmPw+")");
	    
	    if(afterPw.equals(PASSWORD)){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('변경하려는 비밀번호가 기존 비밀번호와 동일합니다')");
            script.println("history.back()");
            script.println("</script>");
	    } else if(!afterPw.equals(confirmPw)) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('변경하려는 비밀번호가 일치하지 않습니다.')");
            script.println("history.back()");
            script.println("</script>");
	    } else {
	        ChangePwDAO userDAO = new ChangePwDAO();
	        int changePw = ChangePwDAO.change(confirmPw, ID);
	        
	        if(changePw == 1) {
	            UserDTO userDTO = new UserDTO();
	            userDTO.setPASSWORD(confirmPw);
	            
	            PrintWriter script = response.getWriter();
	            script.println("<script>");
	            script.println("alert('비밀번호가 변경되었습니다.')");
	            script.println("location.href='main.jsp?formNm=notice'");
	            script.println("</script>");
	        }else {
	            PrintWriter script = response.getWriter();
	            script.println("<script>");
	            script.println("alert('비밀번호 변경에 실패하였습니다.')");
	            script.println("history.back()");
	            script.println("</script>");
	        }
	    }
	}

}
