<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="DAO.NoticeDAO" %>
<%@ page import="DAO.FileDAO" %>
<%@ page import="DTO.NoticeDTO" %>
<%@ page import="DTO.FileDTO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<title>IT_SOUP</title> 
<style type="text/css">
.insert {
    padding: 20px 30px;
    display: block;
    width: 1000px;
    margin: 5vh auto;
    height: 90vh;
    border: 1px solid #dbdbdb;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.insert .file-list {
    height: 200px;
    overflow: auto;
    border: 1px solid #989898;
    padding: 10px;
}
.insert .file-list .filebox p {
    font-size: 14px;
    margin-top: 10px;
    display: inline-block;
}
.insert .file-list .filebox .delete i{
    color: #ff5353;
    margin-left: 5px;
}
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

function checkSize(input){
        if(input.files && input.files[0].size > (5*1024*1024)){
            alert("파일 사이즈가 5MB를 초과하였습니다.");
            $(input).val("");
            return false;
    }
}

$(document).ready(function(){
    $("button[name='delButton0']").on("click", function(e){
        alert(document.getElementById("#delete_btn0"));
        $("#delete_btn0").remove();
        $("#update_btn0").remove();
        $("#list0").remove();
        addRow();
    });
})

$(document).ready(function(){
    $("button[name='delButton1']").on("click", function(e){
        alert(document.getElementById("#delete_btn1"));
        $("#delete_btn1").remove();
        $("#update_btn1").remove();
        $("#list1").remove();
        addRow();
    });
})

$(document).ready(function(){
    $("button[name='delButton2']").on("click", function(e){
        alert(document.getElementById("#delete_btn2"));
        $("#delete_btn2").remove();
        $("#update_btn2").remove();
        $("#list2").remove();
        addRow();
    });
})

function addRow(){
    var dynamic_table = document.getElementById('dynamic_table');
    var newRow = dynamic_table.insertRow();
    var cell = newRow.insertCell();

    cell.innerHTML = '<input type="file" name="fileupload" size="70" onchange="checkSize(this)">';
}

var fileNo = 0;
var filesArr = new Array();

/* 첨부파일 추가 */
function addFile(obj){
    var maxFileCnt = 5;   // 첨부파일 최대 개수
    var attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
    var remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
    var curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    }

    for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {

        const file = obj.files[i];

        // 첨부파일 검증
        if (validation(file)) {
            // 파일 배열에 담기
            var reader = new FileReader();
            reader.onload = function () {
                filesArr.push(file);
            };
            reader.readAsDataURL(file)

            // 목록 추가
            let htmlData = '';
            htmlData += '<div id="file' + fileNo + '" class="filebox">';
            htmlData += '   <p class="name">' + file.name + '</p>';
            htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ');"><i class="far fa-minus-square"></i></a>';
            htmlData += '</div>';
            $('.file-list').append(htmlData);
            fileNo++;
        } else {
            continue;
        }
    }
    // 초기화
    document.querySelector("input[type=file]").value = "";
}

/* 첨부파일 검증 */
function validation(obj){
    const fileTypes = ['application/pdf', 'image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif', 'application/haansofthwp', 'application/x-hwp'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

/* 첨부파일 삭제 */
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

/* 폼 전송 */
function submitForm() {
    // 폼데이터 담기
    var form = document.querySelector("form");
    var formData = new FormData(form);
    for (var i = 0; i < filesArr.length; i++) {
        // 삭제되지 않은 파일만 폼데이터에 담기
        if (!filesArr[i].is_delete) {
            formData.append("attach_file", filesArr[i]);
        }
    }

    $.ajax({
        method: 'POST',
        url: '/register',
        dataType: 'json',
        data: formData,
        async: true,
        timeout: 30000,
        cache: false,
        headers: {'cache-control': 'no-cache', 'pragma': 'no-cache'},
        success: function () {
            alert("파일업로드 성공");
        },
        error: function (xhr, desc, err) {
            alert('에러가 발생 하였습니다.');
            return;
        }
    })
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
            <form method="post" action="notice_writeController" encType="multipart/form-data" onsubmit="return check()">
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
                 <input type="submit" class="btn btn-primary" style="position:relative; left:1050px;" value="등록">
                 <a href="notice.jsp" class="btn btn-primary" style="position:relative; left:1050px;">목록</a>
                <input type="file" name="fi" multiple />
                <div class="file-list"></div>
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
    FileDAO fileDAO = new FileDAO();
    ArrayList<FileDTO> attachFile = fileDAO.getList(N_NO);
    
    int size = attachFile.size();
    %>
        <div class="container">
        <div class="row">
            <form method="post" action="mod_notice_write?N_NO=<%=N_NO %>" encType="multipart/form-data"  onsubmit="return check()">
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
                         <td style="width:150px; ">제목</td><td><input class="form-control" style="text-align: left;" name="N_TITLE" id = "N_TITLE" value=<%= notice_read.getN_TITLE() %>></td> 
                    </tr>
                    <tr>
                         <td style="width:150px; ">내용</td><td><textarea class="form-control"  style="min-height: 250px; text-align: left;" name="N_CONTENT" id = "N_CONTENT"><%= notice_read.getN_CONTENT()%></textarea></td>
                    </tr>
                </tbody>
                </table>
             <%
             int NNO = notice_read.getNO();
             if(NO == NNO){
             %> 
             <input type="submit" class="btn btn-primary pull-right" style="position:relative; top:-5px; right:70px;" value="등록" onClick="return confirm('수정하시겠습니까?');">
             <%
             }
             %>
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; left:50px; top:-5px;">목록</a>
             
<!--                  <div class = "form-group">파일 -->
             <%
             if(size == 3){
             %>
                <div>
                    <table style=" width:35%;">
                        <thead>
                            <tr>
                                <th>첨부파일</th>
                            </tr>
                        </thead>
                        <%
                        for(int i=0; i<attachFile.size(); i++){
                        %>
                        <tbody>
                            <tr>
                               <td id="list<%=i %>" style="width:70%;"><a href="downloadController?fileName=<%= attachFile.get(i).getF_REALNAME() %>"><%= attachFile.get(i).getF_NAME() %></a>
                               <td id="delete_btn<%=i %>" style="position:relative; right:15px;"><button type="button" name="delButton<%=i %>" class="form-control-file border">삭제</button>
                               <input type = "hidden" name="F_NO" value="<%=attachFile.get(i).getF_NO() %>">
                               <script>
                                   var name = <%=attachFile.get(i).getF_NO() %>;
                                   alert("name = " + name);
                               </script>
                            </tr>
                        </tbody>
                         <%
                         }
                         %>
                            <tr>
                               <td style="width:30%;"><table id="dynamic_table" border="1"></table>
                            </tr>
                    </table>
                    <h5><font color="red">업로드할 파일은 최대 5MB까지 업로드 가능</font></h5>
                </div>
             <%
             } else if(size == 2){
             %>
                <div>
                    <table style=" width:35%;">
                        <thead>
                            <tr>
                                <th>첨부파일</th>
                            </tr>
                        </thead>
                        <%
                        for(int i=0; i<attachFile.size(); i++){
                        %>
                        <tbody>
                            <tr>
                               <td id="list<%=i %>" style="width:70%;"><a href="downloadController?fileName=<%= attachFile.get(i).getF_REALNAME() %>"><%= attachFile.get(i).getF_REALNAME() %></a>
                               <td id="delete_btn<%=i %>" style="position:relative; right:15px;"><button type="button" name="delButton<%=i %>" class="form-control-file border">삭제</button>
                               <input type = "hidden" name="F_NO" value="<%=attachFile.get(i).getF_NO() %>">
                               <script>
                                   var name = <%=attachFile.get(i).getF_NO() %>;
                                   alert("name = " + name);
                               </script>
                            </tr>
                        </tbody>
                         <%
                         }
                         %>
                            <tr>
                               <td style="width:30%;"><table id="dynamic_table" border="1"></table>
                            </tr>
                    </table>
                     <div class = "form-group">
                        <input type="file" name="fname1" id="fname1" class="form-control-file border" onchange="checkSize(this)"/>
                        <h5><font color="red">업로드할 파일은 최대 5MB까지 업로드 가능</font></h5>
                     </div>
                </div>
            <%
             } else if(size == 1){
            %>
                <div>
                    <table style=" width:35%;">
                        <thead>
                            <tr>
                                <th>첨부파일</th>
                            </tr>
                        </thead>
                        <%
                        for(int i=0; i<attachFile.size(); i++){
                        %>
                        <tbody>
                            <tr>
                               <td id="list<%=i %>" style="width:70%;"><a href="downloadController?fileName=<%= attachFile.get(i).getF_REALNAME() %>"><%= attachFile.get(i).getF_REALNAME() %></a>
                               <td id="delete_btn<%=i %>" style="position:relative; right:15px;"><button type="button" name="delButton<%=i %>" class="form-control-file border">삭제</button>
                               <input type = "hidden" name="F_NO" value="<%=attachFile.get(i).getF_NO() %>">
                               <script>
                                   var name = <%=attachFile.get(i).getF_NO() %>;
                                   alert("name = " + name);
                               </script>
                            </tr>
                        </tbody>
                         <%
                         }
                         %>
                            <tr>
                               <td style="width:30%;"><table id="dynamic_table" border="1"></table>
                            </tr>
                    </table>
                 <div class = "form-group">
                    <input type="file" name="fname1" id="fname1" class="form-control-file border" onchange="checkSize(this)"/>
                    <input type="file" name="fname2" id="fname2" class="form-control-file border" onchange="checkSize(this)"/>
                    <h5><font color="red">업로드할 파일은 최대 5MB까지 업로드 가능</font></h5>
                 </div>
                </div>
            <%
             } else {
            %>
                <div>
                    <table style=" width:35%;">
                        <thead>
                            <tr>
                                <th>첨부파일</th>
                            </tr>
                        </thead>
                        <%
                        for(int i=0; i<attachFile.size(); i++){
                        %>
                        <tbody>
                            <tr>
                               <td id="list<%=i %>" style="width:70%;"><a href="downloadController?fileName=<%= attachFile.get(i).getF_REALNAME() %>"><%= attachFile.get(i).getF_REALNAME() %></a>
                               <td id="delete_btn<%=i %>" style="position:relative; right:15px;"><button type="button" name="delButton<%=i %>" class="form-control-file border">삭제</button>
                            </tr>
                        </tbody>
                         <%
                         }
                         %>
                            <tr>
                               <td style="width:30%;"><table id="dynamic_table" border="1"></table>
                            </tr>
                    </table>
                 <div class = "form-group">
                    <input type="file" name="fname1" id="fname1" class="form-control-file border" onchange="checkSize(this)"/>
                    <input type="file" name="fname2" id="fname2" class="form-control-file border" onchange="checkSize(this)"/>
                    <input type="file" name="fname3" id="fname3" class="form-control-file border" onchange="checkSize(this)"/>
                    <h5><font color="red">업로드할 파일은 최대 5MB까지 업로드 가능</font></h5>
                 </div>
                </div>
             <%
             }
             %>
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
                        System.out.println("NO = " + NO);
                    
                        NoticeDAO noticeDAO = new NoticeDAO();
                        NoticeDTO notice_read = noticeDAO.notice_read(N_NO, REPLY, RE_NO);
                        
                        FileDAO fileDAO = new FileDAO();
                        ArrayList<FileDTO> getList = fileDAO.getList(N_NO);
                        
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
             <a onClick="return confirm('정말로 삭제하시겠습니까?');" href="delete_notice_writeController?N_NO=<%=N_NO %>" class="btn btn-primary" style="position:relative; left:1120px;px;px; top: -10px;">삭제</a>
             <a href="notice_write.jsp?N_NO=<%=N_NO %>&REPLY=<%=REPLY%>&RE_NO=<%=RE_NO %>&formNm=mod_notice_write" class="btn btn-primary pull-right" style="position:relative; right:65px; top:-10px;">수정</a>
             <a href="notice.jsp" class="btn btn-primary pull-right" style="position:relative; right:80px; top:-10px;">목록</a>
             <%
                } else {
             %>
             <a href="notice.jsp" class="btn btn-primary pull-right">목록</a>
             <%
                }
             %>
                <div >
                    <table style=" width:30%; position:relative;">
                        <thead>
                            <tr>
                                <th>첨부파일</th>
                            </tr>
                        </thead>
                        <%
                        for(int i=0; i<getList.size(); i++){
                        %>
                        <tbody>
                            <tr>
                               <td style="width:30%;"><a href="downloadController?fileName=<%= getList.get(i).getF_REALNAME() %>"><%= getList.get(i).getF_REALNAME() %></a>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <%
                }
                %>
            </form>
                   <form method="post" action="reply_writeController?N_NO=<%= N_NO %>&REPLY=<%=REPLY %>&RE_NO=<%=RE_NO %>&NO=<%=NO %>" onsubmit="return check()" style="width:1180px; left:0px; bottom:200px">
                    <table class="table table-striped" style="text-align:center; border:1px solid #dddddd;">
                        <tbody>
                            <tr>
                                 <td><input type="text" class="form-control" placeholder="댓글 제목" name="R_TITLE" maxlength="50"></td> 
                            </tr>
                            <tr>
                                 <td><textarea class="form-control" placeholder="댓글 내용" name="R_CONTENT" maxlength="2048" style="height:300;"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                 <input type="submit" class="btn btn-primary" style="position:relative; left:1080px; top:-10px" value="댓글 등록">
                 </form>
        </div>
    </div>
    <%
     }
    %>
        <script src="js/bootstrap.js"></script>
</body>
</html>