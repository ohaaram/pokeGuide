package com.pokeguide.handler;


import com.pokeguide.dto.ChatMessageDTO;
import com.pokeguide.entity.ChatMessage;
import com.pokeguide.repository.ChatMessageRepository;
import com.pokeguide.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.HashMap;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebsocketHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        //메시지 발송
        String msg = message.getPayload();
        String [] parts = msg.split("\\*");
        if(parts[0].equals("fileUpload")){
            ChatMessage chatMessage = chatMessageRepository.findById(Integer.parseInt(parts[1])).get();
            msg=
                    "file"+ "*"+
                            chatMessage.getOName()+"*"
                            +chatMessage.getSName()+"*"+
                            chatMessage.getUid() + "*"+chatMessage.getCDate()
                            +"*"+chatMessage.getChatNo()+"*"+chatMessage.getMessage();
        }else{
            // 메시지를 DTO로 변환
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
            chatMessageDTO.setMessage(parts[0]);
            chatMessageDTO.setChatNo(Integer.parseInt(parts[1]));
            chatMessageDTO.setUid(parts[2]);
            chatMessageDTO.setCDate(LocalDateTime.now());

            // 메시지 저장
            chatService.saveChat(chatMessageDTO);
        }

        for(String key : sessionMap.keySet()) {
            WebSocketSession wss = sessionMap.get(key);
            try {
                wss.sendMessage(new TextMessage(msg));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        log.info("here//");
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
        log.info("here2//");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}
