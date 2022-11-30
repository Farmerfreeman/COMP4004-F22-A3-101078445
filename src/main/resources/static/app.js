var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (message) {
            showGreeting(JSON.parse(message.body));
        });
        stompClient.subscribe('topic/game'), function (message){
            showGameState(JSON.parse(message.body))
        }
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({
        name: $("#username").val(),
        content: $("#message").val()
    } ));
}

function sendState(){
    stompClient.send("/app/game", {}, JSON.stringify({
        playerTurn: 1,
        players: [
            {name: "p1", cards: null, score: 20},
            {name: "p2", cards: null, score: 10},
            {name: "p3", cards: null, score: 0},
            {name: "p4", cards: null, score: 50}
        ],
        stockPile: [
            {suit: "SPADE", rank: 2}
        ],
        discardPile: [
            {suit: "DIAMOND", rank: 11}
        ],
        gameOver: false,
        roundOver: true
    }));
}




function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.time + message.from + ": " + message.content + "</td></tr>");
}

function showGameState(message){
    $("#greetings").append("<tr><td> Turn player: " + message.playerTurn + " Players: " + message.players + " Cards: " + message.cards + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    $( "#sendGame" ).click(function () { sendState(); })
});