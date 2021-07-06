<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Polibrary</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <%--    <link rel="stylesheet" href="/webjars/bootstrap/5.0.1/css/bootstrap.min.css">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <!-- chat CSS -->
    <link rel="stylesheet" href="/css/chat.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.min.css" rel="stylesheet"/>
    <%@include file="../include/css.jsp" %>
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

        #bookName {
            color: gold;
        }

        .mCSB_container{
            margin-right: 0px !important;
        }
        .messages .message.message-personal {
            font-size: 20px;
        }
        .messages .message {
            font-size: 20px;
        }
        #app {
            width: 80%;
            max-width: 800px;
            left: 50%
        }

        @media screen and (max-width: 768px) {
            #app {
                width: 100%;
                max-height: none;
                height: 80vh;
            }
        }


        .chat-title h2 {
            font-size: 12px;
        }

        .chat-title h1 {
            font-size: 22px;
        }
        #opponent__department, #opponent__name, .opponent__avatar {
            text-align: right;
        }

        .message-box .message-input {
            height: 30px;
            font-size: 20px;
        }

        .message-box .message-submit {
            top: 5px;
            line-height: 3;
        }
    </style>

</head>
<body>
<!-- ======= Mobile nav toggle button ======= -->
<i class="bi bi-list mobile-nav-toggle d-xl-none"></i>
<%@include file="../include/header.jsp" %>
<!-- chat -->
<div id="app__box">
    <div class="chat" id="app" >
        <div class="chat-title">
            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                <label class="btn btn-secondary active">
                    <input type="radio" name="options" id="option1" autocomplete="off" checked onclick="statusChange(+0)"> 요청중
                </label>
                <label class="btn btn-secondary">
                    <input type="radio" name="options" id="option2" autocomplete="off" onclick="statusChange(+1)"> 거래중
                </label>
                <label class="btn btn-secondary">
                    <input type="radio" name="options" id="option3" autocomplete="off" onclick="statusChange(+2)"> 완료
                </label>
            </div>
            <h1 id="opponent__name"></h1>
            <h2 id="opponent__department"></h2>
            <h1 id="bookName"></h1>
<%--            <figure class="avatar opponent__avatar" >--%>
<%--                <img src="https://png.pngtree.com/png-vector/20190420/ourlarge/pngtree-vector-business-man-icon-png-image_966609.jpg" class="opponent__avatar"/></figure>--%>
        </div>
        <div class="messages" style="font-size: 20px">
            <div class="messages-content" >
                <template v-for="r in messages ">
                    <!-- 김선열 쓴 부분을 session값에서 자기 이름으로 변경필요 -->
                    <div v-if="r.fromUserName === fromUserName " class="message message-personal">{{r.message}}</div>

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
            <textarea type="text" class="message-input" style="overflow:hidden;" placeholder="Type message..." v-model="message" v-on:keypress.enter="sendMessage"></textarea>
            <button type="button" class="message-submit"  @click="sendMessage">Send</button>
        </div>
    </div>
</div>
<div class="bg"></div>



<!-- JavaScript -->
<%--<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>--%>
<%--<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>--%>
<%--<script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>--%>
<%--<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>--%>
<%@include file="../include/js.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<!-- chat js -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.1/jquery.mCustomScrollbar.concat.min.js"></script>
<script>
    //alert(document.title);
    // websocket & stomp initialize


    let sock = new SockJS("http://poly-library.com:9999/chat-service/chat/message");
    let ws = Stomp.over(sock);
    let nick="";
    let reconnect = 0;
    // vue.js
    let vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            rentalReqId: '',
            deliveryId: '',
            userId: {},
            fromUserId: '',
            fromUserName: '',
            toUserId: '',
            toUserName: '',
            message: '',
            messages: []
        },
        // computed:{
        //     odered: function () {
        //         return : _.orderBy(this.messages, [], "desc");
        //     }
        //
        // }
        created() {
            this.findRoom();
            this.fromUserName = sessionStorage.getItem("nickname");
        },
        mount() {
            // axios.get()
        },
        methods: {
            findRoom: function() {

                axios.get('/chat-service/chats/'+sessionStorage.getItem('roomId')).then(response => {
                    console.log(response)
                    this.messages = response.data;

                }).then(function(){
                    //메세지가 vue js로 다 뜬후에 스크롤 생성 동작 해야함(스크롤 생성이 먼저 동작시 메시지가 보이지 않음)
                    $('.messages-content').mCustomScrollbar();
                    updateScrollbar();
                })
            },
            sendMessage: function() {
                ws.send("/chat-service/pub/chat/message",{}, JSON.stringify({type:'TALK', roomId:sessionStorage.getItem('roomId'), fromUserName:typeof(sessionStorage.getItem("nickname"))==="undefined"?"": sessionStorage.getItem("nickname"), toUserName:sessionStorage.getItem('opponentName'), message:this.message}));
                this.message = '';
            },
            recvMessage: function(recv) {
                this.messages.push({"type":recv.type,"fromUserName":recv.type=='ENTER'?'[알림]':recv.fromUserName,"message":recv.message, "toUserName":sessionStorage.getItem('nickname')})

                insertMessage(recv.fromUserName, recv.message);
            }
        }
    });
    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/chat-service/sub/chat/room/"+sessionStorage.getItem('roomId'), function(message) {
                <!--ws.subscribe(("/sub/chats/{}",${roomId}), function(message) {-->
                let recv = JSON.parse(message.body);
                vm.recvMessage(recv);
            });
            <%--ws.send("/chats/"+"${roomId}"+"/message", {}, JSON.stringify({roomId:'${roomId}', fromUserId:'edc0475e-3685-43ca-97c6-3b75aeae9c7f', frontUserName:'김선열'}));--%>

        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("http://poly-library.com:9999/chat-service/chat/message");
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
        if (user == sessionStorage.getItem('nickname')) {
            $('<div class="message message-personal">' + msg + '</div>').appendTo($('#mCSB_1_container'));
        } else {
            $('<div class="message new"><figure class="avatar"><img src="https://png.pngtree.com/png-vector/20190420/ourlarge/pngtree-vector-business-man-icon-png-image_966609.jpg" /></figure>' + msg + '</div>').appendTo($('#mCSB_1_container'));
        }
        setDate();
        updateScrollbar();
    }

    document.getElementById('opponent__name').innerText = sessionStorage.getItem('opponentName');
    document.getElementById('opponent__department').innerText = sessionStorage.getItem('opponentDepartment');
    document.getElementById('bookName').innerText = sessionStorage.getItem('bookName');
</script>
<script>
    function statusChange(status) {
        console.log("userUuid:"+sessionStorage.getItem("userUuid"));
        console.log("bookId:"+sessionStorage.getItem("bookId"));
        $.ajax({
            // url: "http://poly-library.com/user-service/users/findEmailByStId",
            url: "http://poly-library.com/post-service/posts/users/"+sessionStorage.getItem("userUuid")+"/books/"+(+sessionStorage.getItem("bookId")),
            type : "get",
            success : function(data) {
                console.log("data:"+data);
                $.ajax({
                    url: "http://poly-library.com/post-service/posts/"+data+"/status/"+status,
                    type: "put",
                    dataType: "json",
                    contentType: "application/json;charset:utf-8",
                    success: function (data) {
                        console.log(data)

                        // location.href = "/login"
                    }, error: function (error){
                        Swal.fire("상태변경 실패.", '', 'error');
                    }
                })
            }

        })
    }
</script>

</body>
</html>