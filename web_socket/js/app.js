let ws, currentUser;

// On pressing Connect this method will be called
function connect(topic) {

  console.log(topic)
  ws = new WebSocket('ws://localhost:8080/' + topic);

  //This function will called everytime new message arrives
  ws.onmessage = function (e) {
    console.log(e);
    printMessage(e.data);
  };
  document.getElementById("connectButton").disabled = true;
  document.getElementById("connectButton").value = "Connected";
  document.getElementById("name").disabled = true;
  currentUser = document.getElementById("name").value;
  console.log(currentUser);
}


//This function takes care of printing the message on browser
function printMessage(data) {
  const messages = document.getElementById("messages");

  const newMessage = document.createElement("div");
  newMessage.className = "incoming-message";

  let messageObj = JSON.parse(data.replace(/\n/g, "\\n").replace(/\t/g, "\\t"))

  messageObj = JSON.parse(data)
  newMessage.innerHTML = messageObj["name"] + " : " + messageObj.message + " : " + messageObj.content;
  messages.appendChild(newMessage);
}

//This function handles functionality of sending the message to websocket
function sendToGroupChat() {
  if (ws == undefined) return;
  let messageText = document.getElementById("message").value;
  document.getElementById("message").value = "";
  let name = document.getElementById("name").value;
  let messageObject = {
    name: name,
    message: messageText,
    content: 'hello'
  };

  let newMessage = document.createElement("div");
  newMessage.innerHTML = messageText + " : " + currentUser;
  newMessage.className = "outgoing-message";
  messages.appendChild(newMessage);
  console.log(' messages' + messages);
  //In-Built functions Send is used with object we created
  ws.send(JSON.stringify(messageObject));
}