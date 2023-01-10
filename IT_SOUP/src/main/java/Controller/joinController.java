package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        
        HttpSession session = request.getSession();
        String naming = (String)session.getAttribute("naming");
        System.out.println("naming = "+ naming);
        
        String ID= request.getParameter("ID");
        String PASSWORD= request.getParameter("PASSWORD").replace(" ", "");
        String NAME= request.getParameter("NAME").replace(" ", "");
        String SSN= request.getParameter("SSN1") + request.getParameter("SSN2") .replace(" ", "");
        String HPNO= request.getParameter("HPNO1").replace(" ", "") + request.getParameter("HPNO2").replace(" ", "") + request.getParameter("HPNO3").replace(" ", "");
        String ADDR= request.getParameter("ADDR1").replace(" ", "");
        String ADDR2 = request.getParameter("ADDR2").replace(" ", "");
        String EMAIL= request.getParameter("EMAIL").replace(" ", "");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setID(ID);
        userDTO.setPASSWORD(PASSWORD);
        userDTO.setNAME(NAME);
        userDTO.setSSN(SSN);
        userDTO.setHPNO(HPNO);
        userDTO.setADDR(ADDR);
        userDTO.setADDR2(ADDR2);
        userDTO.setEMAIL(EMAIL);

            UserDAO userDAO = new UserDAO();
            
            int result = userDAO.chkID(ID);
                        
            if(result == 0) {
                UserDAO userDAO2 = new UserDAO();
                
                int result2 = userDAO2.join(userDTO);
                
                if(result2 ==-1) {
                    System.out.println("회원가입실패");
                } else {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('가입이 정상적으로 완료되었습니다.사용자에게 아이디/비밀번호를 안내해 주세요.')");
                    script.println("location.href='notice.jsp'");
                    script.println("</script>");
                    
//                    RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
//                    dispatcher.forward(request, response);
                    System.out.println("회원가입성공");
                }
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('이미 사용중인 아이디 입니다.')");
                script.println("history.back()");
                script.println("</script>");
        } 
    }
}
