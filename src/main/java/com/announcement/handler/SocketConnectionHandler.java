package com.announcement.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.announcement.model.Message;
import com.announcement.util.Converter;
import com.announcement.util.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class SocketConnectionHandler extends TextWebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(SocketConnectionHandler.class);

    // In this list all the connections will be stored
    // Then it will be used to broadcast the message
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    // This method is executed when client tries to connect to the sockets

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        super.afterConnectionEstablished(session);
        // Logging the connection ID with Connected Message
        log.info(session.getId().concat(" connected!"));

        // Adding the session into the list
        webSocketSessions.add(session);
    }

    // When client disconnect from WebSocket then => this method is called
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {super.afterConnectionClosed(session, status);
        log.info(session.getId().concat(" disconnected!"));

        // Removing the connection info from the list
        webSocketSessions.remove(session);
    }

    // It will handle exchanging of message in the network
    // It will have a session info who is sending the
    // message Also the Message object passes as parameter
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);

        log.info("receive req payload {}", message.getPayload());
        Map<String,Object> requestBody = Converter.convertJsonStringToMap(String.valueOf(message.getPayload()));

        Validator.validateKeys(requestBody, Message.class);


        // Create a custom message object
//        Message customMessage = new Message();

//        Converter.convert(requestBody, customMessage);

//        customMessage.setTitle("TEST FROM BK");

        // Iterate through the list of WebSocket sessions and send the custom message
        for (WebSocketSession webSocketSession : webSocketSessions) {
            if (session == webSocketSession) {
                continue; // Skip the session that sent the message
            }

//            // Convert the custom message object to JSON and send it to the session
            ObjectMapper objectMapper = new ObjectMapper();
            String customMessageJson = objectMapper.writeValueAsString(message.getPayload());
//            webSocketSession.sendMessage(new TextMessage(customMessageJson));

            log.info("listen message: {}", message);
            log.info("broadcast message {}", new TextMessage(customMessageJson.replaceAll("\\\\","")));
            webSocketSession.sendMessage(new TextMessage(customMessageJson));
//            webSocketSession.sendMessage(message);
//            log.info("Received custom message : {}", message);
        }
    }
}