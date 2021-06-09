<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/5.0.1/css/bootstrap.min.css">

    <!-- chat CSS -->
    <link rel="stylesheet" href="/css/chat.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.min.css" rel="stylesheet"/>
    <style>
        [v-cloak] {
            display: none;
        }

        .list-group {
            width: 500px;
            height: 800px;
            background-color: black;
            overflow: scroll;
        }

        .mCSB_container{
            margin-right: 0px !important;
        }

    </style>

</head>
<body>

<!-- chat -->
<div class="chat" id="app" >
    <div class="chat-title">
        <h1> 상대방 이름 </h1>
        <h2> 상대방 과 이름</h2>
        <figure class="avatar">
            <img src="https://png.pngtree.com/png-vector/20190420/ourlarge/pngtree-vector-business-man-icon-png-image_966609.jpg" /></figure>
    </div>
    <div class="messages">
        <div class="messages-content" >

            <template v-for="(r,i) in room ">
                <!-- 김선열 쓴 부분을 session값에서 자기 이름으로 변경필요 -->
                <div v-if="r.fromUserName == '김선열'" class="message message-personal">{{r.message}}</div>
                <div v-else class="message new">
                    <figure class="avatar">
                        <img src="https://png.pngtree.com/png-vector/20190420/ourlarge/pngtree-vector-business-man-icon-png-image_966609.jpg" />
                    </figure>
                    <span> {{r.message}} </span>
                </div>
            </template>

        </div>
    </div>
    <div class="message-box">
        <textarea type="text" class="message-input" placeholder="Type message..." v-model="message" v-on:keypress.enter="sendMessage" style="overflow: hidden"></textarea>
        <button type="button" class="message-submit"  @click="sendMessage">Send</button>
    </div>
</div>
<div class="bg"></div>


<%--<div class="container" id="app" v-cloak>--%>
<%--    <div>--%>
<%--        <h2>${roomId}</h2>--%>
<%--    </div>--%>
<%--    <div class="input-group">--%>
<%--        <div class="input-group-prepend">--%>
<%--            <label class="input-group-text">내용</label>--%>
<%--        </div>--%>
<%--        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">--%>
<%--        <div class="input-group-append">--%>
<%--            <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="list-group">--%>
<%--        <div class="list-group-item" v-for="(r,i) in room">--%>
<%--            {{r.fromUserName}} - {{r.message}} - {{i}}--%>
<%--        </div>--%>
<%--        <#--        <div class="list-group-item" v-for="message in messages">-->--%>
<%--        <#--            {{message.fromUserName}} - {{message.message}}</a>-->--%>
<%--        <#--        </div>-->--%>
<%--    </div>--%>
<%--    <div></div>--%>
<%--</div>--%>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<!-- chat js -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.concat.min.js"></script>
<script>
    //alert(document.title);
    // websocket & stomp initialize


    let sock = new SockJS("/ws-stomp");
    let ws = Stomp.over(sock);
    let reconnect = 0;
    // vue.js
    let vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            userId: {},
            fromUserId: '',
            fromUserName: '',
            toUserId: '',
            toUserName: '',
            message: '',
            messages: []
        },
        created() {
            this.findRoom();
        },
        mount() {
            axios.get()
        },
        methods: {
            findRoom: function() {
                axios.get('/chat-service/chats/'+'${roomId}').then(response => {
                    <!--axios.get(('/chats/{}',${roomId})).then(response => {-->
                    this.room = response.data;
                    <!--this.user = {${userData}};-->
                    console.log("response.data : {}", response.data);
                    this.roomId = '${roomId}';
                    this.fromUserName = '세뇨르';
                    // this.massages.unshift()
                    <!--this.fromUserId = '${userId}'; -->
                    <!--this.fromUserName = '${userName}';-->
                    console.log(response.data);
                }).then(function(){
                    //메세지가 vue js로 다 뜬후에 스크롤 생성 동작 해야함(스크롤 생성이 먼저 동작시 메시지가 보이지 않음)
                    $('.messages-content').mCustomScrollbar();
                    updateScrollbar();
                })
            },
            sendMessage: function() {
                ws.send("/chats/"+"${roomId}"+"/message", {}, JSON.stringify({roomId:'${roomId}', fromUserId:'edc0475e-3685-43ca-97c6-3b75aeae9c7f', fromUserName:'김선열' ,message:this.message}));
                <!--ws.send(("/chats/{}/message", ${roomId}), {}, JSON.stringify({roomId:this.roomId, fromUserId:this.fromUserId, message:this.message}));-->
                this.message = '';
                $('.message-input').val(null);
            },
            recvMessage: function(recv) {
                console.log("recv : {}", recv);
                // this.messages.unshift({"fromUserId":recv.body.fromUserId, "fromUserName": recv.body.fromUserName,"message":recv.body.message})
                this.room.push({"fromUserId":recv.body.fromUserId, "fromUserName": recv.body.fromUserName,"message":recv.body.message})
                /*
                console.log("user :: " + recv.body.fromUserName);
                console.log("message :: " + recv.body.message);
                */
                insertMessage(recv.body.fromUserName, recv.body.message);
            }
        }
    });
    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/queue/chats/"+sessionStorage.getItem('roomId'), function(message) {
                <!--ws.subscribe(("/sub/chats/{}",sessionStorage.getItem('roomId')), function(message) {-->
                console.log("message : {}", message);
                let recv = JSON.parse(message.body);
                console.log("recv : {}", recv);
                vm.recvMessage(recv);
            });
            <%--ws.send("/chats/"+"${roomId}"+"/message", {}, JSON.stringify({roomId:'${roomId}', fromUserId:'edc0475e-3685-43ca-97c6-3b75aeae9c7f', frontUserName:'김선열'}));--%>

        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }
    connect();
</script>

<!-- chat.js -->
<script>
    var $messages = $('.messages-content'),
        d, h, m,
        i = 0;
    /*
    $(window).load(function() {
        $messages.mCustomScrollbar();
        updateScrollbar();
    });
    */
    function updateScrollbar() {
        $messages.mCustomScrollbar("update").mCustomScrollbar('scrollTo', 'bottom', {
            scrollInertia: 10,
            timeout: 0
        });
    }
    function setDate(){
        d = new Date()
        if (m != d.getMinutes()) {
            m = d.getMinutes();
            $('<div class="timestamp">' + d.getHours() + ':' + m + '</div>').appendTo($('.message:last'));
        }
    }
    // '김선열' 부분 세션 자기 자신의 이름 값으로 변경해야함
    function insertMessage(user, msg) {
        if (user == '김선열') {
            $('<div class="message message-personal">' + msg + '</div>').appendTo($('.mCSB_container')).addClass('new');
        } else {
            $('<div class="message new"><figure class="avatar"><img src="https://png.pngtree.com/png-vector/20190420/ourlarge/pngtree-vector-business-man-icon-png-image_966609.jpg" /></figure>' + msg + '</div>').appendTo($('.mCSB_container')).addClass('new');
        }
        setDate();
        updateScrollbar();
    }
</script>
</body>
</html>