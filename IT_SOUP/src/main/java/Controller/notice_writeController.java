package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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

        NoticeDAO noticeDAO = new NoticeDAO();
        int NO = noticeDAO.findNo(ID); 
        session.setAttribute("NO", NO);
        
        int N_NO = noticeDAO.findN_NO();

            ServletContext application = request.getServletContext();
            String directory = application.getRealPath("\\upload\\");
            int uploadFilesSizeLimit = 5*1024*1024;
            
    //        String saveDir = "C:\\Users\\chulg\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\IT_SOUP\\upload";
    //        int size = 5 * 1024 * 1024;
            
            File currentDir = new File(directory);
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(currentDir);
            factory.setSizeThreshold(uploadFilesSizeLimit);
            
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = upload.parseRequest(request);
            } catch(FileUploadException e) {
                System.out.println("에러 1 :" + e);
            }
            try {
                Iterator itr = items.iterator();
                while(itr.hasNext()) {
                    FileItem item = (FileItem)itr.next();
                    if(item.isFormField()) {
                        if(item != null && item.getFieldName().equals("N_TITLE")) {
                            String N_TITLE = item.getString();
                            System.out.println("N_TITLE = " + N_TITLE);
                            
                            session.setAttribute("N_TITLE", N_TITLE);
                            
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
                    File upPath = new File(currentDir + "\\" + getTodayStr());
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
                
                session.removeAttribute("N_TITLE");
                session.removeAttribute("N_CONTENTTITLE");
            } catch(Exception e) {
                System.out.println("씨발 : " + e);
            }
    }
    
    private String getTodayStr() {
        return new SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis());
    }
}