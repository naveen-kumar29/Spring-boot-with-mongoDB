package com.example.springbootwithjwttoken.service;


import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.dto.LoginRequestDTO;
import com.example.springbootwithjwttoken.dto.SignUpRequestDTO;
import com.example.springbootwithjwttoken.model.User;
import com.example.springbootwithjwttoken.repository.LoginRepository;
import com.example.springbootwithjwttoken.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginService {


    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private JwtUtils jwtUtils;


    public APIResponse signUp(SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = new APIResponse();
        //validation

        //dto to entity
        User user = new User();
        user.setId(signUpRequestDTO.getId());
        user.setName(signUpRequestDTO.getName());
        user.setGender(signUpRequestDTO.getGender());
        user.setPassword(signUpRequestDTO.getPassword());
        user.setEmailId(signUpRequestDTO.getEmailId());
        user.setPhoneNumber(signUpRequestDTO.getPhoneNumber());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLoginAt(LocalDateTime.now());

        //store entity
        loginRepository.save(user);
        //return
        apiResponse.setData(user);
        apiResponse.setMessage("Data fetched successfully");
        return apiResponse;
    }

    public void deleteAll() {
        loginRepository.deleteAll();
    }

    public APIResponse getProfile(String token) {
        APIResponse apiResponse = new APIResponse();
//        if(jwtUtils.validateJwt("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiIzNDQ0MiIsImlhdCI6MTc0MTY3Nzg3NSwiZXhwIjoxNzQxNjgxNDc1LCJ1c2VySWQiOjM0NDQyLCJ1c2VyTmFtZSI6Im5hdmVlbiJ9.QlMeQsngfN1TbqYeBsW8enF228PHmM5ndSBfkjD6Q33-gXOx0NTc9IpuWFDaUWHh2gIgADo5wgRFtDGlil-0Wg")) {

        String validation = token.substring(7);
        if(jwtUtils.validateJwt(validation)) {
            User user = loginRepository.findById(34442L).get();
            apiResponse.setData(user);
            apiResponse.setMessage("Data fetched successfully");
        }else{
            apiResponse.setMessage("Unauthorized");
            apiResponse.setData(null);
            apiResponse.setStatus(401);
        }
        return apiResponse;
    }

    public APIResponse login(LoginRequestDTO loginRequestDTO) {
        APIResponse apiResponse = new APIResponse();

        //validation

        //verify user exist  with given email and password
        User user = loginRepository.findByEmailIdAndPassword(loginRequestDTO.getEmailId(), loginRequestDTO.getPassword());

        //response
        if(user == null) {
            apiResponse.setMessage("User not found");
            apiResponse.setData(null);
        }else{
            //generate jwt token
           String token =  jwtUtils.generateJwt(user);
           user.setToken(token);
           apiResponse.setData(user);
           apiResponse.setMessage("Login successful");
        }

        return apiResponse;
    }
}
