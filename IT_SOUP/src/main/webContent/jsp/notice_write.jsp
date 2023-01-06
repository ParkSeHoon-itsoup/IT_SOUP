<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body> 
    <%
        String formNm = request.getParameter("formNm");
            
        session = request.getSession();
        
        String ID = (String)session.getAttribute("ID");
        String naming = request.getParameter("naming");
        
        if( (String)request.getAttribute("ID") != null){
            ID =  (String)request.getAttribute("ID");
        }
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
                        <ul class="dropdown-menu">
                            <li><a href="logoutController">로그아웃</a></li>
                            <li><a href="">내정보관리</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
     </nav>
     <div>
     </div>
     
    <div class="container">
        <div class="row">
            <form method="post" action="notice_writeController">
                <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                    <thead>
                        <tr>
                            <th colspan="2" style="background-color:#eeeeee; text-align:center;">공지사항 작성</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" class="form-control" placeholder="글 제목" name="N_NAME" maxlength="50"></td>
                        </tr>
                        <tr>
                            <td><textarea class="form-control" placeholder="글 내용" name="N_CONTENT" maxlength="2048" style="height:350px;"></textarea></td>
                        </tr>
                    </tbody>
                </table>
            <a href="notice.jsp"  class="btn btn-primary pull-right">등록</a>
            </form>
        </div>
</div>
        <script src="js/bootstrap.js"></script>
</body>
</html>