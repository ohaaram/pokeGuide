package com.pokeguide.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.pokeguide.service.SocketIOService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig {

    @Value("${front1.url}")
    private String frontUrl1;

    @Value("${front.url}")
    private String frontUrl;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(frontUrl1);
        config.setPort(8081);
        config.setOrigin(frontUrl);  // 모든 도메인 허용
        SocketIOServer server = new SocketIOServer(config);
        server.start(); // 서버 시작
        return server;
    }

    @Bean
    public SocketIOService socketIOService(SocketIOServer socketIOServer) {
        return new SocketIOService(socketIOServer);
    }
}
