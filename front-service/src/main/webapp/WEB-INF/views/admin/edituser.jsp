<%@ page import="poly.util.CmmUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String uid = CmmUtil.nvl(request.getParameter("uid"));
%>
<html>
<head>
    <title>Polibrary</title>
    <%@include file="../include/css.jsp" %>
</head>
<body>
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<!-- ======= Header ======= -->
<%@include file="../include/header.jsp"%>

<main id="main">
    <section id="hero" class="d-flex flex-column justify-content-center align-items-center">
        <div class="hero-container" data-aos="fade-in">
            <h1 class="section-title shadowyl center">Polibrary</h1>
            <div class="row" data-aos="fade-in">
                <section class="portfolio section-bg rounded-3 shadow">
                    <div class="col-lg-12 mt-2 mt-lg-0 d-flex align-items-stretch justify-content-center"><div class="m-lg-5"><span class="black center title">Edit User</span></div></div>
                    <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch justify-content-center">
                    <form>
                        <!-- 회원 이메일 수정 -->
                            <div class="form-group mb-3">
                                <label for="email">학교 이메일</label>
                            </div>
                            <div class="form-group mb-3">
                                <input type="email" class="form-control" name="email" id="email" placeholder="학교 이메일"/>
                            </div>


                        <!-- 회원 이름 수정 -->
                            <div class="form-group mb-3">
                                <label for="name">회원 이름</label>
                            </div>
                            <div class="form-group mb-3">
                                <input type="text" class="form-control" name="name" id="name" placeholder="이름" onkeyup="emailChk()"/>
                            </div>

                        <!-- 회원 학번 -->
                            <div class="form-group mb-3">
                                <label for="studentId">학번</label>
                             </div>
                            <div class="form-group mb-3">
                                <input type="text" class="form-control" name="studentId" id="studentId" placeholder="학번"/>
                            </div>
                            <br/>

                            <div class="text-center mt-2">
                                <br/>
                                <button type="submit" class="btn btnpoly" onclick="updateUser()">수정</button>
                                <button type="button" class="btn btnpoly" onclick="deleteUser()">삭제</button>
                            </div>
                    </form>
                    </div>
                    <div class="text-end">
                        <a style="font-weight: bold" href="/admin/adminhome">회원 리스트</a>

                    </div>
                </section>
            </div>
        </div>
    </section>
</main>

<!-- End Header -->
<%@include file="../include/js.jsp" %>
<script type="text/javascript">
    $(document).ready(function() {
        $.ajax({
            url: "http://poly-library.com/user-service/users/<%=uid%>",
            type: "get",
            dataType: "json",
            success: function (json) {
                console.log(json);
                console.log("json.email;" + json.email);
                var email = json.email;
                var name = json.name;
                var studentId = json.studentId;

                $("#email").val(email);
                $("#name").val(name);
                $("#studentId").val(studentId);
            }
        })
    })

    function updateUser(){
        let email = $("#email").val();
        let name = $("#name").val();
        let studentId = $("#studentId").val();

        console.log(email);
        console.log(name);
        console.log(studentId);

        $.ajax({
            type: "put",
            url: "http://poly-library.com/user-service/users/<%=uid%>",
            dataType: "json",
            contentType: "application/json",
            data : JSON.stringify({
                "email": email,
                "name": name,
                "studentId": studentId
            }),
            // success: function (json) {
            //     console.log(json);
            //     console.log("put 응답함.")
            // }
        })
    }


    // $('input[name=email]').attr('value', email);
    // $('input[name=studentId]').attr('value',studentId);
    // $('input[name=name]').attr('value',name);

    function deleteUser() {
        Swal.fire({
            title: 'Polibrary',
            text: '정말 삭제하시겠습니까?',
            icon: 'question',
            showConfirmButton: true,
            confirmButtonText: '삭제',
        }).then(value => {
            if (value.isConfirmed) {
                $.ajax({
                    // 회원아이디 가져와야함
                    // 회원수정 로직도 필요
                    url: "http://poly-library.com/user-service/users/<%=uid%>",
                    type: "post",
                    success: function (data) {
                        console.log("삭제받아온 : " + data);
                        if (data == 1) {
                            Swal.fire({
                                title : 'Polibrary',
                                text : '삭제가 완료되었습니다.',
                                icon : 'success',
                                showConfirmButton: true,
                                confirmButtonText: '네'
                            }).then(value => {
                                if  (value.isConfirmed) {
                                    location.href = "/admin/adminhome";
                                }
                            })
                        }
                    }
                })
            }
        })
    }
</script>
<script>
    $(document).ready (function() {
        const user_no = sessionStorage.getItem("userUuid");
        if (user_no === undefined || user_no === null || user_no === '' || user_no !== 'edc0475e-3685-43ca-97c6-3b75aeae9c7f') {
            Swal.fire({
                title: '어드민으로 접속을 해주세요.',
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
</body>
</html>
