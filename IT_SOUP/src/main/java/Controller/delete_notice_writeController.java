package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.NoticeDAO;

@WebServlet("/delete_notice_writeController")
public class delete_notice_writeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        int N_NO = Integer.parseInt(request.getParameter("N_NO"));
        
        NoticeDAO noticeDAO = new NoticeDAO();
        int delete = noticeDAO.delete_notice_write(N_NO);
        
        if(delete == 1) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href='notice.jsp'");
            script.println("</script>");
            
//            RequestDispatcher dispatcher = request.getRequestDispatcher( "notice.jsp");
//            dispatcher.forward(request, response);
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('공지사항 삭제에 실패하였습니다..')");
            script.println("location.href='notice.jsp?formNm=notice'");
            script.println("</script>");
        }
	}
}
