package com.example.mspostulaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videocall")
public class SignalingController {

    private final SignalingWebSocketHandler webSocketHandler;

    @Autowired
    public SignalingController(SignalingWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @PostMapping("/send/{roomId}")
    public ResponseEntity<String> sendMessage(@PathVariable String roomId, @RequestBody String message) {
        webSocketHandler.broadcastMessageToRoom(roomId, message);
        return ResponseEntity.ok("Message sent to room " + roomId);
    }
}
