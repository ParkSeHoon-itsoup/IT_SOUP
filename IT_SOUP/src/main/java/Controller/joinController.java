package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
        String SSN= request.getParameter("SSN1") + request.getParameter("SSN2") ;
        String HPNO= request.getParameter("HPNO1") + request.getParameter("HPNO2") + request.getParameter("HPNO3");
        String ADDR= request.getParameter("ADDR1") + ", " + request.getParameter("ADDR2");
        String EMAIL= request.getParameter("EMAIL");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setID(ID);
        userDTO.setPASSWORD(PASSWORD);
        userDTO.setNAME(NAME);
        userDTO.setSSN(SSN);
        userDTO.setHPNO(HPNO);
        userDTO.setADDR(ADDR);
        userDTO.setEMAIL(EMAIL);

        PrintWriter script = response.getWriter();

        if(null == ID || "".equals(ID)) {
            script.println("<script>");
            script.println("alert('아이디는 필수 입력 사항입니다.')>");
            script.println("history.back()");
            script.println("</script>");
        } else if(null == PASSWORD || "".equals(PASSWORD)) {
            script.println("<script>");
            script.println("alert('비밀번호는 필수 입력 사항입니다.')>");
            script.println("history.back()");
            script.println("</script>");
        } else if((null != ID || !"".equals(ID)) && (ID.length() <4)){
            script.println("<script>");
            script.println("alert('아이디는 4글자 이상이어야 합니다.')>");
            script.println("history.back()");
            script.println("</script>");
        } else if((null != ID || !"".equals(ID)) && (ID.length() > 4)){
            UserDAO userDAO = new UserDAO();
            
            int result = userDAO.chkID(ID);
                        
            if(result == 0) {
                System.out.println("사용 가능한 ID 입니다.");
                
                UserDAO userDAO2 = new UserDAO();
                
                int result2 = userDAO2.join(userDTO);
                
                if(result2 ==-1) {
                    System.out.println("회원가입실패");
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
                    dispatcher.forward(request, response);
                    System.out.println("회원가입성공");
                }
            } else {
                script.println("<script>");
                script.println("alert('이미 사용중인 아이디 입니다.')>");
                script.println("history.back()");
                script.println("</script>");
            } 
        } else {
        }
    }
}
