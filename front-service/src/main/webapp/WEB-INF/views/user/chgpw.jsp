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
                <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center"><div class="m-lg-5"><span class="black title center" style="font-size: 45px;">Change PW</span></div></div>
                <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
                    <form action="http://poly-library.com/user-service/users/chgPw" method="post" role="form" onsubmit="return false;">

                        <!-- 변경할 비밀번호 입력 -->
                        <div class="form-group mb-3">
                            <input type="password" class="form-control" name="password" id="password" required placeholder="변경할 비밀번호"/>
                            <div id="pwchk"></div>
                        </div>

                        <!-- 변경할 비밀번호 확인 -->
                        <div class="form-group">
                            <input type="password" class="form-control" name="password2" id="password2" required placeholder="변경할 비밀번호 확인"/>
                            <div id="pw2chk"></div>
                        </div>

                        <div class="text-center mt-2">
                            <br/>
                            <button type="submit" class="btn btnpoly" onclick="pwdChk()">비밀번호 변경</button>
                        </div>
                    </form>
                </div>
                <div class="text-end pr-1">
                    <a style="font-weight: bold" href="javascript:history.back()">뒤로가기</a>
                    <br/>
                    <a style="font-weight: bold" href="/user/finduser">ID/비밀번호 찾기</a>
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

    //pwJ.test($(pwd2).val()) == false
    // 변경할 비밀번호 확인
    $(password).on("keyup", function() {
        if ((pwJ.test($(password).val()) == false)) {
            $(pwchk).text('비밀번호를 4~20자 사이로 입력해 주세요');
            $(pwchk).css('color','red');
            return false;
        } else if (!(pwJ.test($(password).val()) == false)){
            $(pw2chk).hide();
        }
    })

    $(password2).on("keyup", function() {
        if (!($(password).val() == $(password2).val())) {
            $(pw2chk).text('비밀번호가 일치하지 않습니다.');
            $(pw2chk).css('color','red');
            return false;
        } else {
            $(pw2chk).hide();
        }
    })

    function pwdChk() {
        if (!cnt == 2) {
            Swal.fire('POLIBRARY','입력한 정보를 다시 한 번 확인해 주세요.','warning');
            return false;
        }
        let password = document.getElementById('password').value;
        let email = sessionStorage.getItem('email');
        console.log("email: {}", email);
        $.ajax({
            // url: "http://poly-library.com/user-service/users/findEmailByStId",
            url: "http://poly-library.com/user-service/users/chgPw",
            type: "put",
            data : JSON.stringify({
                "password": password,
                "email": email
            }),
            dataType: "json",
            contentType: "application/json;charset:utf-8",
            success: function (data) {
                console.log(data)

                Swal.fire({
                    title: data.msg,
                    icon: 'success',
                    showConfirmButton: true,
                    timer: 5000
                }).then((value) => {
                    sessionStorage.removeItem("email");
                    location.href = data.url;
                })
                // location.href = "/login"
            }, error: function (error){
                Swal.fire("비밀번호 변경에 실패했습니다.", '', 'error');
            }
        })


    }
</script>

</body>
</html>
