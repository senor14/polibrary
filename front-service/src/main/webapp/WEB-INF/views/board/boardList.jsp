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
	.hero {
		overflow: scroll;
	}
    </style>
</head>
<body>
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<!-- ======= Header ======= -->
<%@include file="../include/header.jsp"%>
<main id="main">
	<section id="hero" class="d-flex flex-column align-items-center" style="overflow: scroll">
		<div class="hero-container" data-aos="fade-in" style="width:100%">
			<div class="container justify-content-center" id="boardList" style="overflow:auto">
				<div class="row mb-2">
					<div class="col-1 mb text-center">
						등록번호
					</div>
					<div class="col-7  text-center">
						책
					</div>
					<div class="col-2  text-center">
						요청자
					</div>
					<div class="col-2 text-center">
						학과
					</div>
					<hr/>
				</div>

			</div>
		</div>
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
				<button id="" type="button" class="btn btn-primary" onclick="getChat()">대화하기</button>
			</div>
		</div>
	</div>
</div>

<!-- End Header -->
<%@include file="../include/js.jsp" %>
<script type="text/javascript">

	
window.addEventListener('load', function () {
  	$.ajax({
        url: "http://poly-library.com/post-service/posts",
        type: "get",
        contentType: "json",
        success: function (data) {

        	console.log(data);
        	let resHTML = "";
        	$.each(data, function(index, item){
            	let postId = item.postId;
            	let reqUuid = item.userUuid;
            	let department = item.department;
            	let nickname = item.nickname;
            	let title =item.title;
				let bookId =item.bookId;
      			let thumbnail = item.thumbnail;

      			resHTML += "<div class='row mb-2'>";
      			resHTML += '<div class="col-1 text-center">'+postId+'</div>';
      			resHTML += '<div class="col-2 text-center"><img src="'+thumbnail+'" style="width:100%;border: 5px groove beige;" onclick="fnOpenBooks(this)" bookId="'+bookId+'" postId="'+postId+'" reqUuid="'+reqUuid+'" ></div>';
      			resHTML += '<div class="col-5">'+title+'</div>';
      			resHTML += '<div class="col-2 text-center">'+nickname+'</div>';
      			resHTML += '<div class="col-2 text-center">'+department+'</div>';
      			resHTML += '</div><hr/>';
            });

        	$("#boardList").append(resHTML);
        }
    })
})
</script>

<script>
	function fnOpenBooks(post){
		console.log(post);

		let postId = $(post).attr('postId');
		console.log(postId);
		$.ajax({
			url: "http://poly-library.com/post-service/posts/"+postId,
			type: "get",
			success: function (data) {
				console.log(data);
				$('#img').attr("src", data.thumbnail);

				let resHTML = "";
				resHTML += "<div class='mb-3'><p class='mb-1'>글번호 : </p><p id='postId'>"+data.postId+"</p></div>";
				resHTML += "<div class='mb-3'><p class='mb-1'>책번호 : </p><p id='bookId'>"+data.bookId+"</p></div>";
				resHTML += "<div class='mb-3'><p class='mb-1'>도서명 : </p><p id='title'>"+data.title+"</p></div>";
				resHTML += "<div class='mb-3'><p class='mb-1'>저자 : </p><p id='author'>"+data.author+"</p></div>";
				resHTML += "<div class=''><p class='mb-1'>요청자 : </p><p id='nickname'>"+data.nickname+"</p></div>";
				resHTML += "<div class=''><p class='mb-1'>요청자 학과 : </p><p id='department'>"+data.department+"</p></div>";
				resHTML += "<div class=''><p class='mb-1' ></p><p id='reqUuid' hidden>"+data.userUuid+"</p></div>";
				$('#book_info').html(resHTML);
			}
		})

		$('#books-modal').modal('show');

	}
</script>

<script>
	function getChat(post){

		let thumbnail = $('#img').attr("src");
		let title = $('#title').html();
		let author = $('#author').html();
		let bookId = $('#bookId').html();
		let postId = $('#postId').html();
		let nickname = $('#nickname').html();
		let department = $('#department').html();
		let reqUuid = $('#reqUuid').html();

		bookId *= 1;
		postId *= 1;
		console.log(thumbnail);
		console.log(title);
		console.log(author);
		console.log(bookId);
		console.log(postId);
		console.log(nickname);
		console.log(department);
		console.log(reqUuid);
		$.ajax({
			url: "http://poly-library.com/chat-service/rooms",
			type: "post",
			data:JSON.stringify({
				"bookId": bookId,
				"bookName": title,
				"thumbnail": thumbnail,
				"deliveryId": sessionStorage.getItem("userUuid"),
				"deliveryName": sessionStorage.getItem("nickname"),
				"deliveryDepartment": sessionStorage.getItem("department"),
				"rentalReqId": reqUuid,
				"rentalReqName": nickname,
				"rentalReqDepartment":department,
			}),
			dataType: "JSON",
			contentType: "application/json",
			success: function (data) {
				Swal.fire({
					position: 'center',
					icon: 'success',
					title: '대화방으로 들어갑니다.',
					showConfirmButton: false,
					timer: 1500
				})
				$('#books-modal').modal('hide');
				sessionStorage.setItem('opponentName', data.rentalReqName);
				sessionStorage.setItem('opponentDepartment', data.rentalReqDepartment);
				sessionStorage.setItem('bookName', data.bookName);
				sessionStorage.setItem('roomId', data.roomId);
				location.href="/room/enter?roomId="+data.roomId+"&name="+sessionStorage.getItem("nickname")+"&userid="+userUuid;
			}
		})
	}
</script>
</body>
</html>
