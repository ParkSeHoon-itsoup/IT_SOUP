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
            script.println("location.href='login.jsp'");
            script.println("</script>");
        }
        
        String ID = request.getParameter("ID").replace(" ", "");
        String PASSWORD = request.getParameter("PASSWORD").replace(" ", "");
        
        LoginDAO loginDAO = new LoginDAO();
	    int result = loginDAO.login(ID, PASSWORD);
	    
	    if(result == 1) {
	        UserDTO setID = loginDAO.setID(ID);
	        
	        UserDTO userDTO = new UserDTO();
	        userDTO.setNO(setID.getNO());
            userDTO.setNAME(setID.getNAME());
            userDTO.setPASSWORD(setID.getPASSWORD());
	        
            session.setAttribute("PASSWORD", userDTO.getPASSWORD());
            
	        UserDTO  getEMP = loginDAO.getEMP(ID);
	        String NAME = getEMP.getNAME();
	        String CHG_PW_YN = getEMP.getCHG_PW_YN();
	        String LEVEL = getEMP.getLEVEL();
	        String MOD_DD = getEMP.getMOD_DD();
	        
            session.setAttribute("NO", userDTO.getNO());
            session.setAttribute("LEVEL", LEVEL);
	        
	        String naming = NAME + " (" + ID + ")";
	        session.setAttribute("naming", naming);
            session.setAttribute("LEVEL", LEVEL);
    
	        request.setAttribute("naming", naming);
            request.setAttribute("formNm", "notice");
	        
            session.setAttribute("ID", ID);
            request.setAttribute("ID", ID);
            
	        if("".equals(MOD_DD) || null == MOD_DD  &&  "N".equals(CHG_PW_YN)) {
                response.sendRedirect("login.jsp?formNm=chgPwYn&ID=" + ID);
	        } else if((!"".equals(MOD_DD) || null != MOD_DD) && "N".equals(CHG_PW_YN)) {
	            response.sendRedirect("login.jsp?formNm=lostPw&ID=" + ID);
	        } else {
	                PrintWriter script = response.getWriter();
	                script.println("<script>");
	                script.println("location.href='notice.jsp'");
	                script.println("</script>");
	        }
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
        }
	}
}
