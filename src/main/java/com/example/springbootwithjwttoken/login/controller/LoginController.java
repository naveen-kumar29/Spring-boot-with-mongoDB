package com.example.springbootwithjwttoken.login.controller;


import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.login.dto.LoginRequestDTO;
import com.example.springbootwithjwttoken.login.dto.SignUpRequestDTO;
import com.example.springbootwithjwttoken.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = loginService.signUp(signUpRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        APIResponse apiResponse = loginService.login(loginRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public String delete() {
        loginService.deleteAll();
        return "Deleted Successfully";
    }

    @GetMapping("/get-profile")
    public ResponseEntity<APIResponse> getProfile(@RequestHeader(value = "Authorization")String token) {
        System.out.println(token);
        APIResponse apiResponse = loginService.getProfile(token);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
