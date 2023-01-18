package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.FileDAO;
import DAO.NoticeDAO;
import DTO.FileDTO;

@WebServlet("/delete_notice_writeController")
public class delete_notice_writeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UTF-8 인코딩
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        int N_NO = Integer.parseInt(request.getParameter("N_NO"));
        
        FileDAO fileDAO2 = new FileDAO();
        ArrayList<FileDTO> delList = fileDAO2.getList(N_NO);
        
        ArrayList<String> lists = new ArrayList<String>();
        for(int i=0; i<delList.size(); i++){
            String fileName = delList.get(i).getF_NAME();
            lists.add(fileName);
        }
        
        NoticeDAO noticeDAO = new NoticeDAO();
        int delete = noticeDAO.delete_notice_write(N_NO);
        
        if(delete == 1) {
            FileDAO fileDAO = new FileDAO();
            int result = fileDAO.deldelAttach(N_NO);
            
            if(result == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('첨부파일 삭제에 실패하였습니다.')");
                script.println("location.href='notice.jsp?formNm=notice'");
                script.println("</script>");
            } else {
                System.out.println("lists = " + lists);
                for(int i=0; i<lists.size(); i++) {
                    String fileName = lists.get(i);
                    
                    System.out.println("fileName" + i +" = " + fileName);
                    
                    String directory = this.getServletContext().getRealPath("\\upload\\");
                    
                    System.out.println("path = " + directory + "\\" + fileName);
                    
                    File file = new File(directory + "\\" + fileName);
                    if(file.exists()) {
                        System.out.println("해치웠나?");
                        file.delete();
                    }else {
                        System.out.println("파일이 존재하지 않습니다.");   
                    }
                }
                request.setAttribute("url", request.getContextPath() + "\\download\\");
                
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href='notice.jsp'");
                script.println("</script>");
             }
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('공지사항 삭제에 실패하였습니다..')");
            script.println("location.href='notice.jsp?formNm=notice'");
            script.println("</script>");
        }
	}
}
