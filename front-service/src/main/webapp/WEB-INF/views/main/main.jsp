<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Polibrary</title>
    <%@include file="../include/css.jsp" %>
    <style>
        .second__row {
            margin-top: 40px;
        }
    </style>
</head>
<body>
<main id="main">
    <!-- ======= Mobile nav toggle button ======= -->
    <i class="bi bi-list mobile-nav-toggle d-xl-none"></i>

    <!-- ======= Header ======= -->
    <%@include file="../include/header.jsp" %>

    <section id="hero" class="d-flex flex-column justify-content-center align-items-center">
        <div class="hero-container" data-aos="fade-in" style="width: 80%">
            <h1 class="section-title shadowyl">Polibrary</h1>
            <div class="container justify-content-center">
                <div class="row">
                    <div class="col-sm-6 services__item">
                        <h3 id="nick_name" style="font-weight:bold"></h3>
                        <h4 style="font-weight:bold">총 대여권수 : <p style="display:contents;"id="bringBook"></p></h4>
                    </div>
                    <div class="col-sm-6 services__item">
                        <h3 class="bold">신간</h3>
                        <div class="row" id="newBookList">
                        </div>
                    </div>
                </div>
                <div class="row second__row" style="margin-top: 40px">
                    <div class="col-8 services__item">
                        <h3 class="bold">현재 대여중인 책</h3>
                        <div class="row" id="borrowBook">
                        </div>
                    </div>
                    <div class="col-4 services__item">
                        <h3 class="bold">포인트</h3>
                        <div class="row" id="point">
                            <h1 id="point__content" onload="pointLoad();"></h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- 책 내용 상세 모달 모달 -->
<div class="modal fade" id="books-modal" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" style="z-index:9999;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body p-4 row mb-3">
                <div class="col-6">
                    <img class=""  id="img" src="" style="width:100%;border: 5px groove beige;">
                </div>
                <div class="col-6" id="book_info">
                    <div class='mb-3'><p class='mb-1'>도서명 : </p><p id="title"></p></div>
                    <div class='mb-3'><p class='mb-1'>저자 : </p><p id="author"></p></div>
                    <div class=''><p class='mb-1'>출판사 : </p><p id="pubInfo"></p></div>
                    <div style="display:none;" id="bookId"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="" type="button" class="btn btn-primary" onclick="fnPostReq()">요청글 생성</button>
            </div>
        </div>
    </div>
</div>
<!-- End Header -->
<%@include file="../include/js.jsp" %>
<script>
    $( document ).ready(function() {
        init();
    });
</script>
<script type="text/javascript">

    function init() {
        console.log("test");

        // 대여중인 책 리스트 가져오기
        $.ajax({
            url: "http://poly-library.com/rental-service/"+userUuid+"/rentals",
            type: "get",
            success: function (data) {
                console.log(data);
                let resHTML ="";
                $.each(data, function(key, value) {
                    resHTML += "<div class='col-2'>";
                    resHTML += "<img style='width:100%;border: 5px groove beige;' bookId='"+value.bookId+"' src='"+value.thumbnail+"' onclick='fnOpenBooks(this)'>";
                    resHTML += "</div>";
                });
                $('#borrowBook').append(resHTML);
                // 데이터 구조 확인 후 데이터 뿌리기
                // let searchList = "";
                // for (let i = 0; i < data.length; i++) {
                //     searchList += "<li>" + data[i] + "</li>";
                // }
                // $("#borrowBook").html(searchList);
                // $("#borrowBook").css("color", "white");
                // $("#borrowBook").show();
            }
        })

        // 신간 책 확인 로직
        $.ajax({
            url: "http://poly-library.com/book-service/books/newBooks",
            type: "get",
            success: function (data) {
                console.log(data);
                let resHTML ="";
                $.each(data, function(key, value) {
                    resHTML += "<div class='col-4'>";
                    resHTML += "<img style='width:100%;border: 5px groove beige;' src='"+value.thumbnail+"'  bookId='"+value.bookId+"' onclick='fnOpenBooks(this)'>";
                    resHTML += "</div>";
                });
                $('#newBookList').append(resHTML);
                // 데이터 값 확인하고 신간 책 목록 뿌리기
                // let searchList = "";
                // for (let i = 0; i < data.length; i++) {
                //     searchList += "<li>" + data[i] + "</li>";
                // }
                // $("#borrowBook").html(searchList);
                // $("#borrowBook").css("color", "white");
                // $("#borrowBook").show();
            }
        })

        // 대여권수 확인하기
        $.ajax({
            url: "http://poly-library.com/rental-service/"+ userUuid + "/countMyRentals",
            type: "get",
            success: function (data) {
                console.log(data);
                $('#bringBook').html(data.rentalCount);
                // 데이터 값 확인하고 책권수 뿌리기
                // let searchList = "";
                // for (let i = 0; i < data.length; i++) {
                //     searchList += "<li>" + data[i] + "</li>";
                // }
                // $("#bringBook").html(searchList);
                // $("#bringBook").css("color", "white");
                // $("#bringBook").show();
            }
        })
    }


</script>
<script>
    function fnOpenBooks(book){
        console.log(book);

        let bookId = $(book).attr('bookid');
        console.log(bookId);
        $.ajax({
            url: "http://poly-library.com/book-service/books/"+bookId,
            type: "get",
            success: function (data) {
                console.log(data);
                $('#img').attr("src", data.thumbnail);
                $('#title').html(data.title);
                $('#author').html(data.author);
                $('#pubInfo').html(data.pubInfo);
                $('#bookId').html(bookId);
            }
        })

        $('#books-modal').modal('show');

    }
</script>
<!--요청 글 작성 버튼 기능 -->
<script>
    function fnPostReq(book){

        let thumbnail = $('#img').attr("src");
        let title = $('#title').html();
        let author = $('#author').html();
        let bookId = $('#bookId').html();
        bookId *= 1;
        console.log(thumbnail);
        console.log(title);
        console.log(author);
        console.log(bookId);
        $.ajax({
            url: "http://poly-library.com/post-service/posts",
            type: "post",
            data:JSON.stringify({
                "userUuid": userUuid,
                "nickname": nickname,
                "department": department,
                "bookId": bookId,
                "title": title,
                "author": author,
                "thumbnail": thumbnail
            }),
            dataType: "JSON",
            contentType: "application/json",
            success: function (data) {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: '요청 완료!',
                    showConfirmButton: false,
                    timer: 1500
                })
                $('#books-modal').modal('hide');
            }
        })
    }
</script>
<script>
    document.getElementById("nick_name").innerText = nickname + " 님";
    $(document).ready (function() {
        $.ajax({
            url: "http://poly-library.com/user-service/users/"+userUuid+"/point",
            type: "get",
            success: function (data) {
                console.log("안영이여:",data);
                sessionStorage.setItem("point", data);
                document.getElementById("point__content").innerText = data;
                // location.href = "/login"
            }, error: function (error){
            }
        })
    })

</script>
</body>
</html>