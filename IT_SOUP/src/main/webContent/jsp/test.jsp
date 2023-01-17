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
        
.page_wrap {
    text-align:center;
    font-size:0;
 }
.page_nation {
    display:inline-block;
}
.page_nation .none {
    display:none;
}
.page_nation a {
    display:block;
    margin:0 3px;
    float:left;
    border:1px solid #e6e6e6;
    width:28px;
    height:28px;
    line-height:28px;
    text-align:center;
    background-color:#fff;
    font-size:13px;
    color:#999999;
    text-decoration:none;
}
.page_nation .arrow {
    border:1px solid #ccc;
}
.page_nation .pprev {
    background:#f8f8f8 url('img/page_pprev.png') no-repeat center center;
    margin-left:0;
}
.page_nation .prev {
    background:#f8f8f8 url('img/page_prev.png') no-repeat center center;
    margin-right:7px;
}
.page_nation .next {
    background:#f8f8f8 url('img/page_next.png') no-repeat center center;
    margin-left:7px;
}
.page_nation .nnext {
    background:#f8f8f8 url('img/page_nnext.png') no-repeat center center;
    margin-right:0;
}
.page_nation a.active {
    background-color:#42454c;
    color:#fff;
    border:1px solid #42454c;
}
</style>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body> 
    <%
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    
        String formNm = request.getParameter("formNm");
        String searchField = request.getParameter("searchField");
        String searchText = request.getParameter("searchText");
       
       System.out.println("formNm = " + formNm);
       System.out.println("searchField = " + searchField);
       System.out.println("searchText = " + searchText);
      
        session = request.getSession();
        String naming = (String)session.getAttribute("naming");
        String ID           = (String)session.getAttribute("ID");
        String NAME   = (String)session.getAttribute("NAME");
        String LEVEL   = (String)session.getAttribute("LEVEL");

        if( (String)request.getAttribute("ID") != null){
            ID =  (String)request.getAttribute("ID");
        }

       String pageNumber = request.getParameter("pageNumber");
       if(pageNumber == null){
           pageNumber = "1";
       }
        
        int currentPage = Integer.parseInt(pageNumber);
        int startRow = (currentPage-1)*10 + 1;
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
                            <li> <a href="login.jsp?formNm=join" style="position:relative;">회원가입</a></li>
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
                if("searchList".equals(formNm)){
                    NoticeDAO noticeDAO = new NoticeDAO();
                     ArrayList<NoticeDTO> searchList = noticeDAO.getsearchList(searchField, searchText, startRow);
                     
                     System.out.println("currentPage = " + currentPage);
                     System.out.println("strartRow = " + startRow);
                     
                     for(int i=0; i<searchList.size(); i++){
                %>
                    <tr>
                        <td style="text-align:center"><%=searchList.get(i).getN_NO() %></td>
                        <td style="text-align:left"><a href="notice_write.jsp?N_NO=<%=searchList.get(i).getN_NO() %>&REPLY=<%=searchList.get(i).getREPLY() %>&RE_NO=<%=searchList.get(i).getRE_NO() %>&formNm=notice_read"><%=searchList.get(i).getN_TITLE().replaceAll(" ", "&nbsp;").replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></a></td>
                        <td style="text-align:center"><%=searchList.get(i).getNAME() + "(" + searchList.get(i).getID()+ ")" %></td>
                        <td style="text-align:center"><%=searchList.get(i).getN_DATE().substring(0,4) + "년 "  + searchList.get(i).getN_DATE().substring(5,7) + "월 " + searchList.get(i).getN_DATE().substring(8,10) + "일"%></td>
                    </tr>
                <%
                   }
                %>
                </tbody>
            </table>
                <%
                        int cnt = noticeDAO.listCount();
                        int pageCount = cnt/10 + (cnt*10==0?0:1);
                        int pageBlock = 10;
                        int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
                        int endPage = startPage + pageBlock-1;
                       
                       if(endPage > pageCount){
                           endPage = pageCount;
                       }
                %>
                       <a href="notice.jsp?pageNumber=1"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''">[처음]</a>
                <%
                if(startPage>pageBlock){
                %>
                       <a href="notice.jsp?pageNumber=1"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''">[처음]</a>
                       <a href="notice.jsp?pageNumber=<%=startPage-pageBlock %>"  onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''" >[이전]</a>
                <%
                       }
                
                      for(int idx=startPage; idx<=endPage; idx++){
                %>
                        <a href="notice.jsp?pageNumber=<%=idx  %>" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''"  class="page" onClic="change()"><%=idx %></a>
                <%
                      }
                
                     if(endPage<pageCount){
                %>
                         <a href="notice.jsp?pageNumber=<%=startPage+pageBlock %>"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''">[다음]</a>
                         <a href="notice.jsp?pageNumber=<%=pageCount %>"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''" style="">[마지막]</a>
                        
            <%
                 } 
            if("01".equals(LEVEL) || "02".equals(LEVEL)){
            %>
             <a href="notice_write.jsp?naming=<%= naming %>&formNm=notice_write" class="btn btn-primary pull-right">글쓰기</a>
             <a href="notice.jsp" class="btn btn-primary pull-right">목록</a>
             <%
            }
            %>
            <form method="post" name="search" action="notice.jsp?formNm=searchList">
                <table class="pull-right">
                    <tr>
                        <td><select class="form-control" name="searchField">
                                 <option value="N_TITLE">제목</option>
                                 <option value="N_CONTENT">내용</option>
                                 <option value="TOTAL">제목  + 내용</option>
                                 </select></td>
                        <td><input type="text" class="form-control" placeholder="검색어 입력" name="searchText" maxlength="100"></td>
                        <td><button type="submit" class="btn btn-primary">검색</button></td>
                    </tr>
               </table>
           </form>
             <%
                } else {
                   NoticeDAO noticeDAO = new NoticeDAO();
                   int cnt = noticeDAO.listCount();
                   ArrayList<NoticeDTO> list = noticeDAO.getList(startRow);
                   
                   for(int i=0; i<list.size(); i++){
                   %>
                    <tr>
                        <td style="text-align:center"><%=list.get(i).getN_NO() %></td>
                        <%
                        int reply = list.get(i).getREPLY();
                        String RE = "";
                        
                        for(int r=0; r<reply; r++){
                            RE += "\t\t[RE]" ;
                        }
                        %>
                        <td style="text-align:left"><a href="notice_write.jsp?N_NO=<%=list.get(i).getN_NO() %>&REPLY=<%=list.get(i).getREPLY() %>&RE_NO=<%=list.get(i).getRE_NO() %>&formNm=notice_read"><%=RE + list.get(i).getN_TITLE().replaceAll(" ", "&nbsp;").replaceAll("<", "&alt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></a></td>
                        <td style="text-align:center"><%=list.get(i).getNAME() + "(" + list.get(i).getID()+ ")" %></td>
                        <td style="text-align:center"><%=list.get(i).getN_DATE().substring(0,4) + "년 "  + list.get(i).getN_DATE().substring(5,7) + "월 " + list.get(i).getN_DATE().substring(8,10) + "일"%></td>
                    </tr>
                <%
                   }
                %>
                </tbody>
            </table>
            <%
                   int pageCount = cnt/10 + (cnt*10==0?0:1);
                   int pageBlock = 10;
                   int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
                   int endPage = startPage + pageBlock-1;
                   
                   if(endPage > pageCount){
                       endPage = pageCount;
                   }
            %>
            <%
            if(startPage>pageBlock){
            %>
                <div class="page_wrap">
                    <div class="page_nation">
                   <a class="arrow pprev" href="notice.jsp?pageNumber=1"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''">[처음]</a>
                   <a class="arrow prev"href="notice.jsp?pageNumber=<%=startPage-pageBlock %>"  onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''" >[이전]</a>
            <%
                   }
            
                  for(int idx=startPage; idx<=endPage; idx++){
            %>
                    <a class="active" href="notice.jsp?pageNumber=<%=idx  %>" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''" ><%=idx %></a>
            <%
                  }
            
                 if(endPage<pageCount){
            %>
                     <a class="arrow next" href="notice.jsp?pageNumber=<%=startPage+pageBlock %>"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''">[다음]</a>
                     <a class="arrow nnext" href="notice.jsp?pageNumber=<%=pageCount %>"onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight=''" style="">[마지막]</a>
                     </div>
                 </div>
            <%
                 } 
            if("01".equals(LEVEL) || "02".equals(LEVEL)){
            %>
             <a href="notice_write.jsp?naming=<%= naming %>&formNm=notice_write" class="btn btn-primary pull-right">글쓰기</a>
             <a href="notice.jsp" class="btn btn-primary pull-right">목록</a>
             <%
            }
            %>
            <form method="post" name="search" action="notice.jsp?formNm=searchList">
            <table class="pull-right">
            <tr>
                <td><select class="form-control" name="searchField">
                         <option value="N_TITLE">제목</option>
                         <option value="N_CONTENT">내용</option>
                         <option value="TOTAL">제목  + 내용</option>
                </select></td>
                <td><input id="search" type="text" class="form-control" placeholder="검색어 입력" name="searchText" maxlength="100"></td>
                <td><button type="submit" class="btn btn-primary">검색</button></td>
            </tr>
        </table>
    </form>
       <%
        }
        %>
      </div>
    </div>
        <script src="js/bootstrap.js"></script>
</body>
</html>
<div class="page_wrap">
   <div class="page_nation">
      <a class="arrow pprev" href="#"></a>
      <a class="arrow prev" href="#"></a>
      <a href="#" class="active">1</a>
      <a href="#">2</a>
      <a href="#">3</a>
      <a href="#">4</a>
      <a href="#">5</a>
      <a href="#">6</a>
      <a href="#">7</a>
      <a href="#">8</a>
      <a href="#">9</a>
      <a href="#">10</a>
      <a class="arrow next" href="#"></a>
      <a class="arrow nnext" href="#"></a>
   </div>
</div>
</body>
</html>