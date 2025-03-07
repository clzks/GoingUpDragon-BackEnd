package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.dto.SignupRequestDTO;
import com.goingupdragon.going_up_dragon.service.SignupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDTO request) {
        try {
            signupService.registerUser(request.getEmail(), request.getPassword(), request.getPhoneNumber());
            // JSON 형태로 메시지를 반환
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "회원가입 성공!");
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HashMap<String, String>() {{
                        put("error", "회원가입 실패: " + e.getMessage());
                    }});
        }
    }
}