package com.announcement.handler;

import com.announcement.configure.WebSocketSessionManager;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class CustomWebSocketHandler extends TextWebSocketHandler {
    private WebSocketSessionManager sessionManager;

    public CustomWebSocketHandler(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionManager.addSession(session);
    }

    // Other WebSocket handler methods
}