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
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function change(){
    var getPassword3 = $("#afterPw").val();
    
      if(getPassword3.length < 6){
          alert("[비밀번호] 비밀번호는 최소 6자리입니다.");
          $("#afterPw").val("");
          $("#afterPw").focus();
          return false;
      }
}

function check(){
  var getCheck= RegExp(/^[a-zA-Z0-9]{4,20}$/);
  var getName= RegExp(/^[가-힣]{2,}$/);
  var getPassword1 = $("#password1").val();
  var getPassword2 = $("#password2").val();
  var getHpno = $("#hpno1").val() + $("#hpno2").val() + $("#hpno2").val();
  var getSsn = $("#ssn1").val() + $("#ssn2").val();
  var getCheckHpno1 = RegExp(/^010$/);
  var getCheckHpno2 = RegExp(/^([0-9]{4})$/);
  var getCheckHpno3 = RegExp(/^([0-9]{4})$/);
  var fmt = RegExp(/^\d{6}[1234]\d{6}$/); 
  var buf = new Array(13); 
   var getMail = RegExp(/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);
   
  //아이디 유효성검사
  if(!getCheck.test($("#id").val())){
    alert("[아이디] 4~20 사이의 영어 대소문자, 숫자만 입력가능합니다.");
    $("#id").val("");
    $("#id").focus();
    return false;
  }
  
  //비밀번호 유효성 검사
  if(getPassword1.length < 6){
      alert("[비밀번호] 비밀번호는 최소 6자리입니다.");
      $("#password1").val("");
      $("#password1").focus();
      return false;
  }

  //비밀번호 재확인
  if(getPassword1 != getPassword2){
      alert("[비밀번호 재획인] 처음 입력된 비밀번호와 다릅니다.");
      $("#password2").val("");
      $("#password2").focus();
      return false;
  }
  
  //이름 유효성 검사
  if($("#name").val().length == 0){
      alert("[이름] 이름은 필수 입력 사항입니다.");
      $("#name").focus();
      return false;
      
  } else if(!getName.test($("#name").val())){
      alert("[이름] 2글자 이상의 한글만 입력가능합니다.");
      $("#name").val("");
      $("#name").focus();
      return false;
  }
  
  if(getSsn.length == 0){
      alert("[주민번호] 주민번호는 필수 입력 사항입니다.");
      $("#ssn1").focus();
      return false;
  } else if(check_jumin() == false){
      return false;
  }
  
  if(getHpno.length != 0){
      if(!getCheckHpno1.test($("#hpno1").val())){
          alert("[전화번호]010만 사용가능합니다.");
          $("#hpno1").val("");
          $("#hpno1").focus();
          return false;
      }

      //전화번호2 유효성 검사
      if(!getCheckHpno2.test($("#hpno2").val())){
          alert("[전화번호]4자리의 숫자만 입력해주세요.");
          $("#hpno2").val("");
          $("#hpno2").focus();
          return false;
     }

      //전화번호3 유효성 검사
      if(!getCheckHpno3.test($("#hpno3").val())){
          alert("[전화번호]4자리의 숫자만 입력해주세요.");
          $("#hpno3").val("");
          $("#hpno3").focus();
          return false;
      }
  }
  
  if($("#email").val().length == 0){
      alert("[이메일] 이메일은 필수 입력 사항입니다.");
      $("#email").focus();
      return false;
  } else {
     if(!getMail.test($("#email").val())){
         alert("[이메일] 올바른 이메일형식이 아닙니다.")
         $("#email").val("");
         $("#email").focus();
         return false;
     }
  }
      return true;
}

function check_jumin(){
    var jumin = $("#ssn1").val() + $("#ssn2").val();
    var fmt = RegExp(/^\d{6}[1234]\d{6}$/)  //포멧 설정
    var buf = new Array(13);

    //주민번호 유효성 검사
    if (!fmt.test(jumin)) {
      alert("[주민번호] 올바른 주민등록번호 형식이 아닙니다.");
      $("#ssn1").val("");
      $("#ssn1").focus();
      return false;
    }

    //주민번호 존재 검사
    for (var i = 0; i < buf.length; i++){
      buf[i] = parseInt(jumin.charAt(i));
    }

    var multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];// 밑에 더해주는 12자리 숫자들 
    var sum = 0;

    for (var i = 0; i < 12; i++){
    sum += (buf[i] *= multipliers[i]);
  }

  if ((11 - (sum % 11)) % 10 != buf[12]) {
    alert("[주민번호] 잘못된 주민등록번호 입니다.");
    $("#id_num").focus();
    return false;
  }
}


window.onload = function(){
    document.getElementById("addr").onclick = function(){
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address;
                var extraAddr = '';
                
                //주소타입 선택에 따른 분기
                if(data.userSelectedType === 'R'){
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }
                
                //기본 주소가 도로명 타입일때 조합한다.
                if(data.addressType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr +=data.bname;
                    }
                    //건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    //조합형주소의 유무에 따라 양쪽에 괄효를 추가하여 최종 주소를 만든다.
                    addr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                    
                    document.getElementById('addr').value = addr;
                }
                
                //우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('addr').value = addr;
                
                document.getElementById('addr2').focus();
            }
        }).open();
    }
}

function logincheck(){
      if($("#loginId").val().length == 0){
        alert("[아이디] 아이디를 입력하세요.");
        $("#id").focus();
        return false;
      }
      
      if($("#loginPW").val().length == 0){
          alert("[비밀번호] 비밀번호를 입력하세요.");
          $("#loginPW").focus();
          return false;
        }
}
</script>
</head>
<body>
    <%
    String formNm = request.getParameter("formNm");
    
    session = request.getSession();
    
    String ID = (String)session.getAttribute("ID");
    
    if( (String)request.getAttribute("ID") != null){
        ID =  (String)request.getAttribute("ID");
    }
    
    if(ID != null){
    %>
        <nav class="navbar navbar-default">
            <div class="navbar-header">
                <a class="navbar-brand" href="main.jsp">IT_SOUP</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="notice.jsp">공지사항</a></li>
                    <li ><a href="#">주소록</a></li>
                </ul>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=session.getAttribute("naming") %> <span class="caret"></span></a>
                            <%
                            String LEVEL = (String)session.getAttribute("LEVEL");

                            if("01".equals(LEVEL) || "02".equals(LEVEL)){
                            %>
                            <ul class="dropdown-menu">
                                <li><a href="logoutController">로그아웃</a></li>
                                <li><a href="#">내정보관리</a></li>
                                <li> <a href="main.jsp?naming=<%= request.getAttribute("naming") %>?formNm=join" style="position:relative;">회원가입</a></li>
                            </ul>
                            <%
                            } else {
                            %>
                            <ul class="dropdown-menu">
                                <li><a href="logoutController">로그아웃</a></li>
                                <li><a href="#">내정보관리</a></li>
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
        if("lostPw".equals(formNm)){
        %>
        <script>
          alert("임시비밀번호 발급 후 첫 로그인입니다. 비밀번호를 변경해주세요.")
        </script>
        <div class="container">
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="changePwController"  onsubmit="return change()">
                        <h3 style="text-align:center;">비밀번호 변경</h3>
                        <div class ="form-group">
                          <input type ="password" class="form-control" placeholder="변경 후 비밀번호" id="afterPw" name ="afterPw" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <div class ="form-group">
                          <input type ="password" class="form-control" placeholder="변경 후 비밀번호 재확인" id="confirmPw" name ="confirmPw" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="비밀번호 변경" style="width:300px;  position:relative; right:35px;">
                    </form>
                </div> 
            </div> 
        </div>
        <%
        } else if("chgPwYn".equals(formNm)){
        %>
        <script>
           alert("회원가입 후 첫 로그인입니다. 비밀번호를 변경해주세요.")
        </script>
        <div class="container">
            <div class="col-lg-4"></div>
                <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="changePwController"  onsubmit="return change()">
                        <h3 style="text-align:center;">비밀번호 변경</h3>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="변경 후 비밀번호" id="afterPw" name ="afterPw" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="변경 후 비밀번호 재확인" id="confirmPw" name ="confirmPw" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                            <input type="submit" class="btn btn-primary form-control" value="비밀번호 변경" style="width:300px;  position:relative; right:35px;">
                    </form>
                </div> 
            </div> 
        </div>
        <%
        } else if("join".equals(formNm)){
            String naming = (String)session.getAttribute("naming");
            
            System.out.println("naming2 = " + naming);
            
        %>
        <div class="container">
            <div class ="jumbotron" style="padding-top:20px; background-color:#eee; width:60%; position:relative; left:20%;">
                <form method = "post" action="joinController" onsubmit="return check()"style=" width:100%; ">
                    <h3 style="text-align:center;">회원가입</h3>
                    <div class ="form-group" style="right-padding:20%;">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px;">*아이디</h5>
                        <input type ="text"  placeholder="필수입력항목" name ="ID" id="id" maxlength='20' style="ime-mode:disabled; width:400px; position:relative; left:5%;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; ">*비밀번호</h5>
                        <input type ="password"   placeholder="필수입력항목" name ="PASSWORD" id="password1" maxlength='20' style="ime-mode:disabled; width:400px; position:relative; left:5%;">
                    </div>
                        <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; ">*비밀번호 재확인</h5>
                        <input type ="password"   placeholder="필수입력항목" name ="PASSWORD2" id="password2" maxlength='20' style="ime-mode:disabled; width:400px; position:relative; left:5%;">
                    </div>
                        <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; ">*이름</h5>
                        <input type ="text"   placeholder="필수입력항목" name ="NAME"  id="name" maxlength='20' style="ime-mode:active; width:400px; position:relative; left:5%;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; ">*주민번호</h5>
                        <input type ="text"  placeholder="필수입력항목"  name ="SSN1" id="ssn1" maxlength='6' style="width:195px; position:relative; left:5%;">
                        <input type ="password" placeholder="필수입력항목"   name ="SSN2" id="ssn2" maxlength='7' style="width:200px; position:relative; left:5%;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; "> 전화번호</h5>
                        <input type ="text"  name ="HPNO1"  id="hpno1" maxlength='3'  style="width:124px; text-align:center; position:relative; left:5%;">
                        <input type ="text"   name ="HPNO2"  id="hpno2" maxlength='4' style="width:133px; text-align:center; position:relative; left:5%;">
                        <input type ="text"  name ="HPNO3"  id="hpno3" maxlength='4' style="width:133px; text-align:center; position:relative; left:5%;">
                    </div>
                        <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px; ">*이메일</h5>
                        <input type ="text"  placeholder="필수입력항목"   name ="EMAIL" id="email" maxlength='50' style="ime-mode:inactive; width:400px; position:relative; left:5%;">
                    </div>
                        <div class ="form-group">
                        <h5 style="width:110px; float:left; position:relative; top:-4px; left:15px;"> 주소</h5>
                        <input type ="text" placeholder="클릭시 주소찾기 팝업을 호출합니다."   name ="ADDR1"  id="addr" maxlength='20' style="ime-mode:active; width:400px; position:relative; left:5%;">
                        <input type ="text"   name ="ADDR2"  id="addr2" maxlength='20' style="ime-mode:active; width:400px; position:relative; left:5%;">
                    </div>
                    <input type="submit" class="btn btn-primary form-control" value="회원가입" style="width:540px;">
                </form>
            <div>
            <a href="main.jsp?formNm=main" style="position:relative; bottom:-15px; left:250px;">홈으로</a>
        </div>
        <%
        }
    } else {
        %>
        <nav class="navbar navbar-default">
            <div class="navbar-header">
                <a class="navbar-brand" href="main.jsp">IT_SOUP</a>
            </div>
        </nav>
        <%
        if("login".equals(formNm)){
        %>
        <div class="container">
        <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="loginController" onsubmit="return logincheck()">
                        <h3 style="text-align:center;">로그인</h3>
                        <div class ="form-group">
                            <input type ="text" class="form-control" placeholder="아이디"  id="loginId"name ="ID" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="비밀번호" id="loginPW" name ="PASSWORD" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="로그인" style="width:300px;  position:relative; right:35px;">
                        <div>
                            <a href="main.jsp?formNm=findID" style="position:relative; bottom:-15px; left:-30px;">아이디 찾기</a>
                            <a href="main.jsp?formNm=findPW" style="position:relative; 100px; bottom:-15px; left:100px;">비밀번호 찾기</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%
        } else if("findID".equals(formNm)){
        %>
        <div class="container">
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="findController" >
                        <h3 style="text-align:center;">아이디 찾기</h3>
                        <div class ="form-group">
                            <input type ="text" class="form-control" placeholder="이름"  id="findNM" name ="NAME" maxlength='20'>
                        </div>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="주민번호" id="findSSN"  name ="SSN" maxlength='20'>
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="아이디 찾기">
                        <div>
                            <a href="main.jsp?formNm=main" style="position:relative; bottom:-15px; left:90px;">홈으로</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%
        } else if("findPW".equals(formNm)){
        %>
        <div class="container">
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="findPwController" >
                        <h3 style="text-align:center;">비밀번호 찾기</h3>
                        <div class ="form-group">
                            <input type ="text" class="form-control" placeholder="아이디"  id="findID" name ="ID" maxlength='20'>
                        </div>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="이메일" id="findEmail"  name ="EMAIL" maxlength='20'>
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="임시비밀번호 이메일 발송">
                            <a href="main.jsp?formNm=main" style="position:relative; bottom:-15px; left:90px;">홈으로</a>
                    </form>
                </div>
            </div>
        </div>
        <%
        } else {
        %> 
        <div class="container">
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <div class ="jumbotron" style="padding-top:20px;">
                    <form method = "post" action="loginController" onsubmit="return logincheck()">
                        <h3 style="text-align:center;">로그인</h3>
                        <div class ="form-group">
                            <input type ="text" class="form-control" placeholder="아이디"  id="loginId"name ="ID" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <div class ="form-group">
                            <input type ="password" class="form-control" placeholder="비밀번호" id="loginPW" name ="PASSWORD" maxlength='20' style="width:300px; position:relative; right:35px;">
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="로그인" style="width:300px;  position:relative; right:35px;">
                        <div>
                            <a href="main.jsp?formNm=findID" style="position:relative; bottom:-15px; left:-30px;">아이디 찾기</a>
                            <a href="main.jsp?formNm=findPW" style="position:relative; 100px; bottom:-15px; left:100px;">비밀번호 찾기</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%
        }
    }
    %>
    <script src="js/bootstrap.js"></script>
</body>
</html>