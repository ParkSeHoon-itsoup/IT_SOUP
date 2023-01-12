<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="DAO.NoticeDAO" %>
<%@ page import="DTO.NoticeDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>IT_SOUP</title> 
<style type="text/css">
</style>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script>
function check(){
    var getN_TITLE = $("#N_TITLE").val();
    var getN_CONTENT = $("#N_CONTENT").val();
    var getR_TITLE = $("#R_TITLE").val();
    var getR_CONTENT = $("#R_CONTENT").val();
    
    if(getN_TITLE.length == 0){
        alert("[제목] 제목은 필수입력사항입니다.");
        $("#N_TITLE").val("");
        $("#N_TITLE").focus();
        return false;
    }
    
    if(getN_CONTENT.length == 0){
        alert("[내용] 내용은 필수입력사항입니다.");
        $("#N_CONTENT").val("");
        $("#N_CONTENT").focus();
        return false;
    }
    
    if(getR_TITLE.length == 0){
        alert("[제목] 제목은 필수입력사항입니다.");
        $("#R_TITLE").val("");
        $("#R_TITLE").focus();
        return false;
    }
    
    if(getR_CONTENT.length == 0){
        alert("[내용] 내용은 필수입력사항입니다.");
        $("#R_CONTENT").val("");
        $("#R_CONTENT").focus();
        return false;
    }
    
}
</script>
</head>
<body> 
    <%
        String formNm = request.getParameter("formNm");
        
        System.out.println("formNm = " + formNm);
    
        session = request.getSession();
        
        int NO = (int)session.getAttribute("NO");
        String ID = (String)session.getAttribute("ID");
        String naming = (String)session.getAttribute("naming");
        
        if( (String)request.getAttribute("ID") != null){
            ID =  (String)request.getAttribute("ID");
        }
        
        String LEVEL = (String)session.getAttribute("LEVEL");
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
                            data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=naming%> <span class="caret"></span></a>
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
     <%
     if("notice_write".equals(formNm)){
     %>
    <div class="container">
        <div class="row">
            <form method="post" action="notice_writeController" onsubmit="return check()">
                <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                    <thead>
                    <tr>
                        <th colspan="2" style="background-color:#eeeeee; text-align:center;">공지사항 작성</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                         <td><input type="text" class="form-control" placeholder="글 제목" name="N_TITLE" maxlength="50"></td> 
                    </tr>
                    <tr>
                         <td><textarea class="form-control" placeholder="글 내용" name="N_CONTENT" maxlength="2048" style="height:350px;"></textarea></td>
                    </tr>
                </tbody>
                </table>
             <input type="submit" class="btn btn-primary pull-right" style="position:relative;" value="등록">
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; right:10px;">목록</a>
            </form>
        </div>
    </div>
    <%
     }  else if("reply_write".equals(formNm)){
         int N_NO = Integer.parseInt(request.getParameter("N_NO"));
         int REPLY = Integer.parseInt(request.getParameter("REPLY"));
         int RE_NO = Integer.parseInt(request.getParameter("RE_NO"));
         
         
    %><div class="container">
        <div class="row">
            <form method="post" action="reply_writeController?N_NO=<%=N_NO %>&NO=<%=NO %>&REPLY=<%=REPLY %>&RE_NO=<%=RE_NO %>" onsubmit="return check()">
                <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                    <thead>
                    <tr>
                        <th colspan="3" style="background-color:#eeeeee; text-align:center;">댓글 작성</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                         <td style="width:150px; ">제목</td><td><input  class="form-control" style="text-align: left;"  name="R_TITLE" id = "N_TITLE"></td> 
                    </tr>
                    <tr>
                         <td style="width:150px; ">내용</td><td><textarea class="form-control"  style="min-height: 400px; text-align: left;"  name="R_CONTENT" id = "N_CONTENT"></textarea></td>
                    </tr>
                </tbody>
                </table>
             <input type="submit" class="btn btn-primary pull-right" style="position:relative;" value="등록">
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; right:10px;">목록</a>
            </form>
        </div>
    </div>
    <%
     } else if("mod_notice_write".equals(formNm)){
         int N_NO = Integer.parseInt(request.getParameter("N_NO"));
         int REPLY = Integer.parseInt(request.getParameter("REPLY"));
         int RE_NO = Integer.parseInt(request.getParameter("RE_NO"));
    %>
        <div class="container">
        <div class="row">
            <form method="post" action="mod_notice_write?N_NO=<%=N_NO %>" onsubmit="return check()">
                <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                    <thead>
                    <tr>
                        <th colspan="3" style="background-color:#eeeeee; text-align:center;">공지사항 수정</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        NoticeDAO noticeDAO = new NoticeDAO();
                        NoticeDTO notice_read = noticeDAO.notice_read(N_NO, REPLY, RE_NO);
                    %>
                    <tr>
                         <td style="width:150px; ">제목</td><td><input  class="form-control" style="text-align: left;" placeholder= "<%= notice_read.getN_TITLE() %>" name="N_TITLE" id = "N_TITLE"></td> 
                    </tr>
                    <tr>
                         <td style="width:150px; ">내용</td><td><textarea class="form-control"  style="min-height: 400px; text-align: left;" placeholder="<%= notice_read.getN_CONTENT()%>" name="N_CONTENT" id = "N_CONTENT"></textarea></td>
                    </tr>
                </tbody>
                </table>
             <input type="submit" class="btn btn-primary pull-right" style="position:relative;" value="등록" onClick="return confirm('수정하시겠습니까?');">
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; right:10px;">목록</a>
            </form>
        </div>
    </div>
    <%
     } else if("notice_read".equals(formNm)){
    %>
        <div class="container">
        <div class="row">
            <form method="post" action="notice_writeController">
                <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                    <thead>
                    <tr>
                        <th colspan="3" style="background-color:#eeeeee; text-align:center;">공지사항</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int N_NO = Integer.parseInt(request.getParameter("N_NO"));
                    System.out.println("N_NO = " + N_NO);
	                    int REPLY = Integer.parseInt(request.getParameter("REPLY"));
                        System.out.println("REPLY = " + REPLY);
	                    int RE_NO = Integer.parseInt(request.getParameter("RE_NO"));
                        System.out.println("RE_NO = " + RE_NO);
                    
                        NoticeDAO noticeDAO = new NoticeDAO();
                        NoticeDTO notice_read = noticeDAO.notice_read(N_NO, REPLY, RE_NO);
                    %>
                    <tr>
                         <td style="width:150px; ">제목</td><td colspan="2"  style="text-align: left;"><%= notice_read.getN_TITLE() %></td> 
                    </tr>
                    <tr>
                          <td style="width:150px; ">작성자</td><td colspan="2"  style="text-align: left;"><%= notice_read.getNAME() + "(" + notice_read.getID() + ")"%> </td> 
                    </tr>
                    <tr>
                          <td style="width:150px; ">작성일</td><td colspan="2"  style="text-align: left;"><%= notice_read.getN_DATE().substring(0,4) + "년 "  + notice_read.getN_DATE().substring(5,7) + "월 " + notice_read.getN_DATE().substring(8,10) + "일" %></td> 
                    </tr>
                    <tr>
                         <td style="width:150px; ">내용</td><td colspan= "2" style="min-height: 400px; text-align: left;"><%= notice_read.getN_CONTENT()%> </td>
                    </tr>
                </tbody>
                </table>
                <%
                int NNO = notice_read.getNO();
                if("01".equals(LEVEL) ||("02".equals(LEVEL) && NO == NNO)){
                %>
             <a onClick="return confirm('정말로 삭제하시겠습니까?');" href="delete_notice_writeController?N_NO=<%=N_NO %>" class="btn btn-primary" style="position:relative; left:1115px;">삭제</a>
             <a href="notice_write.jsp?N_NO=<%=N_NO %>&REPLY=<%=REPLY%>&RE_NO=<%=RE_NO %>&formNm=mod_notice_write" class="btn btn-primary pull-right" style="position:relative; right:65px;">수정</a>
             <a href="notice_write.jsp?N_NO=<%=N_NO %>&REPLY=<%= REPLY%>&RE_NO=<%=RE_NO %>&formNm=reply_write" class="btn btn-primary pull-right" style="position:relative; right:65px;">댓글 작성</a>
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; right:75px;">목록</a>
             <%
                } else {
             %>
             <a href="notice.jsp" class="btn btn-primary pull-right">목록</a>
             <%
                }
             %>
            </form>
        </div>
    </div>
    <%
     }
    %>
        <script src="js/bootstrap.js"></script>
</body>
</html>