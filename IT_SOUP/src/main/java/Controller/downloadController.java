package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadController")
public class downloadController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String fileName = "일정관리(1차).xlsx";
//            String fileName = request.getParameter("FILENAME");
            
            // 다운로드 경로 (내려받을 파일경로를 설정한다.)
            String filePath = "C:\\NEW";
            
            // 경로와 파일명으로 파일 객체를 생성한다.
            File file  = new File(filePath, fileName);
            
            int fileSize = (int) file.length();
            
            if (fileSize > 0) {
                
                String encodedFilename = "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8");
                
                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Content-Disposition", encodedFilename);
                response.setContentLengthLong(fileSize);

                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                
                try {
                    byte[] buffer = new byte[4096];
                    int bytesRead=0;
                    
                    while ((bytesRead = in.read(buffer))!=-1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    
                    out.flush();
                }
                finally {
                    in.close();
                    out.close();
                }
            } else {
                throw new FileNotFoundException("파일이 없습니다.");
            }
        }
        catch (Exception e) {
           System.out.println("다운로드 실패 : " + e);
        }
      }
}
