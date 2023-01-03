<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script language="javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
    <script>
    function checks(){
      var hobbyCheck = false;
      var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
      var getCheck= RegExp(/^[a-zA-Z0-9]{4,12}$/);
      var getName= RegExp(/^[가-힣]+$/);
      var fmt = RegExp(/^\d{6}[1234]\d{6}$/); //형식 설정
      var buf = new Array(13); //주민등록번호 배열

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
/*
      //이름 유효성 검사
      if(!getCheck.test($("#name").val())){
        alert("이름형식에 맞게 입력해주세요")
        $("#name").val("");
        $("#name").focus();
        return false;
      }
   */        

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
        return true;
    }

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
    </script>
</head>
<body>
<form action="joinController" name="data" method="post" onsubmit="return checks()"> 
        <!--밑에 submit 누르면 메일로 보내짐-->
    <table border="1" bordercolor="grey" align="center">
        <tbody>
        <tr>
            <td colspan="2" bgcolor="lightblue" align="center">회원 기본 정보</td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">아이디</td>
            <td><input size="15" type="text" id="id"> 4~12자의 영문 대소문자와 숫자로만 입력</td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">비밀번호</td>
            <td><input size="15" type="password" id="password"> 4~12자의 영문 대소문자와 숫자로만 입력</td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">비밀번호 확인</td>
            <td><input size="15" type="password" id="password_check"></td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">메일 주소</td>
            <td><input type="text" id="mail"> 예) id@domain.com</td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">이름</td>
            <td><input type="text" id="name"></td>
        </tr>
        <tr>
            <td colspan="2" bgcolor="lightblue" align="center"> 개인 신상 정보</td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">주민등록번호</td>
            <td>
                <input size="7" type="text" id="id_num">
                - <input size="7" type="password" id="id_num_back">
                예)123456-1234567
            </td>
        </tr>
        <tr>
            <td align="center" bgcolor="lightgrey"><b>생일</b></td>
             <td><input type="text" AUTOCOMPLETE="off" name ="year" id="year" size="4" readonly  disabled><b> 년</b>
                 <input type="text" AUTOCOMPLETE="off" name ="month" id="month" style="width: 20px;" readonly  disabled><b> 월</b>    
                 <input type="text" AUTOCOMPLETE="off" name ="date" id="date" style="width: 20px;"" readonly  disabled><b> 일</b>
               </td>    
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">관심분야</td>
            <td>
                <input type="checkbox" name="hobby[]" value="computer">컴퓨터
                <input type="checkbox" name="hobby[]" value="internet">인터넷
                <input type="checkbox" name="hobby[]" value="travel">여행
                <input type="checkbox" name="hobby[]" value="movie">영화감상
                <input type="checkbox" name="hobby[]" value="music">음악감상
            </td>
        </tr>
        <tr>
            <td bgcolor="lightgrey" align="center">자기소개</td>
            <td><textarea cols="50" style="margin: 0px; height: 300px; width:480px" id="intro"></textarea>
                <!--크기 조정 가능-->
            </td>
        </tr>
    </tbody>
    </table>
        <p align="center">
            <input type="submit" value="회원 가입"> 
            <!--누르면 메일이 보내짐-->
            <input type="reset" value="다시 입력"> 
            <!--누르면 초기화-->
        </p>
</form>
</body>