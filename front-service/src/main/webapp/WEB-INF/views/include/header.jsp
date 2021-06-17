<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--<%@include file="../include/css.jsp" %>--%>
<!-- jquery (cdn 사용) -->
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<header id="header">
    <div class="d-flex flex-column">
        <div class="profile">
            <h1 class="text-light"><a href="index.html">POLIBRARY</a></h1>
            <div class="social-links mt-3 text-center">
                <h2 id="user_name"></h2>
            </div>
        </div>
        <nav id="navbar" class="nav-menu navbar">
            <ul>
                <li>
                    <a href="/main" class="nav-link scrollto"><i class="bx bx-home"></i> <span>메인</span></a>
                </li>
                <li>
                    <a href="/book/bookSearch" class="nav-link scrollto"><i class="bx bx-book-content"></i><span>책 검색</span></a>
                </li>
<%--                <li>--%>
<%--                    <a href="/chat/room" class="nav-link scrollto"><i class="bx bx-server"></i><span>채팅</span></a>--%>
<%--                </li>--%>
                <li>
                    <a href="/board/myList" class="nav-link scrollto"><i class="bx bx-envelope"></i><span>내 신청</span></a>
                </li>
                <li>
                    <a href="/board/myDelivery" class="nav-link scrollto"><i class="bx bx-box"></i><span>내 배송</span></a>
                </li>
                <li>
                    <a href="/rental/rentalList" class="nav-link scrollto"><i class="bx bx-user"></i><span>내 대여목록</span></a>
                </li>
                <li>
                    <a href="/board/boardList" class="nav-link scrollto"><i class="bx bx-happy"></i><span class="black">대여희망 게시판</span></a>
                </li>
                <li class="userInfo">
                    <a href="/user/chginf" class="nav-link scrollto userInfo"><i class="bx bx-file-blank userInfo" style="color: cornflowerblue"></i><span class="black userInfo">내 정보수정</span></a>
                </li>
                <li class="logout">
                    <a href="javascript:logout()" onclick="logout()" class="nav-link scrollto logout"><i class="bi bi-x-lg" style="color: firebrick"></i><span class="black logout">로그아웃</span></a>
                </li>
            </ul>
        </nav>
        <!-- .nav-menu -->
    </div>
</header>

<style>
    .black:hover {
        color: white;
    }
    .logout {
        color: firebrick;!important;
    }
    .userInfo {
        color: cornflowerblue;!important;
    }
</style>
<%--<%@include file="../include/js.jsp" %>--%>
<!-- 로그인 안하고 다른 화면갈 때 -->
<script>
    $(document).ready (function() {
        const user_no = sessionStorage.getItem("userUuid");
        if (user_no === undefined || user_no === null || user_no === '') {
            Swal.fire({
                title: '로그인을 해주세요.',
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


<script>
    let userUuid = sessionStorage.getItem("userUuid");
    let name = sessionStorage.getItem("name");
    let nickname = sessionStorage.getItem("nickname");
    // let email = sessionStorage.getItem("email");
    let studentId = sessionStorage.getItem("studentId");
    let department = sessionStorage.getItem("department");

    document.getElementById("user_name").innerText = sessionStorage.getItem('nickname')+" 님";
    console.log("user_name: {}", sessionStorage.getItem('nickname')+" 님")

</script>
<!-- 로그아웃 요청시 -->
<script>

    function logout() {
        $.ajax({
            url: "http://poly-library.com/user-service/logout",
            type: "get",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                sessionStorage.clear();
                console.log(data);
                Swal.fire("로그아웃 되었습니다.", '', 'success');
                location.href = "/login"
            }, error: function(error){
                sessionStorage.clear();
                console.log(error);
                Swal.fire("로그아웃 되었습니다!", '', 'success');
                location.href = "/login"
            },
        })
    }
</script>
