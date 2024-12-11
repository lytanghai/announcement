package com.announcement.configure;

import com.announcement.handler.SocketConnectionHandler;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Arrays;
import java.util.List;

// web socket connections is handled 
// by this class
@Configuration
@EnableWebSocket
public class WebSocketConfig
    implements WebSocketConfigurer {

    private List<String> CHANNEL = Arrays.asList("/public", "/news", "/gold");

    // Overriding a method which register the socket
    // handlers into a Registry
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // For adding a Handler we give the Handler class we
        // created before with End point Also we are managing
        // the CORS policy for the handlers so that other
        // domains can also access the socket
        CHANNEL.forEach(route -> webSocketHandlerRegistry
                .addHandler(new SocketConnectionHandler(), route)
                .setAllowedOrigins("*"));
    }


    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(8080);

        return new SocketIOServer(config);
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManager();
    }

    @Bean
    public CommandLineRunner destroySessions(WebSocketSessionManager sessionManager) {
        return args -> {
            // Close all WebSocket sessions
            sessionManager.closeAllSessions();
        };
    }
}