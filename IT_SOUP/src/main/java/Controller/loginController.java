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

import DAO.LoginDAO;
import DTO.UserDTO;

@WebServlet("/loginController")
public class loginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        HttpSession session = request.getSession();
        
        String user = null;
                
        if((String)session.getAttribute("ID") != null) {
            user = (String)session.getAttribute("ID");
        }
        
        if(user != null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('이미 로그인 되어 있습니다.')");
            script.println("location.href='main.jsp");
            script.println("</script>");
        }
        
        String ID = request.getParameter("ID").replace(" ", "");
        String PASSWORD = request.getParameter("PASSWORD").replace(" ", "");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setID(ID);
        userDTO.setPASSWORD(PASSWORD);
        
        LoginDAO loginDAO = new LoginDAO();
        
	    int result = loginDAO.login(userDTO.getID(), userDTO.getPASSWORD());
	    
	    if(result == 1) {
	        session.setAttribute("ID", ID);
	        
	        String namingResult = loginDAO.naming(userDTO.getID());
	        ID = (String)session.getAttribute("ID");
	        
	        String name = loginDAO.name(ID);
	        session.setAttribute("name", name);
	        
	        String naming = namingResult + " (" + ID + ")";
	        
	        request.setAttribute("naming", naming);
            request.setAttribute("formNm", "notice");
	        
            RequestDispatcher dispatcher = request.getRequestDispatcher( "notice.jsp");
            dispatcher.forward(request, response);
	    } else if(result == 0) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호가 틀립니다.')");
            script.println("history.back()");
            script.println("</script>");
	    } else if(result == -1){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('존재하지 않는 아이디 입니다.')");
            script.println("history.back()");
            script.println("</script>");
        } else if(result == -2) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('서버오류')");
            script.println("history.back()");
            script.println("</script>");
        }
	}
}
