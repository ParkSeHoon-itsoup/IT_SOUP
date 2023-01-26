<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
<script>
window.onpageshow = function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
       location.href = "#";
            }
    }
</script>
</head>
<body>
    <script>
        location.href='login.jsp';
    </script>
</body>
</html>