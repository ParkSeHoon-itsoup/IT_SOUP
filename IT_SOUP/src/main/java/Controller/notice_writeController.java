package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import DAO.FileDAO;
import DAO.NoticeDAO;

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

        NoticeDAO noticeDAO = new NoticeDAO();
        int NO = noticeDAO.findNo(ID); 
        session.setAttribute("NO", NO);
        
        int N_NO = noticeDAO.findN_NO();

        ServletContext application = request.getServletContext();
        String directory = application.getRealPath("\\upload\\");
        int uploadFilesSizeLimit = 5*1024*1024;
                    
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(directory));
        factory.setSizeThreshold(uploadFilesSizeLimit);
        factory.setDefaultCharset("utf-8");
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        try {
            
            items = upload.parseRequest(request);
            
            System.out.println("size = " + items.size());
            
            for(int i=0; i<items.size();i++) {
                System.out.println(items.get(i));
            }
            
            if(items.get(2).toString() != null) {
                System.out.println("잡았다 요놈~!");
                String beforeSplit = items.get(2).toString();
                String[] afterSplit = beforeSplit.split(", ");
                for(int s=0; s<afterSplit.length; s++) {
                    System.out.println(afterSplit[s]);
                }
                String bytes = "size=0 bytes";
                if(afterSplit[2].equals(bytes)) {
                    System.out.println("게섯거라~!");
                }
                System.out.println(items.get(2).toString());
            }
            
            
            int size =  items.size();
            System.out.println("items.size = " + size);
            
        } catch(FileUploadException e) {
            System.out.println("파싱에러 :" + e);
        }
        try {
            if(items.size() >5) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('첨부파일은 최대 3개까지업로드 할 수 있습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                Iterator itr = items.iterator();
                String beforeSplit = items.get(2).toString();
                String[] afterSplit = beforeSplit.split(", ");
                String bytes = "size=0 bytes";
                if(!afterSplit[2].equals(bytes)) {
                    
                    request.setCharacterEncoding("utf-8");
                    response.setContentType("text/html; charset=utf-8");
                    response.setCharacterEncoding("utf-8");
                    
                    
                    
                    
                    
                    System.out.println("파일O");

                    while(itr.hasNext()) {
                        FileItem item = (FileItem)itr.next();
                        if(item.isFormField()) {
                            if(item != null && item.getFieldName().equals("N_TITLE")) {
                                String N_TITLE = item.getString();
                                System.out.println("N_TITLE = " + N_TITLE);
                                session.setAttribute("N_TITLE", N_TITLE);
                            } else if(item != null && item.getFieldName().equals("N_CONTENT")) {
                                String N_CONTENT = item.getString();
                                System.out.println("N_CONTENT = " + N_CONTENT);
                                session.setAttribute("N_CONTENT", N_CONTENT);
                            }
                        } else {
                        System.out.println("else_getFieldName = " + item.getFieldName());
                        
                        String origin = item.getName();
                        
                        System.out.println("origin = " + origin);
                        
                        String ext = origin.substring(origin.lastIndexOf("."));
                        
                        UUID uuid = UUID.randomUUID();
                        String name = origin;
                        
                        System.out.println("name = " + name);
                        
                        System.out.println(item.getSize());
                        File upPath = new File(directory);
                        if(!upPath.exists()) {
                            upPath.mkdirs();
                        }
                        
                        FileDAO fileDAO = new FileDAO();
                        int resultAttach = fileDAO.regAttach(N_NO, name, origin);
                        
                        item.write(new File(upPath, name));
                        }
                    }
                    String N_TITLE = (String)session.getAttribute("N_TITLE");
                    String N_CONTENT = (String)session.getAttribute("N_CONTENT");
                    int result = noticeDAO.write(N_NO, NO, N_TITLE, N_CONTENT);
                    
                    if(result == -1) {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('파일업로드에 실패하였습니다(파일O)')");
                        script.println("location.href='notice.jsp'");
                        script.println("</script>");
                    } else {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('글쓰기에 성공하였습니다(파일O)')");
                        script.println("location.href='notice.jsp'");
                        script.println("</script>");
                    }
                } else {
                    System.out.println("파일X");
                    while(itr.hasNext()) {
                        FileItem item = (FileItem)itr.next();
                        if(item.isFormField()) {
                            if(item != null && item.getFieldName().equals("N_TITLE")) {
                                String N_TITLE = item.getString();
                                System.out.println("N_TITLE = " + N_TITLE);
                                session.setAttribute("N_TITLE", N_TITLE);
                            } else if(item != null && item.getFieldName().equals("N_CONTENT")) {
                                String N_CONTENT = item.getString();
                                System.out.println("N_CONTENT = " + N_CONTENT);
                                session.setAttribute("N_CONTENT", N_CONTENT);
                            }
                        }
                    }
                    
                    String N_TITLE = (String)session.getAttribute("N_TITLE");
                    String N_CONTENT = (String)session.getAttribute("N_CONTENT");
                    int result = noticeDAO.write(N_NO, NO, N_TITLE, N_CONTENT);
                    
                    if(result == -1) {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('파일업로드에 실패하였습니다.')");
                        script.println("location.href='notice.jsp'");
                        script.println("</script>");
                    } else {
                        PrintWriter script = response.getWriter();
                        script.println("<script>");
                        script.println("alert('글쓰기에 성공하였습니다(파일X)')");
                        script.println("location.href='notice.jsp'");
                        script.println("</script>");
                    }
                    
                    session.removeAttribute("N_TITLE");
                    session.removeAttribute("N_CONTENTTITLE");
                }
            }
        } catch(Exception e) {
            System.out.println("파일업로드 에러_Controller : " + e);
        }
    }
}