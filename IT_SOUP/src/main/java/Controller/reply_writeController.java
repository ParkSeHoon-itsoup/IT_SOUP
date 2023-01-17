package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.NoticeDAO;

/**
 * Servlet implementation class reply_writeController
 */
@WebServlet("/reply_writeController")
public class reply_writeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        //int NO = Integer.parseInt(request.getParameter("NO"));
    //    System.out.println("NO" + NO);
        int N_NO = Integer.parseInt(request.getParameter("N_NO"));
        System.out.println("N_NO = " + N_NO);
        int REPLY = Integer.parseInt(request.getParameter("REPLY"));
        System.out.println("REPLY = " + REPLY);
        int RE_NO = Integer.parseInt(request.getParameter("RE_NO"));
        System.out.println("RE_NO = " + RE_NO);
        String R_TITLE = request.getParameter("R_TITLE");
        System.out.println("R_TITLE" + R_TITLE);
        String R_CONTENT = request.getParameter("R_CONTENT");
        System.out.println("NO = " + R_CONTENT);
        String P_ID = String.valueOf(N_NO) +String.valueOf(REPLY) + String.valueOf(RE_NO); 
        System.out.println("P_ID = " + P_ID);
//        
//        if(REPLY == 0) {
//            NoticeDAO noticeDAO = new NoticeDAO();
//     //       int result = noticeDAO.reply_write(NO, N_NO, REPLY, RE_NO, R_TITLE,  R_CONTENT, P_ID);
//            
//            if(result == 1) {
//                PrintWriter script = response.getWriter();
//                script.println("<script>");
//                script.println("alert('새로운 댓글을 등록하였습니다.')");
//                script.println("location.href='notice.jsp'");
//                script.println("</script>");
//            } else {
//                PrintWriter script = response.getWriter();
//                script.println("<script>");
//                script.println("alert('댓글 등록에 실패하였습니다..')");
//                script.println("location.href='notice.jsp'");
//                script.println("</script>");
//            }
//        } else {
//            NoticeDAO noticeDAO = new NoticeDAO();
//            int result = noticeDAO.reply_write(NO, N_NO, REPLY, RE_NO, R_TITLE,  R_CONTENT, P_ID);
//            
//            if(result == 1) {
//                PrintWriter script = response.getWriter();
//                script.println("<script>");
//                script.println("alert('새로운 댓글을 등록하였습니다.')");
//                script.println("location.href='notice.jsp'");
//                script.println("</script>");
//            } else {
//                PrintWriter script = response.getWriter();
//                script.println("<script>");
//                script.println("alert('댓글 등록에 실패하였습니다..')");
//                script.println("location.href='notice.jsp'");
//                script.println("</script>");
//            }
//        }
	}
}
