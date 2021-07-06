<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>

    <title>Polibrary</title>
    <meta content="" name="description"/>
    <meta content="" name="keywords"/>
    <%@include file="../include/css.jsp" %>
</head>
<body>
<section id="hero" class="d-flex flex-column justify-content-center align-items-center">
    <div class="hero-container" data-aos="fade-in">
        <h1 class="section-title shadowyl center">Polibrary</h1>
        <div class="row" data-aos="fade-in">
            <section class="portfolio section-bg rounded-3 shadow">

                <div class="form-group mb-3 center">
                    <label for="search_1" class="font">이메일 찾기</label>
                    <input type="radio" id="search_1" name="search_total" onclick="finduser(1)" checked="checked"/>
                    &nbsp;
                    <label for="search_2" class="font">비밀번호 찾기</label>
                    <input type="radio" id="search_2" name="search_total" onclick="finduser(2)"/>
                </div>

                <!-- id 찾기 -->
                <div id="searchId">
                <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center">
                    <span class="black center title">Find ID</span>
                </div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
<%--                    <form action="http://3.34.217.193:8000/user-service/users/findEmailByStId" method="post" role="form">--%>
                    <form action="http://poly-library.com/userservice/users/findEmailByStId" method="post" role="form" onsubmit="return false;">

                        <!-- id 찾기를 위한 학번 입력란 -->
                            <div class="form-group mb-3 center">
                                <label for="studentId">학번을 입력해 주세요</label>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" name="studentId" id="studentId"
                                       placeholder="학번"/>
                            </div>

                            <!-- 제출(id 찾기) -->
                            <div class="text-center mt-2">
                                <br/>
                                <button type="submit" class="btn btnpoly" onclick="findId()">ID 찾기</button>
                            </div>

                    </form>

                </div>
                </div>

                <!-- 비밀번호 찾기 -->
                <div id="searchPw">
                    <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center">
                        <span class="black center title">Find PW</span>
                    </div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
<%--                    <form action="http://3.34.217.193:8000/user-service/users/authByEmail" method="get" role="form">--%>
<%--                    <form action="http://localhost:8000/user-service/users/authByEmail" method="get" role="form" onsubmit="return false;">--%>
                    <form action="http://poly-library.com/users/authByEmail" method="get" role="form" onsubmit="return false;">
                        <!-- 비밀번호 찾기를 위한 학번 입력란 -->

                            <div class="form-group mb-3 center">
                                <label for="email">학교 이메일을 입력해 주세요</label>
                                <div id="emchk"></div>
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" name="email" id="email"
                                       placeholder="이메일" onkeyup="emailChk()"/>
                            </div>

                            <!-- 제출(비밀번호 찾기) -->
                            <div class="text-center mt-2">
                                <br/>
                                <button type="submit" class="btn btnpoly"  onclick="pwCheck()">비밀번호 찾기</button>
                            </div>

                    </form>
                </div>
                </div>

                <div class="text-end">
                    <a style="font-weight: bold" href="javascript:history.back()">뒤로가기</a>
                </div>
            </section>
        </div>
    </div>
</section>
<%@include file="../include/js.jsp" %>
<script type="text/javascript">

    $(document).ready(function() {
        finduser(1);
    })

    // id 찾기/ 비밀번호 찾기 여부 선택(radio)에 따라 해당 찾기 form 태그를 보임
    function finduser(num) {
        console.log("받아온 searchType : " + num);

        if (num == 1) {
            // type 1 (id찾기)
            $("#searchId").show();
            $("#searchPw").hide();
        } else if (num == 2) {
            // type 2 (비밀번호 찾기)
            $("#searchId").hide();
            $("#searchPw").show();
        }
    }
</script>
<script type="text/javascript">
    function findId() {
        if (($("#studentId").val() == "")) {
            Swal.fire('Polibrary','학번을 입력해 주세요.','warning');
            return false;
        }
        let studentId = document.getElementById('studentId').value;

        $.ajax({
            url: "http://poly-library.com/user-service/users/findEmailByStId",
            // url: "http://localhost:8000/user-service/users/findEmailByStId",
            type: "post",
            data : JSON.stringify({
                "studentId": studentId
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data)

                Swal.fire({
                    title: '찾으시는 이메일 주소는 '+data.email,
                    icon: 'success',
                    showConfirmButton: true,
                    timer: 5000
                }).then((value) => {

                    location.href = "/login"
                })
                // location.href = "/login"
            }, error: function (error){
                Swal.fire("이메일 찾기에 실패했습니다.", '', 'error');
            }
        })
    }
    function pwCheck() {
        if (($("#email").val() == "")) {
            Swal.fire('Polibrary','이메일을 입력해 주세요.','warning');
            return false;
        }
        let email = document.getElementById('email').value;
        sessionStorage.setItem("email", email);
        $.ajax({
            url: "http://poly-library.com/user-service/users/findEmailByStId",
            // url: "http://localhost:8000/user-service/users/authByEmail",
            type: "post",
            data : JSON.stringify({
                "email": email
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                sessionStorage.setItem("auth", data.auth)
                Swal.fire({
                    title: email+' 로 인증번호를 발송했습니다',
                    icon: 'success',
                    showConfirmButton: true,
                    timer: 5000
                }).then((value) => {

                    location.href = data.url
                })
                // location.href = "/login"
            }, error: function (error){
                Swal.fire("이메일 찾기에 실패했습니다.", '', 'error');
            }
        })
    }
</script>
</body>
</html>
