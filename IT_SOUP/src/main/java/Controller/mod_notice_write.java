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

import DAO.NoticeDAO;

@WebServlet("/mod_notice_write")
public class mod_notice_write extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        HttpSession session = request.getSession();
        int NO  =(int) session.getAttribute("NO");
        
        
        int N_NO = Integer.parseInt(request.getParameter("N_NO"));
        String N_TITLE = request.getParameter("N_TITLE");
        String N_CONTENT = request.getParameter("N_CONTENT");
        
        NoticeDAO noticeDAO = new NoticeDAO();
        int mod_notice_write = noticeDAO.mod_notice_write(N_NO, N_TITLE, N_CONTENT, NO);
        
        if(mod_notice_write == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href='notice.jsp'");
            script.println("</script>");
//            RequestDispatcher dispatcher = request.getRequestDispatcher( "notice.jsp");
//            dispatcher.forward(request, response);
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('공지사항 글 수정에 실패하였습니다.')>");
            script.println("history.back()");
            script.println("</script>");
        }
	}
}
