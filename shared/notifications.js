var socket = new WebSocket("ws://cgitest-nc0tn1t3.cloudapp.net/notifications/marcusk");
var socket = new WebSocket("ws://pmc.westeurope.cloudapp.azure.com/notifications/marcusk");
var socket = new WebSocket("ws://localhost:8080/notifications/marcusk");

socket.onopen = event => {
	console.log("Opened");
	socket.send('{ "message": "Hello, Server!" }');
};
socket.onmessage = function(event) {
	console.log(event);
	var message = JSON.parse(event.data);
	console.log(message);
};

socket.send('{ "message": "Hello, Server!" }');
//  sudo nohup ./start.sh &
