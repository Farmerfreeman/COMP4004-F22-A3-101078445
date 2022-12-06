var socket = null;
var playerId = null;
let cardCount = 0;

let yourTurn = false;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}



function setUID(uid) {
    if (uid != null) {
        var stripped = uid.replace(/\./g, ' ');
        document.getElementById('consoleLabel').innerHTML = 'Console (UID: ' + stripped + ')';
        playerId = uid;
    } else {
        document.getElementById('consoleLabel').innerHTML = 'Console';
        playerId = '';
    }
}

function connect() {
    socket = new SockJS('/game');
    socket.onopen = function(){
        setConnected(true);
        clientLog("Connection Opened")
    };
    socket.onmessage = function(event){
        dispatch(event.data);
    };


}

function disconnect() {

    setConnected(false);
    console.log("Disconnected");
}
function dispatch(message){
    let split = message.split('|');
    let logMessage = split[0].concat(split[2])
    console.log(split)
    switch (split[1]){
        case 'CONNECTED':
            log(logMessage)
            let connectedMessage = logMessage.split(' ');
            let last = connectedMessage[connectedMessage.length - 1];
            if (split[1] === 'CONNECTED') {
                setUID(last);
            }
            break;
        case 'OTHER_CONNECTED':
            log(logMessage)
            break;
        case 'START':
            log(logMessage)
            break;
        case 'ADD_CARD':
            addCard(split[2])
            break;
    }
}

function sendMessage(){
    socket.send('TEST MESSAGE')
}

function testCard(){
    socket.send('|')
}

function addCard(card){
    let li = document.createElement('li')
    li.innerHTML = card;
    cardCount++;
    li.id = ("card " + cardCount);
    li.onclick = function(){playCard(li.id)}
    document.getElementById('playerHandCards').appendChild(li);
}

function playCard(card){
    if (yourTurn){
        let li = document.getElementById(card)


        let discard = document.getElementById("discardPileCard")
        discard.innerHTML = li.innerHTML

        li.remove()
        cardCount--;
        console.log("Played card " + card)

        socket.send('PLAYED_CARD_' + card)

    }
    else {
        log("It is not your turn!")
    }


}


/**
 * Log from the client.
 * @param message the message.
 */
function clientLog(message) {
    var pad = '00';
    var date = new Date();
    var hour = "" + date.getHours();
    var hourPad = pad.substring(0, pad.length - hour.length) + hour;
    var min = "" + date.getMinutes();
    var minPad = pad.substring(0, pad.length - min.length) + min;
    var hourMin = hourPad + ':' + minPad;
    var prefix = '<strong>' + hourMin + ' Client' + '</strong>: ';
    log(prefix + message);
}

/**
 * Log to the console
 * @param message the message.
 */
function log(message) {
    var console = document.getElementById('console');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.innerHTML = message;

    console.appendChild(p);
    while (console.childNodes.length > 25) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });

});