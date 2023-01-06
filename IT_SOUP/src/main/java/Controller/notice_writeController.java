package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.NoticeDAO;

@WebServlet("/notice_writeController")
public class notice_writeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    System.out.println("??????");
	    
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        
        String ID = (String)session.getAttribute("ID");
        
        NoticeDAO noticeDAO = new NoticeDAO();
        int NO = noticeDAO.findNo(ID); 
        
        String N_TITLE = request.getParameter("N_TITLE");
        String NAME =(String) session.getAttribute("NAME");
        String N_CONTENT = request.getParameter("N_CONTENT");
                
        int notice_write = noticeDAO.write(NO, N_TITLE,NAME,  N_CONTENT);
        
        System.out.println("notice_write = " + notice_write);
	}
}
