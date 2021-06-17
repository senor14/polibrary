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
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<!-- ======= Header ======= -->
<%@include file="../include/header.jsp" %>

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
                            <input type="email" class="form-control" name="email" id="email" placeholder="학교 이메일" value=""  readonly/>
                        </div>

                        <!-- 학번 입력 -->
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="studentId" id="studentId" numberOnly="true" placeholder="학번" value="" readonly/>
                        </div>

                        <!-- 이름 입력 -->
                        <div class="form-group mb-3">
                            <input type="text" class="form-control" name="name" id="name" placeholder="이름" value="" readonly/>
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
                            <input type="text" class="form-control" name="nickname" id="nickname" placeholder="닉네임" value=""/>
                        </div>

                        <!-- 회원가입 버튼 -->
                        <div class="text-center mt-2">
                            <br/>
                            <button type="button" class="btn btnpoly" onclick="chgMyInfo()">정보수정</button>
                        </div>
                    </form>
                </div>

            </section>
        </div>
    </div>
</section>
<%@include file="../include/js.jsp" %>
<script type="text/javascript">
    // 변경할 비밀번호 확인
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

    function chgMyInfo(){
        if (($("#pwd").val() == "")) {
            Swal.fire('Polibrary','비밀번호를 입력해 주세요.','warning');
            return false;
        } else if (($("#pwd2").val() == "")) {
            Swal.fire('Polibrary','비밀번호 확인을 입력해 주세요.','warning');
            return false;
        } else if (($("#nickname").val() == "")) {
            Swal.fire('Polibrary','닉네임을 입력해 주세요.','warning');
            return false;
        }

        let pwd = document.getElementById("pwd").value;
        let nickname = document.getElementById("nickname").value;

        $.ajax({
            url: "http://poly-library.com/user-service/users/"+sessionStorage.getItem("userUuid"),
            type: "put",
            data : JSON.stringify({
                "pwd" : pwd,
                "nickname": nickname
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                Swal.fire("회원정보 변경에 성공하였습니다.", '', 'success');
                sessionStorage.setItem("nickname", nickname);
                location.href = "/user/chginf"
            }, error: function(error){
                console.log(error);
                Swal.fire("다시 확인해주세요.", '', 'error');
            },
        })
    }



</script>
<script>
    document.getElementById('email').value = sessionStorage.getItem("email");
    document.getElementById('studentId').value = sessionStorage.getItem("studentId");
    document.getElementById('nickname').value = sessionStorage.getItem("nickname");
    document.getElementById('name').value = sessionStorage.getItem("name");

</script>
</body>
</html>
