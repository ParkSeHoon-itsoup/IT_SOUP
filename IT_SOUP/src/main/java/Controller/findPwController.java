package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FindPwDAO;

@WebServlet("/findPwController")
public class findPwController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
	    
        String Id = request.getParameter("ID").replace(" ", "");
        String Email = request.getParameter("EMAIL").replace(" ", "");
        
        FindPwDAO findPwDAO = new FindPwDAO();
        
        String findPw = findPwDAO.findPw(Id, Email);
        
        request.setAttribute("findPw", findPw);
        
        if(null != findPw || !"".equals(findPw)) {
            String tmpPw = findPwDAO.tmpPw();
            
            int updateTmpPw = findPwDAO.updateTmpPw(tmpPw, Id, Email);
            
            if(updateTmpPw == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('업데이트에 실패하였습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                int tmpPwMail = findPwDAO.sendMail(tmpPw, Email);
                
                if(tmpPwMail == 1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('임시비밀번호가 가입시 작성하신 이메일로 발송되었습니다. 이메일을 확인해 주세요.')");
                    script.println("location.href='main.jsp'");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('임시비밀번호 이메일 전송이 실패하였습니다..')");
                    script.println("history.back()");
                    script.println("</script>");
                }
            }
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('아이디 또는 이메일주소가 잘못 입력 되었습니다.')");
            script.println("history.back()");
            script.println("</script>");
        }
	}
}
