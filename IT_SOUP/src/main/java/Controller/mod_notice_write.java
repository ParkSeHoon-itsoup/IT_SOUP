package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import DTO.FileDTO;

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
        
        ServletContext application = request.getServletContext();
        String uploadFilePath = application.getRealPath("\\upload\\");
        
        System.out.println("uploadFilePath = " + uploadFilePath);
        
        File path = new File(uploadFilePath);
        if(!path.exists()) {
            path.mkdirs();
        }

        int uploadFilesSizeLimit = 5*1024*1024;
        String encType = "UTF-8";

        MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFilesSizeLimit,encType, new DefaultFileRenamePolicy());
        String N_TITLE = multi.getParameter("N_TITLE");
        String N_CONTENT = multi.getParameter("N_CONTENT");
        
        System.out.println("N_TITLE_modCon = " + N_TITLE);
        System.out.println("N_CONTENT_modCon = " + N_CONTENT);
        
        if(multi.getParameterValues("F_NO") != null) {
           //updateSavedFile배열 형변환
           String[] updateSavedFile = multi.getParameterValues("F_NO");
           int[] cast = new int[updateSavedFile.length];
           for(int i=0; i<cast.length; i++) {
           cast[i] = Integer.parseInt(updateSavedFile[i]);
           }

           ArrayList<Integer> update = new ArrayList<>();
           //cast[] --> ArrayList
           for(int eachFile : cast) {
              update.add(eachFile);
           }
            
           //log용
           for(int idx=0; idx<update.size(); idx++) {
               System.out.println("update = " + update.get(idx));
           }
           FileDAO saveFileDAO = new FileDAO();
           ArrayList<FileDTO> savedFile = saveFileDAO.getList(N_NO);

           //saveFile log용
           ArrayList<Integer> save = new ArrayList<Integer>();
           for(int idx=0; idx<savedFile.size(); idx++) {
              int F_NO = savedFile.get(idx).getF_NO();
              save.add(F_NO);
               System.out.println("save = " + F_NO);
           }
            
           ArrayList<Integer> mergedList = new ArrayList<>(save);
           mergedList.removeAll(update);
           
           System.out.println("mergedList = " + mergedList);
           
           if(!mergedList.isEmpty()) {
               for(int i=0; i<mergedList.size(); i++) {
                   int F_NO = mergedList.get(i);
                   
                   FileDAO fileDAO = new FileDAO();
                   int result = fileDAO.updateDeleteAttach(N_NO, F_NO);
                   
                   if(result == -1) {
                       PrintWriter script = response.getWriter();
                       script.println("<script>");
                       script.println("alert('첨부파일 수정삭제에 실패하였습니다.')");
                       script.println("location.href='notice.jsp'");
                       script.println("</script>");
                   } else {
                       FileDAO delDAO = new FileDAO();
                       ArrayList<FileDTO> delList = delDAO.getList(N_NO);
                       
                       ArrayList<String> lists = new ArrayList<String>();
                       for(int idx=0; idx<delList.size(); idx++) {
                           String fileName = delList.get(idx).getF_NAME();
                           lists.add(fileName);
                       }
                       
                       System.out.println("lists = " + lists);
                       
                       for(int j=0; j<lists.size(); j++) {
                           String fileName = lists.get(j);
                           
                           System.out.println("fileName = " + fileName);
                           
                           String directory = this.getServletContext().getRealPath("\\upload\\");
                           
                           System.out.println("path = " + directory + "\\" + fileName);
                           
                           File file = new File(directory + "\\" + fileName);
                           if(file.exists()) {
                               System.out.println("해치웠나2?");
                               file.delete();
                           } else {
                               System.out.println("파일이 존재하지 않습니다.");
                           }
                       }
                       
                       PrintWriter script = response.getWriter();
                       script.println("<script>");
                       script.println("alert('첨부파일 수정삭제에 성공하였습니다.')");
                       script.println("location.href='notice.jsp'");
                       script.println("</script>");
                   }
               }
           }
           
           NoticeDAO noticeDAO = new NoticeDAO();
           int mod_notice_write = noticeDAO.mod_notice_write(N_NO, N_TITLE, N_CONTENT, N_NO);
           
           if(mod_notice_write == -1) {
               PrintWriter script = response.getWriter();
               script.println("<script>");
               script.println("alert('공지사항 글 수정에 실패하였습니다.')");
               script.println("location.href='notice.jsp'");
               script.println("</script>");
           } else {
               Enumeration files = multi.getFileNames();
               
               if(!files.hasMoreElements()) {
                   while(files.hasMoreElements()) {
                       String file = (String)files.nextElement();
                       String file_name = multi.getFilesystemName(file);
                       String ori_file_name = multi.getOriginalFileName(file);
                       
                       System.out.println("업로드된 파일명 : " + file_name);
                       System.out.println("원본 파일명 : " + ori_file_name);
                       
                       FileDAO fileDAO = new FileDAO();
                       File f = multi.getFile(file);
                       int resultAttach = fileDAO.regAttach(N_NO, file_name, ori_file_name);
                       
                       if(resultAttach == -1) {
                           PrintWriter script = response.getWriter();
                           script.println("<script>");
                           script.println("alert('첨부파일 등록에 실패하였습니다.')");
                           script.println("location.href='notice.jsp'");
                           script.println("</script>");
                       } else {
                           PrintWriter script = response.getWriter();
                           script.println("<script>");
                           script.println("alert('공지사항을 수정하였습니다.')");
                           script.println("location.href='notice.jsp'");
                           script.println("</script>");
                       }
                   }
               } else {
                   PrintWriter script = response.getWriter();
                   script.println("<script>");
                   script.println("alert('공지사항을 수정하였습니다.')");
                   script.println("location.href='notice.jsp'");
                   script.println("</script>");
               }
           }
        }
	}
}
