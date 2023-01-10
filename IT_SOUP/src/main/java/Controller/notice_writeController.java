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
import DTO.NoticeDTO;

@WebServlet("/notice_writeController")
public class notice_writeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String ID = (String)session.getAttribute("ID");
        
        String N_TITLE = request.getParameter("N_TITLE");
        String N_CONTENT = request.getParameter("N_CONTENT");
        
        String formNm = request.getParameter("formNm");
        
        if((null == N_TITLE || "".equals(N_TITLE)) ||(null == N_CONTENT || "".equals(N_CONTENT))) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('입력이 안 된 사항이 있습니다.')>");
           script.println("history.back()");
            script.println("</script>");
        } else{
            NoticeDAO noticeDAO = new NoticeDAO();
            int NO = noticeDAO.findNo(ID); 
            session.setAttribute("NO", NO);
            
            int notice_write = noticeDAO.write(NO, N_TITLE, N_CONTENT);
            
            if(notice_write == 1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('새 공지사항을 등록하였습니다.')>");
                script.println("location.href='notice.jsp'");
                script.println("</script>");
            } else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('공지사항  등록에 실패하였습니다.')>");
                script.println("history.back()");
                script.println("</script>");
            }
        } 
    }
}
