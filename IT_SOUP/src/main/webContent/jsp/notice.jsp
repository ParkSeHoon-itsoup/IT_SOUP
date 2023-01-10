<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="DAO.NoticeDAO" %>
<%@ page import="DTO.NoticeDTO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>IT_SOUP</title> 
<style type="text/css">
    a, a:hover{
        color : #000000;
        text-decoration:none;
    }
</style>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body> 
    <%
        String formNm = request.getParameter("formNm");
            
        session = request.getSession();
        String naming = (String)session.getAttribute("naming");
        String ID           = (String)session.getAttribute("ID");
        String NAME   = (String)session.getAttribute("NAME");
        String LEVEL   = (String)session.getAttribute("LEVEL");

        if( (String)request.getAttribute("ID") != null){
            ID =  (String)request.getAttribute("ID");
        }

        int pageNumber = 1;
        if(request.getParameter("pageNumber") != null){
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        
        int currentPage = pageNumber;
        int strartRow = (pageNumber-1)*10 +1;
    %>
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <a class="navbar-brand" href="notice.jsp">IT_SOUP</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="">공지사항</a></li>
                <li ><a href="">주소록</a></li>
            </ul>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" 
                            data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=naming %><span class="caret"></span></a>
                        <%
                        if("01".equals(LEVEL) || "02".equals(LEVEL)){
                        %>
                        <ul class="dropdown-menu">
                            <li><a href="logoutController">로그아웃</a></li>
                            <li><a href="">내정보관리</a></li>
                            <li> <a href="main.jsp?formNm=join" style="position:relative;">회원가입</a></li>
                        </ul>
                        <%
                        } else {
                        %>
                        <ul class="dropdown-menu">
                            <li><a href="logoutController">로그아웃</a></li>
                            <li><a href="">내정보관리</a></li>
                        </ul>
                        <%
                        }
                        %>
                    </li>
                </ul>
            </div>
        </div>
     </nav>
     <div>
     </div>
         <div class="container">
        <div class="row">
            <table class="table table-striped" style="text-align:enter; border:1px solid #dddddd;'">
                <thead>
                    <tr>
                        <th style="background-color:#eeeeee; text-align:center;">번호</th>
                        <th style="background-color:#eeeeee; text-align:center;">제목</th>
                        <th style="background-color:#eeeeee; text-align:center;">작성자</th>
                        <th style="background-color:#eeeeee; text-align:center;">작성일</th>
                    </tr>
                </thead>
                <tbody>
                <%
                   NoticeDAO noticeDAO = new NoticeDAO();
                   int cnt = noticeDAO.listCount();
                   ArrayList<NoticeDTO> list = noticeDAO.getList(strartRow);
                   
                   for(int i=0; i<list.size(); i++){
                %>
                    <tr>
                        <td style="text-align:center"><%=list.get(i).getN_NO() %></td>
                        <td style="text-align:left"><a href="notice_write.jsp?N_NO=<%=list.get(i).getN_NO() %>&formNm=notice_read"><%=list.get(i).getN_TITLE().replaceAll(" ", "&nbsp;").replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></a></td>
                        <td style="text-align:center"><%=list.get(i).getNAME() %></td>
                        <td style="text-align:center"><%=list.get(i).getN_DATE().substring(0,11)%></td>
                    </tr>
                <%
                   }
                %>
                </tbody>
            </table>
            <%
                   int pageCount = cnt/10 + (cnt*10==0?0:1);
                   int pageBlock = 10;
                   int startPage = ((pageNumber-1)/pageBlock)*pageBlock+1;
                   int endPage = startPage + pageBlock-1;
                   
                   if(endPage > pageCount){
                       endPage = pageCount;
                   }

                  if(startPage>pageBlock){
            %>
                   <a href="notice.jsp?pageNumber=<%=startPage-pageBlock %>" >[이전]</a>
            <%
                   }
            
                  for(int idx=startPage; idx<=endPage; idx++){
            %>
                    <a href="notice.jsp?pageNumber=<%=idx  %>"><%=idx %></a>
            <%
                  }
            
                 if(endPage<pageCount){
            %>
                     <a href="notice.jsp?pageNumber=<%=startPage+pageBlock %>">[다음]</a>
            <%
                 } 
            
            if("01".equals(LEVEL) || "02".equals(LEVEL)){
            %>
             <a href="notice_write.jsp?naming=<%= naming %>&formNm=notice_write" class="btn btn-primary pull-right">글쓰기</a>
             <%
            }
             %> 
        </div>
    </div>
        <script src="js/bootstrap.js"></script>
</body>
</html>