<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<html lang="en">
<head>
	<title>Polibrary</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!-- CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/5.0.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="/webjars/bootstrap/5.0.1/scss/bootstrap.scss">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
	<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.3/jquery.mCustomScrollbar.min.css'>
	<!-- chat CSS -->
	<link rel="stylesheet" href="/css/chat.css">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.min.css" rel="stylesheet"/>

	<%@include file="../include/css.jsp" %>

	<style>
		[v-cloak] {
			display: none;
		}

		.mCSB_container{
			margin-right: 0px !important;
		}
	</style>
</head>
<body>
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<%@include file="../include/header.jsp" %>

<div class="chat" id="app" >
	<div class="chat-title">
		<h1 style="font-size: 15px"> My Requests </h1>
		<h2> </h2>
		<figure class="avatar">
			<img src="https://play-lh.googleusercontent.com/HD6IXr93gVuSM_B4anLrxinYfl2W4QvQvhAfcE8_AIN02zOgyFQJRSekDvL6TktpfH8" /></figure>
	</div>
	<div class="messages">
		<div class="messages-content" >
			<li class="list-group-item list-group-item-action" style="margin: 5px 0px 5px 0px; background-color:lemonchiffon; border-radius: 5px;"  v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId, item.deliveryName, item.deliveryDepartment, item.bookName, item.bookId)">
				<div class="row">
					<div class="col-3">
						<img :src=" item.thumbnail " style="width:100%;border: 5px groove beige;">
					</div>
					<div class="col-9">
						<div class="row mb-3 mt-2">
							<!-- ????????? ????????? ?????? / ??? ????????? -->
							<div class="col-4">
								{{item.deliveryName}}
							</div>
							<div class="col-8">
								{{item.deliveryDepartment}}
							</div>

						</div>
						<div class="row" style="font-size:15px">
							<div class="col-12">
								{{item.bookName}}
							</div>
						</div>
					</div>
				</div>
			</li>
		</div>
	</div>
	<div class="message-box">
	</div>
</div>
<div class="bg"></div>
<%--
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-12">
            <h3>????????? ?????????</h3>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">?????????</label>
        </div>
        <input type="text" class="form-control" v-model="bookId" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">?????????</label>
        </div>
        <input type="text" class="form-control" v-model="bookName" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="deliveryId" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="deliveryName" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="deliveryDepartment" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="rentalReqId" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="rentalReqName" @keyup.enter="createRoom">
        <div class="input-group-prepend">
            <label class="input-group-text">???????????????</label>
        </div>
        <input type="text" class="form-control" v-model="rentalReqDepartment" @keyup.enter="createRoom">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">????????? ??????</button>
        </div>

    </div>
    <ul class="list-group">
        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId)">
            {{item.bookId}}
        </li>
    </ul>
</div>
--%>
<!-- JavaScript -->
<%@include file="../include/js.jsp" %>
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/5.0.1/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<!--jquery , jquery_lib_scroll -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.concat.min.js"></script>
<script>
	let vm = new Vue({
		el: '#app',
		data: {
			roomId: '',
			bookId : '',
			bookName: '',
			deliveryId: '',
			deliveryName: '',
			deliveryDepartment: '',
			rentalReqId: '',
			rentalReqName: '',
			rentalReqDepartment: '',
			chatrooms: []
		},
		created() {
			this.findAllRoom();
		},
		methods: {
			findAllRoom: function() {
				axios.get('http://poly-library.com/chat-service/rooms/'+userUuid+'/requests').then(response => { this.chatrooms = response.data; });
			},
			createRoom: function() {
				alert("createRoom!!!")
				if("" === this.bookId) {
					alert("??????????????? ????????? ????????????.");
					return;
				} else if (this.deliveryId === '') {
					alert("????????????????????? ????????? ????????????.");
					return;
				} else if (this.rentalReqId === '') {
					alert("????????????????????? ????????? ????????????.");
					return;
				} else {
					// let params = new URLSearchParams();
					const params = {};
					params.bookId = this.bookId;
					params.bookName = this.bookName;
					params.deliveryId = this.deliveryId;
					params.deliveryName = this.deliveryName;
					params.deliveryDepartment = this.deliveryDepartment;
					params.rentalReqId = this.rentalReqId;
					params.rentalReqName = this.rentalReqName;
					params.rentalReqDepartment = this.rentalReqDepartment;
					// params.append("bookId",this.bookId);
					// params.append("bookName",this.bookName);
					// params.append("deliveryId",this.deliveryId);
					// params.append("deliveryName",this.deliveryName);
					// params.append("deliveryDepartment",this.deliveryDepartment);
					// params.append("rentalReqId",this.rentalReqId);
					// params.append("rentalReqName",this.rentalReqName);
					// params.append("rentalReqDepartment",this.rentalReqDepartment);

					axios({
						method: 'post',
						url: '/chat-service/rooms/'+userUuid+'requests',
						headers: {
							'Content-Type': 'application/json'
						},
						data: params,
					})
							// axios.post('/chat-service/rooms', params)
							.then(
									response => {
										alert(response.data.bookId+"??? ????????? ?????????????????????.")
										this.bookId = '';
										this.bookName = '';
										this.deliveryId = '';
										this.deliveryName = '';
										this.deliveryDepartment = '';
										this.rentalReqId = '';
										this.rentalReqName = '';
										this.rentalReqDepartment = '';
										this.findAllRoom();
									}
							)
							.catch( response => { alert("????????? ????????? ?????????????????????.");
							})
				}
			},
			enterRoom: function(roomId, deliveryName, deliveryDepartment, bookName, bookId) {
				let name = sessionStorage.getItem("nickname");
				let userid = sessionStorage.getItem("userUuid");
				sessionStorage.setItem('opponentName', deliveryName);
				sessionStorage.setItem('opponentDepartment', deliveryDepartment);
				sessionStorage.setItem('bookId', bookId);
				sessionStorage.setItem('bookName', bookName);
				sessionStorage.setItem('roomId', roomId);
				location.href="/room/enter?roomId="+roomId+"&name="+name+"&userid="+userid;
				// location.href="/room/enter"
			}
		}
	});
	Vue.nextTick(function () {
		setTimeout(function() {
			var $messages = $('.messages-content');
			$messages.mCustomScrollbar();
		}, 500);
	})
</script>
<!-- partial -->

</body>
</html>