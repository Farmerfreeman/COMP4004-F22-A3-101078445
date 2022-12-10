var socket = null;
var playerId = null;
let cardCount = 0;

let score = 0;
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
            addToScoreBoard(last.slice(0, -1));
            break;
        case 'OTHER_CONNECTED':
            addToScoreBoard(split[3])
            log(logMessage)
            break;
        case 'START':
            log(logMessage)
            break;
        case 'ADD_CARD':
            addCard(split[2])
            break;
        case 'DISCARD':
            updateDiscard(split[2])
            break;
        case 'PLAYER_TURN':
            log(logMessage + "'s turn.")
            updateTurn(split[2])
            break;
        case 'PLAYER_SCORED':
            log(split[0] + "Player " + split[2] + " scored " + split[3])
            updateScore(split[2], split[3])
            break;
        case 'ROUND_OVER':
            log(logMessage)
            emptyHand()
            break;
        case 'WINNER':
            log(logMessage)
            break;
        case "GAME_OVER":
            log(logMessage)
            break;
        case 'CANT_PLAY':
            log(logMessage)
            break;

    }
}

function sendMessage(){
    socket.send('TEST MESSAGE')
}


function addCard(card){
    let li = document.createElement('li')
    li.innerHTML = card;
    cardCount++;
    li.id = ("card " + cardCount);
    li.onclick = function(){playCard(li.id)}
    document.getElementById('playerHandCards').appendChild(li);
    document.getElementById("numCards").innerHTML=cardCount.toString()

}

function playCard(card){
    if (yourTurn){
        let li = document.getElementById(card)


        let discard = document.getElementById("discardPileCard")


        if(discard.firstElementChild.firstElementChild.innerHTML === li.firstElementChild.firstElementChild.innerHTML
        || discard.firstElementChild.lastElementChild.innerHTML === li.firstElementChild.lastElementChild.innerHTML ||
        li.firstElementChild.firstElementChild.innerHTML === '8'){
            log("You played " + li.firstElementChild.firstElementChild.innerHTML + " of " + li.firstElementChild.lastElementChild.innerHTML.slice(0, -1))
        }
        else {
            log("You can't play that card! Click on the stock pile if you need to draw.")
            return
        }




        discard.innerHTML = li.innerHTML


        li.remove()
        cardCount--;
        console.log("Played card " + card)

        socket.send('PLAYED_CARD|' + card.slice(5))
        document.getElementById("numCards").innerHTML=cardCount.toString()

        updateCards()

        if (cardCount === 0){
            socket.send('ROUND_OVER')
        }



    }
    else {
        log("It is not your turn!")
    }


}

function emptyHand(){
    document.getElementById("playerHandCards").innerHTML = '';
}

function drawCard(){
    if(yourTurn){
        socket.send('DREW_CARD')
    }
    else {
        log("It is not your turn!")
    }

}

function updateDiscard(card){
    let discard = document.getElementById("discardPileCard")
    let li = document.createElement('li')
    li.innerHTML = card;

    console.log(card)
    console.log(li)
    discard.innerHTML = li.innerHTML
}

function updateTurn(player){
    console.log(playerId)
    if (playerId.slice(0, -1) === player){
        yourTurn = true
        log("It is your turn! Choose a card to play. \n If you can't play, click the stock pile to draw.")
    }
    else{

        yourTurn = false
    }

}

function updateScore(player, Pscore){
    console.log("PLayer: " + player)
    score = Pscore
    updateScoreBoard(player, Pscore)
}

function updateCards(){
    let cards = document.getElementById("playerHandCards").children
    for (let i = 0; i < cardCount; i++){
        cards[i].id = ("card " + (i + 1));
    }
}

function addToScoreBoard(id){
    let li = document.createElement('li')
    li.innerHTML = id + " " + 0
    li.id = "score" + id

    document.getElementById("scores").appendChild(li)
}

function updateScoreBoard(id, Pscore){
    let li = document.getElementById("score" + id)
    li.innerHTML = id + " " + Pscore;
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