<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <title>Polibrary</title>
    <meta content="" name="description" />
    <meta content="" name="keywords" />
    <%@include file="../include/css.jsp" %>
</head>
<body>
<section id="hero" class="d-flex flex-column justify-content-center align-items-center">
    <div class="hero-container" data-aos="fade-in">
        <h1 class="section-title shadowyl center">Polibrary</h1>
        <div class="row" data-aos="fade-in">
            <section class="portfolio section-bg rounded-3 shadow">
                <div class="col-lg-12 d-flex align-items-stretch justify-content-center"><span class="black title center">Signup</span></div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center mt-5">
                    <form role="form" onsubmit="return false;">

                        <!-- 학교 이메일 입력 -->
                        <div class="form-group mt-3 mb-3">
                            <input type="email" class="form-control" name="email" id="email" placeholder="학교 이메일" />
                        </div>

                        <!-- 학번 입력 -->
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="studentId" id="studentId" numberOnly="true" placeholder="학번" onfocusout="StudentIdCheck()" />
                        </div>

                        <!-- 이름 입력 -->
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="name" id="name" placeholder="이름"/>
                        </div>


                        <!-- 비밀번호 입력 -->
                        <div class="form-group mb-3">
                            <input type="password" class="form-control" name="pwd" id="pwd" placeholder="비밀번호"/>
                        </div>

                        <!-- 비밀번호 확인 name값 확인 -->
                        <div class="form-group mb-3">
                            <input type="password" class="form-control" name="pwd2" id="pwd2" placeholder="비밀번호 확인"/>
                        </div>

                        <!-- 닉네임 -->
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="nickname" id="nickname"placeholder="닉네임" onfocusout="NicknameCheck()"/>
                        </div>

                        <!-- 전공 -->
                        <div class="form-group mb-3">
                            <select id="department" name="department">
                                <option value="" selected disabled>=전공 선택=</option>
                                <option value="데이터분석과">데이터분석과</option>
                                <option value="디지털콘텐츠과">디지털콘텐츠과</option>
                                <option value="의료정보과">의료정보과</option>
                                <option value="주얼리디자인과">주얼리디자인과</option>
                                <option value="i-패션디자인과">i-패션디자인과</option>
                                <option value="패션산업과">패션산업과</option>
                                <option value="전문기술과정">전문기술과정</option>
                                <option value="하이테크과정">하이테크과정</option>
                                <option value="신중년특화과정">신중년특화과정</option>
                                <option value="여성재취업과정">여성재취업과정</option>
                            </select>
                        </div>

                        <!-- 회원가입 버튼 -->
                        <div class="text-center mt-2">
                            <br/>
                            <button type="button" class="btn btnpoly" onclick="signupRequest()">회원가입</button>
                        </div>
                    </form>
                </div>
                <!-- a태그 회원일 경우 로그인 링크 -->
                <div class="text-end">
                    <a style="font-weight: bold" href="/login">로그인</a>
                </div>
            </section>
        </div>
    </div>
</section>
<%@include file="../include/js.jsp" %>
<script type="text/javascript">

    var pwJ = /^[a-zA-Z0-9]{4,20}$/;

    var pwd1 = document.getElementById("password");
    var pwd2 = document.getElementById("password2");

    // 비밀번호 변경시 멘트
    var pwchk = document.getElementById("pwchk");
    var pw2chk = document.getElementById("pw2chk");

    var cnt = 0;

    // 비밀번호 확인
    $(pwd).on("keyup", function() {
        if ((pwJ.test($(pwd).val()) == false)) {
            $(pwchk).text('비밀번호를 4~20자 사이로 입력해 주세요');
            $(pwchk).css('color','red');
            return false;
        } else if (!(pwJ.test($(pwd).val()) == false)){
            $(pw2chk).hide();
        }
    })

    $(pwd2).on("keyup", function() {
        if (!($(pwd).val() == $(pwd2).val())) {
            $(pw2chk).text('비밀번호가 일치하지 않습니다.');
            $(pw2chk).css('color','red');
            return false;
        } else {
            $(pw2chk).hide();
        }
    })


    function signupValid() {
        //이메일 정규식
        let emailJ = /^[0-9_\.\-]+@[A-Za-z\-]+\.[A-Za-z\-]+/;
        let kopo = /.kopo.ac.kr$/

        if (kopo.test($("#email").val()) == false) {
            Swal.fire("kopo.ac.kr 메일만 사용 가능합니다.")
            return false;
        }

        if (emailJ.test($("#email").val()) == false) {
            Swal.fire("이메일 형식을 지켜주세요.\n(example123@office.kopo.ac.kr)", '', 'error');
            return false;
        }

        if ($("#email").val().substring(0,10) !== $("#studentId").val()) {
            Swal.fire("학번과 이메일 앞자리는 같아야합니다.)", '', 'error');
            return false;
        }

        if (($("#password").val() != $("#password2").val())) {
            Swal.fire("비밀번호가 서로 다릅니다.", '', 'error');
            return false;
        }

        let email = document.getElementById("email").value;
        //ajax 호출
        $.ajax({
            //function을 실행할 url
            url : "http://poly-library.com/user-service/users/email/"+email+"/check",
            // url : "http://localhost:8000/user-service/users/email/"+email+"/check",
            type : "get",
            success : function(data) {
                // Swal.fire(data.msg, '', 'success');
            }
        })
        return true;

    }

    function signupRequest(){

        if(!signupValid()){
            return false;
        }

        let email = document.getElementById("email").value;
        let studentId = document.getElementById("studentId").value;
        let name = document.getElementById("name").value;
        let pwd = document.getElementById("pwd").value;
        let nickname = document.getElementById("nickname").value;
        let department = $("#department option:selected").val();

        console.log(typeof(email));
        console.log(typeof(studentId));
        console.log(typeof(name));
        console.log(typeof(pwd));
        console.log(typeof(nickname));
        console.log(typeof(department));

        sessionStorage.setItem("email", email);
        sessionStorage.setItem("studentId", studentId);
        sessionStorage.setItem("name", name);
        sessionStorage.setItem("pwd", pwd);
        sessionStorage.setItem("nickname", nickname);
        sessionStorage.setItem("department", department);

        $.ajax({
            url: "http://poly-library.com/user-service/users/authByEmail",
            // url: "http://localhost:8000/user-service/users/authByEmail",
            type: "post",
            data : JSON.stringify({
                "email": email,
                "studentId": studentId,
                "name" : name,
                "pwd" : pwd,
                "nickname": nickname,
                "department": department
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                sessionStorage.setItem("auth", data.auth);
                Swal.fire({
                    title: email+' 로 인증번호를 발송했습니다',
                    icon: 'success',
                    showConfirmButton: true,
                    timer: 5000
                }).then((value) => {
                    location.href = "/user/signupcheck"
                })
            }, error: function(error){
                console.log(error);
                Swal.fire("다시 확인해주세요.", '', 'error');
            },
        })
    }


    function StudentIdCheck() {
        let studentId = document.getElementById("studentId").value;
            //ajax 호출
            $.ajax({
                //function을 실행할 url
                url : "http://poly-library.com/user-service/users/studentId/"+studentId+"/check",
                // url : "http://localhost:8000/user-service/users/studentId/"+studentId+"/check",
                type : "get",
                success : function(data) {
                    Swal.fire(data.msg, '', 'success');
                }
            })
    }

    function NicknameCheck() {
        let nickname = document.getElementById("nickname").value;
        //ajax 호출
        $.ajax({
            //function을 실행할 url
            url : "http://poly-library.com/user-service/users/nickname/"+nickname+"/check",
            // url : "http://localhost:8000/user-service/users/nickname/"+nickname+"/check",
            type : "get",
            success : function(data) {
                Swal.fire(data.msg, '', 'success');
            }
        })
    }

</script>
</body>
</html>
