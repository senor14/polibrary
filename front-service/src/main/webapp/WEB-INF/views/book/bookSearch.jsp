<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
        <!-- ======= Contact Section ======= -->
        <section id="contact" class="contact" style="z-index: 1; width: 600px">
            <div class="container">
                <div class="row" data-aos="fade-in">
                    <div class="col-lg-12 mt-5 mt-lg-0 d-flex align-items-stretch">
                        <div class="php-email-form">
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label for="keyword">검색</label>
                                    <input type="text" name="keyword" class="form-control" id="keyword" required placeholder="책 이름 또는 저자를 검색해보세요"/>
                                </div>
                            </div>
                            <div class="text-center">
                                <button class="btn btn-primary" onclick="getBookInfo()">검색</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- ======= Contact Section ======= -->
        <section id="result" class="contact" style="z-index: 100;overflow-y: auto;padding: 0px;margin: 45px;">
            <div class="container">
                <div class="row" data-aos="fade-in">
                    <div class="col-lg-12 d-flex align-items-stretch">
                        <div class="info" id="searchResult" style="width: 800px">

                            <%--                        <div class="address font-weight-bold">--%>
                            <%--                            <i class="bi bi-geo-alt"></i>--%>
                            <%--                            <h4>Location:</h4>--%>
                            <%--                            <h5>A108 Adam Street, New York, NY 535022</h5>--%>
                            <%--                        </div>--%>

                        </div>

                    </div>
                </div>
            </div>
        </section>
    </section>
</main>

<!-- 책 내용 상세 모달 모달 -->
<div class="modal fade" id="books-modal" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" style="z-index:2000;">
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
<script type="text/javascript">
    function getBookInfo(){
        let keyword = document.getElementById("keyword").value;
        let html = "";

        // 프리로더 reloading_st();
        $.ajax({
            url : "http://poly-library.com/book-service/books/list?keyword="+keyword,
            type: "get",
            success : function (data){
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    html += '<div class="address font-weight-bold row justify-content-between mb-4" bookId="'+data[i].bookId+'" onclick="fnOpenBooks(this)">'+
                        '<div class="col-2"><img class="bi bi-geo-alt book__thumb" src="'+ data[i].thumbnail +'" style="width: 100%; border: 5px groove beige;"></div>'+
                        ' <div class="col-10"><h4 style="padding:0px;">' +data[i].title +'</h4>'+
                        ' <h6> ' +data[i].author + '</h6></div>' +
                        '</div>'+'<hr>';

                }

                $("#searchResult").html(html);
            }
        })
    }

    document.querySelector('#keyword').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            // 프리로더 reloading_st();
            let keyword = document.getElementById("keyword").value;
            let html = "";

            // 프리로더 reloading_st();
            $.ajax({
                url : "http://poly-library.com/book-service/books/list?keyword="+keyword,
                type: "get",
                success : function (data){
                    console.log(data);
                    for (let i = 0; i < data.length; i++) {
                        html += '<div class="address font-weight-bold row justify-content-between mb-4" bookId="'+data[i].bookId+'" onclick="fnOpenBooks(this)">'+
                            '<div class="col-2"><img class="bi bi-geo-alt" src="'+ data[i].thumbnail +'" style="width: 100%; border: 5px groove beige;"></div>'+
                            ' <div class="col-9"><h4 style="padding:0px;">' +data[i].title +'</h4>'+
                            ' <h6> ' +data[i].author + '</h6></div>' +
                            '</div>'+'<hr>'
                    }
                    $("#searchResult").html(html);
                }
            })
        }
    });
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
                "department": "",
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
</body>
</html>
