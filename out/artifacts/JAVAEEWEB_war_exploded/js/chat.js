var webSocket;
var uID;

function loadData(id) {
    uID = id;
}

if ('WebSocket' in window) {
    webSocket = new WebSocket("ws://localhost:8080/" + window.location.pathname.split("/")[1] + "/webSocket");
} else {
    alert('当前浏览器 Not support websocket')
}

//接收到消息的回调方法
webSocket.onmessage = function (event) {
    var messages = JSON.parse(event.data);
    var table = document.getElementById("messages");
    var tr = document.createElement("tr");
    table.appendChild(tr);
    if (messages.toUID === uID) tr.setAttribute("class", "friend");
    else tr.setAttribute("class", "mine");
    tr.innerHTML = "<td><div class=\"createdTime\">" + messages.createdTime + ".0</div><div class=\"messageContent\">" + messages.content + "</div></td>";
    var h = $(document).height()-$(window).height();
    $(document).scrollTop($(document).height());
}

window.onbeforeunload = function () {
    closeWebSocket();
}

function closeWebSocket() {
    webSocket.close();
}

function sendMessage(uID, friendUID, type) {
    if (document.getElementById("message").value === "" || document.getElementById("message").value === "\n") {
        document.getElementById("message").value = "";
        alert("不能发送空消息！");
        return false;
    }
    webSocket.send(JSON.stringify({
        fromUID: uID,
        toUID: friendUID,
        content: document.getElementById("message").value,
        type: type,
        createdTime: getDateFull()
    }));
    document.getElementById("message").value = "";
}

function getDateFull() {
    var date = new Date();
    var currentdate = date.getFullYear() + "-"
        + appendZero(date.getMonth() + 1) + "-"
        + appendZero(date.getDate()) + " "
        + appendZero(date.getHours()) + ":"
        + appendZero(date.getMinutes()) + ":"
        + appendZero(date.getSeconds());
    return currentdate;
}

function appendZero(s) {
    return ("00" + s).substr((s + "").length);
}

function dealEnter(c) {
    if (c === 13) document.getElementById("send").click();
}