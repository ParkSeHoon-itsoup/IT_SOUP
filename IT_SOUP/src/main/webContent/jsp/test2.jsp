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
<!--     <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script> -->
    <script src="https://code.jquery.com/jquery-3.1.1.min.js">
    <script>
    function checks(){
      var hobbyCheck = false;
      var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
      var getCheck= RegExp(/^[a-zA-Z0-9]{4,12}$/);
      var getName= RegExp(/^[가-힣]+$/);
      var fmt = RegExp(/^\d{6}[1234]\d{6}$/); //형식 설정
      var buf = new Array(13); //주민등록번호 배열
     // var getCheckHpno = RegExp(^(\(?\+?[0-9]*\)?)?[0-9_\- \(\)]*$);

      //아이디 공백 확인
      if($("#id").val() == ""){
        alert("아이디 입력바람");
        $("#id").focus();
        return false;
      }
           
      //아이디 유효성검사
      if(!getCheck.test($("#id").val())){
        alert("형식에 맞게 입력해주세요");
        $("#id").val("");
        $("#id").focus();
        return false;
      }

      //비밀번호 공백 확인
      if($("#password").val() == ""){
        alert("패스워드 입력바람");
        $("#password").focus();
        return false;
      }
/*
      //아이디 비밀번호 같음 확인
      if($("#id").val() == $("#password").val()){
        alert("아이디와 비밀번호가 같습니다");
        $("#password").val("");
        $("#password").focus();
        return false;
      }
      
      //비밀번호 유효성검사
      if(!getCheck.test($("#password").val())){
        alert("형식에 맞게 입력해주세요");
        $("#password").val("");
        $("#password").focus();
        return false;
      }
           
      //비밀번호 확인란 공백 확인
      if($("#password_check").val() == ""){
        alert("패스워드 확인란을 입력해주세요");
        $("#password_check").focus();
        return false;
      }
           
      //비밀번호 서로확인
      if($("#password").val() != $("#password_check").val()){
          alert("비밀번호가 상이합니다");
          $("#password").val("");
          $("#password_check").val("");
          $("#password").focus();
          return false;
      }
          
      //이메일 공백 확인
      if($("#mail").val() == ""){
        alert("이메일을 입력해주세요");
        $("#mail").focus();
        return false;
      }
           
      //이메일 유효성 검사
      if(!getMail.test($("#mail").val())){
        alert("이메일형식에 맞게 입력해주세요")
        $("#mail").val("");
        $("#mail").focus();
        return false;
      }
           
      //이름 공백 검사
      if($("#name").val() == ""){
        alert("이름을 입력해주세요");
        $("#name").focus();
        return false;
      }

      //이름 유효성 검사
      if(!getCheck.test($("#name").val())){
        alert("이름형식에 맞게 입력해주세요")
        $("#name").val("");
        $("#name").focus();
        return false;
      }
           

      if(($("#id_num").val() == "") || ($("#id_num_back").val() == "")){
        alert("주민등록번호를 입력해주세요");
        $("#id_num").focus();
        return false;
      }

      if(check_jumin() ==false){
        return false;
      }

      //취미 유효성 검사
      for(var i=0;i<$('[name="hobby[]"]').length;i++){
        if($('input:checkbox[name="hobby[]"]').eq(i).is(":checked") == true) {
          hobbyCheck = true;
          break;
            }
          }
              
      if(!hobbyCheck){
        alert("하나이상 관심분야를 체크해 주세요");
        return false;
      }

      //자기소개란 공백 검사
      if($("#intro").val() == ""){
        alert("자기소개를 입력해주세요")
        $("#intro").val("");
        $("#intro").focus();
        return false;
        }
      */
        return true;
    }
/*
    function check_jumin(){
      var jumins3 = $("#id_num").val() + $("#id_num_back").val();
      //주민등록번호 생년월일 전달
          
      var fmt = RegExp(/^\d{6}[1234]\d{6}$/)  //포멧 설정
      var buf = new Array(13);

      //주민번호 유효성 검사
      if (!fmt.test(jumins3)) {
        alert("주민등록번호 형식에 맞게 입력해주세요");
        $("#id_num").focus();
        return false;
      }

      //주민번호 존재 검사
      for (var i = 0; i < buf.length; i++){
        buf[i] = parseInt(jumins3.charAt(i));
      }

      var multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];// 밑에 더해주는 12자리 숫자들 
      var sum = 0;

      for (var i = 0; i < 12; i++){
      sum += (buf[i] *= multipliers[i]);// 배열끼리12번 돌면서 
    }

    if ((11 - (sum % 11)) % 10 != buf[12]) {
      alert("잘못된 주민등록번호 입니다.");
      $("#id_num").focus();
      return false;
    }

    var birthYear = (jumins3.charAt(6) <= "2") ? "19" : "20";
    birthYear += $("#id_num").val().substr(0, 2);
    var birthMonth = $("#id_num").val().substr(2, 2);
    var birthDate = $("#id_num").val().substr(4, 2);
    var birth = new Date(birthYear, birthMonth, birthDate);
                              
             
    $("#year").val(birthYear);
    $("#month").val(birthMonth);
    $("#date").val(birthDate);
  }
  */
    </script>
</head>
<body> 
        <div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class ="jumbotron" style="padding-top:20px;">
                <form method = "post" action="joinController" onsubmit="return checks()">
                    <h3 style="text-align:center;">회원가입</h3>
                    <div class ="form-group">
                       <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px;">아이디</h5>
                       <input type ="text" placeholder="필수입력사항" name ="ID" id="id" maxlength='20' style="width:150px;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">비밀번호</h5>
                        <input type ="password" placeholder="필수입력사항" name ="PASSWORD" id="password" maxlength='20' style="width:150px;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">이름</h5>
                        <input type ="text" placeholder="홍길동" name ="NAME" maxlength='20' style="width:150px;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">주민번호</h5>
                        <input type ="password" placeholder="111111" name ="SSN" maxlength='6' style="width:65px;">
                        <input type ="password" placeholder="1111111" name ="SSN" maxlength='7' style="width:75px;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">전화번호</h5>
                        <input type ="text" placeholder="010" name ="HPNO1"  id="hpno1" maxlength='3'  style="width:35px; text-align:center;">
                        <input type ="text"  placeholder="1111" name ="HPNO2"  id="hpno2" maxlength='4' style="width:40px; text-align:center;" >
                        <input type ="text" placeholder="1111" name ="HPNO3"  id="hpno3" maxlength='4' style="width:40px; text-align:center;" >
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">주소</h5>
                        <input type ="text"  placeholder="주소" name ="ADDR" maxlength='20' style="width:150px;">
                    </div>
                    <div class ="form-group">
                        <h5 style="width:60px; float:left; position:relative; top:-4px; left:-5px; ">이메일</h5>
                        <input type ="text"  placeholder="이메일" name ="EMAIL" maxlength='50' style="width:150px;">
                    </div>
                    <input type="submit" class="btn btn-primary form-control" value="회원가입">
                </form>
            </div> 
        </div> 
        </div>
        <div class="col-lg-4"></div>
    <script src="js/bootstrap.js"></script>
</body>
</html>