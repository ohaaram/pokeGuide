package com.pokeguide.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.corundumstudio.socketio.SocketIOServer;

public class SocketIOService {
    private final SocketIOServer server;
    private final ObjectMapper objectMapper;

    public SocketIOService(SocketIOServer server) {
        this.server = server;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void sendMessageToRoom(String chatNo, String event, Object data) {
        try {
            String jsonString = objectMapper.writeValueAsString(data);
            server.getRoomOperations(chatNo).sendEvent(event, jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(String uid, String message) {
        server.getRoomOperations(uid).sendEvent("notification", message);
    }
}
