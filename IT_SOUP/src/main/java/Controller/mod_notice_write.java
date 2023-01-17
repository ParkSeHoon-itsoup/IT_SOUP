package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.FileDAO;
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
        
        String uploadFilePath = "C:\\NEW";
        
        File path = new File(uploadFilePath);
        if(!path.exists()) {
            path.mkdirs();
        }

        int uploadFilesSizeLimit = 5*1024*1024;
        String encType = "UTF-8";

        MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFilesSizeLimit,encType, new DefaultFileRenamePolicy());
        String N_TITLE = multi.getParameter("N_TITLE");
        String N_CONTENT = multi.getParameter("N_CONTENT");

        NoticeDAO noticeDAO = new NoticeDAO();
        int mod_notice_write = noticeDAO.mod_notice_write(N_NO, N_TITLE, N_CONTENT, NO);
        
        if(mod_notice_write == 1) {
           FileDAO fileDAO = new FileDAO();
           int deleteAttach = fileDAO.deldelAttach(N_NO);
           if(deleteAttach == -1) {
               PrintWriter script = response.getWriter();
               script.println("<script>");
               script.println("alert('첨부파일 삭제에 실패하였습니다.')>");
               script.println("history.back()");
               script.println("</script>");
           } else {
               Enumeration files = multi.getFileNames();
               
               while(files.hasMoreElements()) {
                   String file = (String)files.nextElement();
                   String file_name = multi.getFilesystemName(file);
                   String ori_file_name = multi.getOriginalFileName(file);
                   
                  System.out.println("업로드된 파일명: " + file_name);
                  System.out.println("원본 파일명: " + ori_file_name);
                  
                  File f = multi.getFile(file);
                  int resultAttach = fileDAO.regAttach(N_NO, file_name, ori_file_name);
                  
                  if(resultAttach == -1) {
                      PrintWriter script = response.getWriter();
                      script.println("<script>");
                      script.println("alert('첨부파일 등록에 실패하였습니다.')>");
                      script.println("history.back()");
                      script.println("</script>");
                   } 
               }
           }
           PrintWriter script = response.getWriter();
           script.println("<script>");
           script.println("location.href='notice.jsp'");
           script.println("</script>");
        } else {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('공지사항 글 수정에 실패하였습니다.')>");
            script.println("history.back()");
            script.println("</script>");
        }
	}
}
