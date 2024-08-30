package com.pokeguide.service;

import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
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

        // 클라이언트 연결 시 이벤트 리스너 등록
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());

        // 클라이언트가 "join room" 이벤트를 보낼 때 처리
        server.addEventListener("join room", String.class, onJoinRoom());

        // 클라이언트가 "chat message" 이벤트를 보낼 때 처리
        server.addEventListener("chat message", String.class, onChatMessage());
    }

    private ConnectListener onConnected() {
        return client -> {
            String uid = client.getHandshakeData().getSingleUrlParam("uid");
            if (uid == null) {
                System.err.println("Client connected with null UID");
            } else {
                System.out.println("Client connected with UID: " + uid);
            }
        };
    }


    private DisconnectListener onDisconnected() {
        return client -> {
            String uid = client.getHandshakeData().getSingleUrlParam("uid");
            System.out.println("Client disconnected: " + uid);
        };
    }

    private DataListener<String> onJoinRoom() {
        return (client, room, ackSender) -> {
            System.out.println("Client joining room1: " + room);
            client.joinRoom(room);
            System.out.println("Client joined room2: " + room);
        };
    }


    private DataListener<String> onChatMessage() {
        return (client, message, ackSender) -> {
            String chatNo = client.getHandshakeData().getSingleUrlParam("chatNo");
            if (chatNo == null) {
                System.err.println("Chat number is null. Cannot proceed with sending the message.");
                return;
            }

            System.out.println("Sending message to chatNo: " + chatNo);
            server.getRoomOperations(chatNo).sendEvent("chat message", message);
        };
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
        if (uid == null || uid.isEmpty()) {
            System.err.println("UID is null or empty. Notification cannot be sent.");
            return;
        }

        System.out.println("Sending notification to UID: " + uid + " with message: " + message);
        server.getRoomOperations(uid).sendEvent("notification", message);
    }


}
