
    document.addEventListener("DOMContentLoaded", function(){
    connectStomp();

    //dashboard/myChat 접속 시 불러올것 없음
    if(roomId != "" && yourNickname != "dashboard"){
    loadHistory();
}
});
    // STOMP 및 SockJS 클라이언트 설정
    const myNickname = document.querySelector(".loginMemberNickname").value;
    let temp = window.location.pathname.replace("/dashboard/myChat/", "").split('/');
    const roomId = temp[0];
    const yourNickname = temp[1];
    let socket = new SockJS(`http://${location.host}/api/chat/stomp-chatting`);

    let client = Stomp.over(socket);
    //client.debug = null;    //콘솔에 디버그 출력 안함

    let chatRoomIdArr = [];
    //push는 myChatList.js에서 해줌
    function connectStomp(){
    client.connect({}, function(){
        console.log("connection open");

        client.subscribe("/api/chat/receive/room/"+roomId+"/"+encodeURI(myNickname), function(event){
            let data = JSON.parse(event.body);
            let senderNickname = data.senderNickname;
            let message = data.message;
            let time = data.sendTime;
            console.log("client.subscribe : " + data);

//            if(senderNickname != myNickname){   //받은 메시지가 내가 작성한 것인 경우 제외
//                if(senderNickname == yourNickname){     //받은 메시지가 대화하고 있는 상대이어야함
//                    receive(senderNickname, message, time);
//                }
//            }
            if(senderNickname != myNickname){   //받은 메시지가 내가 작성한 것인 경우 제외
                receive(senderNickname, message, time);
                changeListContent(message, time);
            }

            if(chatRoomIdArr.includes(roomId) == false){    //새로 추가된 메시지라면
                changeList();
            }


            //스크롤 맨 아래로
            document.querySelector(".msg_history").scrollTop = document.querySelector(".msg_history").scrollHeight;
        });

        //새로운 채팅 열리면 리스트 업데이트
        client.subscribe("/api/chat/receive/"+myNickname, function(event){
            let data = JSON.parse(event.body);
            if(chatRoomIdArr.includes(roomId) == false){    //새로 추가된 메시지라면
                changeList();
            }
        });

    });

}


    $("button.msg_send_btn").on("click", function(event){
    send();
});

    $("input.write_msg").on("keyup", function(event){
    if(event.keyCode == 13){
    send();
}
});

    function send(){
    let message = $("input.write_msg").val();
    if (!message) {
        alert('메시지를 입력해 주세요.');
        return;
    }
    client.send("/api/chat/send/room/"+roomId+"/"+yourNickname, {}, JSON.stringify({
    "senderNickname" : myNickname,
    "message" : message
}));

    sendView(message, nowTime());
    document.querySelector("input.write_msg").value = "";
    changeListContent(message, nowTime());
    //스크롤 맨 아래로
    document.querySelector(".msg_history").scrollTop = document.querySelector(".msg_history").scrollHeight;
}

    function sendView(message, time){
    $(".msg_history").append(`
        <div class="outgoing_msg">
          <div class="sent_msg">
              <p>${message}</p>
              <span class="time_date"> ${time}</span>
          </div>
        </div>
    `);
}


    function receive(senderNickname, message, time){
    $(".msg_history").append(`
        <div class="incoming_msg">
            <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
            <div class="received_msg">
                <div class="receiver">${senderNickname}</div>
                <div class="received_withd_msg">
                    <p>${message}</p>
                    <span class="time_date">${time}</span></div>
            </div>
        </div>
    `);

}



    function nowTime(){
    var today = new Date();

    var year = today.getFullYear();
    var month = ('0' + (today.getMonth() + 1)).slice(-2);
    var day = ('0' + today.getDate()).slice(-2);
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);

    return year + '-' + month  + '-' + day + " " + hours + ':' + minutes;
}



    function loadHistory(){
    fetch("/api/chat/room/"+roomId, {
        method: "get"
    })
        .then((res) => res.json())
        .then((json) => {
            loadHistoryHTML(json);
        });
}

    function loadHistoryHTML(chatList){
    chatList.forEach((chat) => {
        if(chat.senderNickname == myNickname){
            sendView(chat.message, chat.sendTime);
        }else{
            receive(chat.senderNickname, chat.message, chat.sendTime);
        }
    });

    //스크롤 맨 아래로
    document.querySelector(".msg_history").scrollTop = document.querySelector(".msg_history").scrollHeight;
}



    function changeListContent(message, time){
    document.querySelector("#room"+roomId+" .chat_ib .chat_message").innerHTML = message;
    document.querySelector("#room"+roomId+" .chat_ib .chat_date").innerHTML = time;
}
