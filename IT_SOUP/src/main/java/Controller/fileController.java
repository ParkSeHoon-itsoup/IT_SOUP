package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.FileDAO;

@WebServlet("/fileController")
public class fileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("해치웠나?");
	    
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
//        String savePath = "upload";
//        ServletContext context = getServletContext();
//        String uploadFilePath = context.getRealPath(savePath);
            String uploadFilePath = "C:\\NEW";
            
        File path = new File(uploadFilePath);
        if(!path.exists()) {
            path.mkdirs();
        }

        int uploadFilesSizeLimit = 5*1024*1024;
        String encType = "UTF-8";
        
        try {
            MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFilesSizeLimit,encType, new DefaultFileRenamePolicy());
            Enumeration files = multi.getFileNames();
            while(files.hasMoreElements()) {
                HttpSession session = request.getSession();
                int N_NO = (int)session.getAttribute("N_NO");
                
                System.out.println("N_NO = " + N_NO);
                
                
                String file = (String)files.nextElement();
                String file_name = multi.getFilesystemName(file);
                String ori_file_name = multi.getOriginalFileName(file);
                
               System.out.println("업로드된 파일명: " + file_name);
               System.out.println("원본 파일명: " + ori_file_name);
               
               if("".equals(ori_file_name) || null == ori_file_name) {
                   PrintWriter script = response.getWriter();
                   script.println("<script>");
                   script.println("alert('새로운 공지사항을 등록하였습니다.')");
                   script.println("location.href='notice.jsp'");
                   script.println("</script>");
               } else {
                   FileDAO fileDAO = new FileDAO();
                   int result = fileDAO.regAttach(N_NO, file_name, ori_file_name);
                   if(result == 1) {
                       PrintWriter script = response.getWriter();
                       script.println("<script>");
                       script.println("alert('새로운 공지사항을 등록하였습니다.')");
                       script.println("location.href='notice.jsp'");
                       script.println("</script>");
                   } else {
                       PrintWriter script = response.getWriter();
                       script.println("<script>");
                       script.println("alert('공지사항  등록에 실패하였습니다.')");
                       script.println("location.href='notice.jsp'");
                       script.println("</script>");
                   }
               }
            }
        } catch(Exception e) {
            System.out.println("파일 업로드 실패 : " + e);
        }
	}
}
