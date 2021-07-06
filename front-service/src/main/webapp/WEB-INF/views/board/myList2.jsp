<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<html>
<head>
	<title>df</title>
	<%@include file="../include/css.jsp" %>
	<style>
		.hidden{
			display:none;
		}
		section{
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
	<section id="hero" class="d-flex flex-column align-items-center">
		<div class="hero-container" data-aos="fade-in" style="width:100%">
			<div class="container justify-content-center" id="boardList" style="overflow:auto">
				<div class="row mb-2">
					<div class="col-2 text-center">
						등록번호
					</div>
					<div class="col-7 text-center">
						책
					</div>
					<div class="col-3 text-center">
						저자
					</div>
				</div>

			</div>
		</div>
	</section>
</main>

<!-- End Header -->
<%@include file="../include/js.jsp" %>
<script type="text/javascript">


	window.addEventListener('load', function () {
		$.ajax({
			url: "http://3.35.142.240/post-service/posts/"+userUuid+"/requests",
			type: "get",
			contentType: "json",
			success: function (data) {

				console.log(data);
				let resHTML = "";
				$.each(data, function(index, item){
					let postId = item.postId;
					let bookId = item.bookId;
					let title =item.title;
					let thumbnail = item.thumbnail;
					let author = item.author;

					resHTML += "<div class='row mb-2'>";
					resHTML += '<div class="col-2 text-center">'+postId+'</div>';
					resHTML += '<div class="col-1 text-center"><img src="'+thumbnail+'" style="width:100%;"></div>';
					resHTML += '<div class="col-6">'+title+'</div>';
					resHTML += '<div class="col-3 text-center">'+author+'</div>';
					resHTML += '</div>';
				});

				if(resHTML==""){
					resHTML += "<div class='row mb-2 text-center'>";
					resHTML += "<p>신청된 책이 없습니다.</p>";
					resHTML += "</div>";

				}

				$("#boardList").append(resHTML);
			}
		})
	})
</script>

</body>
</html>
