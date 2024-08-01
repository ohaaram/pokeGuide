package com.pokeguide.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.pokeguide.service.SocketIOService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(8081);
        SocketIOServer server = new SocketIOServer(config);
        server.start(); // 서버 시작
        return server;
    }

    @Bean
    public SocketIOService socketIOService(SocketIOServer socketIOServer) {
        return new SocketIOService(socketIOServer);
    }
}
