"use strict";

// DOM Elements
const connectBtn = document.querySelector("#connectBtn");
const messageForm = document.querySelector("#messageForm");
const messageInput = document.querySelector("#message");
const chatArea = document.querySelector("#chat-messages");
const senderInput = document.querySelector("#senderId");
const recipientInput = document.querySelector("#recipientId");

let stompClient = null;
let senderId = null;
let recipientId = null;

// Add event listener to the Connect button
connectBtn.addEventListener("click", connect);

// Function to connect to WebSocket
function connect() {
  senderId = senderInput.value.trim();
  recipientId = recipientInput.value.trim();

  if (senderId && recipientId) {
    console.log("Connecting to WebSocket server...");
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
  } else {
    alert("Please enter both Sender ID and Recipient ID.");
    console.log("Sender ID or Recipient ID missing.");
  }
}

// Called when WebSocket is connected
function onConnected() {
  console.log("WebSocket connection established.");

  // Unhide the message form
  if (messageForm) {
    messageForm.classList.remove("hidden");
  }

  // Subscribe to user's message queue
  stompClient.subscribe(`/user/${senderId}/queue/messages`, onMessageReceived);
  console.log(`Subscribed to /user/${senderId}/queue/messages.`);

  // Fetch and display chat history
  fetchChatHistory();
}

// Called when WebSocket encounters an error
function onError(error) {
  console.error("WebSocket connection failed:", error);
  alert("Could not connect to WebSocket server. Please try again.");
}

// Fetch chat history
function fetchChatHistory() {
  console.log("Fetching chat history...");
  fetch(`/messages/${senderId}/${recipientId}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((messages) => {
      console.log("Chat history retrieved:", messages);
      messages.forEach((msg) =>
        displayMessage(msg.senderId, msg.content, msg.timestamp)
      );
    })
    .catch((error) => {
      console.error("Error fetching chat history:", error);
    });
}

// Function to send a chat message
function sendMessage(event) {
  event.preventDefault();

  const messageContent = messageInput.value.trim();
  if (messageContent && stompClient && stompClient.connected) {
    const chatMessage = {
      senderId: senderId,
      recipientId: recipientId,
      content: messageContent,
      timestamp: new Date(),
    };

    console.log("Sending message:", chatMessage);
    stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
    displayMessage(senderId, messageContent);
    messageInput.value = "";
  } else {
    console.warn("Message content is empty or WebSocket is not connected.");
  }
}

// Function to handle received messages
function onMessageReceived(payload) {
  try {
    const message = JSON.parse(payload.body);
    console.log("Message received:", message);
    displayMessage(message.senderId, message.content, message.timestamp);
  } catch (error) {
    console.error("Error processing received message:", error);
  }
}

// Function to display messages in the chat area
function displayMessage(sender, content, timestamp = null) {
  const messageContainer = document.createElement("div");
  messageContainer.classList.add("message");

  // Determine if the message is sent or received
  if (sender === senderId) {
    messageContainer.classList.add("sender");
  } else {
    messageContainer.classList.add("receiver");
  }

  const messageContent = document.createElement("p");
  messageContent.textContent = content;

  const messageTimestamp = document.createElement("span");
  messageTimestamp.classList.add("timestamp");
  messageTimestamp.textContent = timestamp
    ? new Date(timestamp).toLocaleTimeString()
    : new Date().toLocaleTimeString();

  messageContainer.appendChild(messageContent);
  messageContainer.appendChild(messageTimestamp);
  chatArea.appendChild(messageContainer);

  // Scroll to the latest message
  chatArea.scrollTop = chatArea.scrollHeight;

  console.log("Displayed message:", { sender, content, timestamp });
}

// Add event listener for sending messages
messageForm.addEventListener("submit", sendMessage);
