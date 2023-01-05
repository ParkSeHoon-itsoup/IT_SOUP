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
import DAO.FindDAO;

@WebServlet("/findController")
public class findController extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        String findNM = request.getParameter("NAME");
        String findSSN = request.getParameter("SSN");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setNAME(findNM);
        userDTO.setSSN(findSSN);
        
        FindDAO findDAO = new FindDAO();
    
        String findID = findDAO.find(userDTO.getNAME(), userDTO.getSSN());

        request.setAttribute("findID", findID);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher( "main.jsp");
        dispatcher.forward(request, response);
        
        if(findID == null || "".equals(findID)) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            System.out.println("3333333333333333333333333333333");
            script.println("alert('이름 또는 주민번호가 잘못 입력 되었습니다.')");
            System.out.println("4444444444444444444444444444444444");
            script.println("history.back()");
            script.println("</script>");
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            System.out.println("11111111111111111111111111111");
            script.println("alert('아이디는" + request.getAttribute("findID") + "입니다.')");
            System.out.println("22222222222222222222222222222");
            script.println("history.back()");
            script.println("</script>");
        }
    }
}
