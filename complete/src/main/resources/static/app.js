var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
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
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showResponse(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {},
        JSON.stringify(
            {
                'formula': $("input[name=formula]:checked").val(),
                'betta': $("#betta").val(),
                'gamma': $("#gamma").val(),
                'order': $("#order").val()
            }
        )
    );
}

function showResponse(response) {
    $("#greetings").empty();
    $("#greetings").append("<tr><td>" + response.content + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() {
        $("#greetings").empty();
        $("#greetings").append('<div class="loader"></div>');
        sendName();
    });

    connect();
});

