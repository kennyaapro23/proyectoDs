package com.example.mspostulaciones.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SignalingWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Map<String, WebSocketSession>> rooms = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId != null && rooms.containsKey(roomId)) {
            for (WebSocketSession s : rooms.get(roomId).values()) {
                if (s.isOpen() && !s.getId().equals(session.getId())) {
                    s.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = (String) session.getAttributes().get("roomId");
        rooms.putIfAbsent(roomId, new HashMap<>());
        rooms.get(roomId).put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId != null && rooms.containsKey(roomId)) {
            rooms.get(roomId).remove(session.getId());
            if (rooms.get(roomId).isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }

    public void broadcastMessageToRoom(String roomId, String message) {
        if (rooms.containsKey(roomId)) {
            for (WebSocketSession session : rooms.get(roomId).values()) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
