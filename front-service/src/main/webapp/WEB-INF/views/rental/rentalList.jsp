<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Polibrary</title>
    <%@include file="../include/css.jsp" %>
    <style>
    .hidden{
    	display:none;
    }
    </style>
</head>
<body>
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<!-- ======= Header ======= -->
<%@include file="../include/header.jsp"%>
<main id="main">
	<section id="hero" class="d-flex flex-column justify-content-center align-items-center">
		<div class="hero-container" data-aos="fade-in" style="margin-left: 100px">
			<div class="container justify-content-center">
				<div class="row" id="books">


      			</div>
			</div>
		</div>
	</section>
</main>

<!-- 책 내용 상세 모달 모달 -->
<div class="modal fade" id="books-modal" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" style="z-index:2000;">
   	<div class="modal-dialog modal-lg">
		<div class="modal-content">
       		<div class="mb-3" style="width:100%">

       		</div>
       		<div class="modal-body p-4 row mb-3">
         		<div class="col-6">
					<img class=""  id="img" src="" style="width:100%">
				</div>
				<div class="col-6" id="book_info">

				</div>
			</div>
    	</div>
   	</div>
</div>

<!-- End Header -->
<%@include file="../include/js.jsp" %>
<script type="text/javascript">

	
window.addEventListener('load', function () {
  	$.ajax({
        url: "http://poly-library.com/post-service/posts/"+userUuid+"/status/2",
        type: "get",
        contentType: "json",
        success: function (data) {

        	console.log(data);
        	let resHTML = "";
        	$.each(data, function(index, item){
            	let postId = item.postId;
            	let bookId =item.bookId;
            	let author = item.author;
      			let thumbnail = item.thumbnail;


      			resHTML += "<div class='col-sm-3'>";
      			resHTML += "<div class='image-wrap-2'>";
      			resHTML += "<div style='background-color: green; color: white; text-align: center;'>대여완료</div>";
      			resHTML += "<div class='image-info'>";
      			resHTML += "<img src='"+thumbnail+"' alt='Image' class='img-fluid' width='370'  onclick='fnOpenBooks(this)'  bookId='"+bookId+"'>";
      			resHTML += "</div></div></div>";
            });
			
        	$("#books").append(resHTML);
        }
    })
}) 
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

			let resHTML = "";
			resHTML += "<div class='mb-3'><p class='mb-1'>도서명 : </p>"+data.title+"</div>";
			resHTML += "<div class='mb-3'><p class='mb-1'>저자 : </p>"+data.author+"</div>";

			$('#book_info').html(resHTML);
		}
	})

	$('#books-modal').modal('show');
	
}
</script> 
    
</body>
</html>
