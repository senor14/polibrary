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
                <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center"><div class="m-lg-5"><span class="black title center">Certification</span></div></div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
<%--                    <form action="http://3.35.142.240:8000/user-service/users/pwCheck" method="post" role="form" onsubmit="certChk()">--%>
                    <form action="http://poly-library.com/user-service/users/pwCheck" method="post" role="form" onsubmit="return false;">

                        <!-- 인증번호 입력란 -->
                        <div class="form-group mb-3 center">
                            <label for="clientAuth">이메일로 전송된 인증번호를 입력해 주세요</label>
                        </div>
                            <div class="form-group mb-3">
                            <input type="text" class="form-control" name="clientAuth" id="clientAuth" placeholder="인증번호"/>
                        </div>

                        <div class="text-center mt-2">
                            <br/>
                            <button type="submit" class="btn btnpoly" onclick="certChk()">인증번호 전송</button>
                        </div>
                    </form>
                </div>
                <div class="text-end">
                    <a style="font-weight: bold" href="/user/signup">뒤로가기</a>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript">
    function certChk() {
        if (($("#clientAuth").val() == "")) {
            Swal.fire('Polibrary','인증번호를 입력해 주세요.','warning');
            return false;
        }
        let clientAuth = document.getElementById('clientAuth').value;
        let auth = sessionStorage.getItem("auth");
        let email = sessionStorage.getItem("email");
        let studentId = sessionStorage.getItem("studentId");
        let name = sessionStorage.getItem("name");
        let pwd = sessionStorage.getItem("pwd");
        let nickname = sessionStorage.getItem("nickname");
        let department = sessionStorage.getItem("department");

        $.ajax({
            url: "http://poly-library.com/user-service/users/pwCheck",
            type: "post",
            data : JSON.stringify({
                "clientAuth": clientAuth,
                "auth": auth
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data)
                $.ajax({
                    url: "http://poly-library.com/user-service/users",
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
                        sessionStorage.clear();
                        console.log(data);
                        Swal.fire("회원가입에 성공하였습니다.", '', 'success');
                        location.href = "/login"
                    }, error: function(error){
                        console.log(error);
                        Swal.fire("다시 확인해주세요.", '', 'error');
                    },
                })
            }, error: function (error){
                Swal.fire("이메일 찾기에 실패했습니다.", '', 'error');
            }
        })


    }
</script>
<%@include file="../include/js.jsp" %>
</body>
</html>
