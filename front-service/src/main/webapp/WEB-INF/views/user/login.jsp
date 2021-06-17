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
        <h1 class="section-title shadowyl center">POLIBRARY</h1>
        <div class="row" data-aos="fade-in">
            <section class="portfolio section-bg rounded-3 shadow">
                <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center"><div class="m-lg-5"><span class="black center title">Login</span></div></div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
                    <form action="http://poly-library.com/user-service/login" method="post" role="form" onsubmit="return false;">
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="email" id="email" placeholder="학교 이메일" onkeyup="emailChk()"/>
                            <div id="emchk"></div>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" id="password" placeholder="비밀번호"/>
                        </div>
                        <div class="text-center mt-2">
                            <br/>
                            <button type="submit" class="btn btnpoly" onclick="loginRequest()">로그인</button>
                        </div>
                    </form>
                </div>
                <div class="text-end">
                    <a style="font-weight: bold" href="/user/signup">회원가입</a>
                    <br/>
                    <a style="font-weight: bold" href="/user/finduser">ID/비밀번호 찾기</a>
                </div>
            </section>
        </div>
    </div>
</section>
<%@include file="../include/js.jsp" %>
<script type="text/javascript">
    $(document).ready (function() {
        const user_no = sessionStorage.getItem("userUuid");
        if (user_no != null) {
            Swal.fire({
                title: '이미 로그인 상태입니다.',
                icon: 'warning',
                showConfirmButton: false,
                timer: 2500
            }).then(val => {
                if (val) {
                    location.href = "javascript:history.back();";
                }
            });
        }
    })
</script>
<script type="text/javascript">
    // 로그인 유효성 체크
    function loginChk() {

        //이메일 형식 저장
        var emailJ = /^[0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

        if (emailJ.test($(email).val()) == false) {
            Swal.fire('POLIBRARY', '이메일을 형식에 맞게 입력해 주세요!', 'warning');
            $(email).focus();
            return false;
        }

        if (($("#password").val() == false)) {
            Swal.fire('POLIBRARY', '비밀번호를 입력해 주세요!', 'warning');
            $("#password").focus();
            return false;
        }
    }

    function loginRequest(){
        loginChk();

        let email = document.getElementById("email").value;
        let password = document.getElementById("password").value;

        $.ajax({
            url: "http://poly-library.com/user-service/login",
            type: "post",
            data : JSON.stringify({
                "email": email,
                "password": password
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                console.log(data.userUuid);
                sessionStorage.setItem("userUuid", data.userUuid);
                sessionStorage.setItem("email", data.email)
                sessionStorage.setItem("name", data.name);
                sessionStorage.setItem("nickname", data.nickname);
                sessionStorage.setItem("department", data.department);
                sessionStorage.setItem("studentId", data.studentId);

                if (data.userUuid === "edc0475e-3685-43ca-97c6-3b75aeae9c7f"){
                    location.href = "/admin/adminhome";
                }
                location.href = "/main"
            }, error: function (error){
                Swal.fire("로그인에 실패했습니다.", '', 'error');
            }
        })
    }
</script>

</body>
</html>
