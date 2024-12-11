package com.announcement.configure;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class WebSocketSessionManager {
    private List<WebSocketSession> sessions = new ArrayList<>();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void closeAllSessions() {
        for (WebSocketSession session : sessions) {
            try {
                session.close();
            } catch (Exception e) {
                // Handle any exceptions that may occur during session closing
            }
        }
        sessions.clear();
    }
}