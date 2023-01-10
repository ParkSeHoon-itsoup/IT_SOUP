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
        
        String findNM = request.getParameter("NAME").replace(" ", "");
        String findSSN = request.getParameter("SSN").replace(" ", "");
        
        FindDAO findDAO = new FindDAO();
        String findID = findDAO.find(findNM, findSSN);
        if("".equals(findID) || findID == null) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('이름 또는 주민번호가 잘못 입력 되었습니다.')");
                script.println("history.back()");
                script.println("</script>");
            }  else {
            request.setAttribute("findID", findID);
            
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('아이디는" + request.getAttribute("findID") + "입니다.')");
            script.println("location.href='login.jsp'");
            script.println("</script>");
        }
        
    }
}
