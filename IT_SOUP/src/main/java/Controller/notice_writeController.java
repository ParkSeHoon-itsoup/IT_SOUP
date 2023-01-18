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

        ServletContext application = request.getServletContext();
        String directory = application.getRealPath("\\upload\\");
        System.out.println("directory = " + directory);
        
        File path = new File(directory);
        if(!path.exists()) {
            path.mkdirs();
        }

        int uploadFilesSizeLimit = 5*1024*1024;
        String encType = "UTF-8";

        MultipartRequest multi = new MultipartRequest(request, directory, uploadFilesSizeLimit,encType, new DefaultFileRenamePolicy());
        String N_TITLE = multi.getParameter("N_TITLE");
        String N_CONTENT = multi.getParameter("N_CONTENT");
        System.out.println("N_TITLE = " + N_TITLE);
        System.out.println("N_CONTENT = " + N_CONTENT);
            
        if((null == N_TITLE || "".equals(N_TITLE)) ||(null == N_CONTENT || "".equals(N_CONTENT))) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('입력이 안 된 사항이 있습니다.')");
           script.println("history.back()");
            script.println("</script>");
        } else {
            NoticeDAO noticeDAO = new NoticeDAO();
            int NO = noticeDAO.findNo(ID); 
            session.setAttribute("NO", NO);
            
            int N_NO = noticeDAO.findN_NO();
            
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setN_NO(N_NO);
            noticeDTO.setNO(NO);
            noticeDTO.setN_TITLE(N_TITLE);
            noticeDTO.setN_CONTENT(N_CONTENT);
            
            int result = noticeDAO.write(noticeDTO);
            
            if(result == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('공지사항  등록에 실패하였습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                    FileDAO fileDAO = new FileDAO();
                    Enumeration files = multi.getFileNames();
                        while(files.hasMoreElements()){
                                String file = (String)files.nextElement();
                                String file_name = multi.getFilesystemName(file);
                                String ori_file_name = multi.getOriginalFileName(file);
                                
                               File f = multi.getFile(file);
                               
                               if(!("".equals(file_name) || null ==file_name)) {
                                   
                                   int resultAttach = fileDAO.regAttach(N_NO, file_name, ori_file_name);
                                   
                                   if(resultAttach == -1) {
                                       PrintWriter script = response.getWriter();
                                       script.println("<script>");
                                       script.println("alert('파일업로드 실패.')");
                                       script.println("location.href='notice.jsp'");
                                       script.println("</script>");
                                   }
                               }
                        }
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('새로운 공지사항을 등록하였습니다.')");
                        script.println("location.href='notice.jsp'");
                        script.println("</script>");
            }
        } 
    }
}
