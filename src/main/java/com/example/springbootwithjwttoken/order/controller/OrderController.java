package com.example.springbootwithjwttoken.order.controller;


import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.order.dto.PlaceOrderDTO;
import com.example.springbootwithjwttoken.order.service.OrderService;
import com.example.springbootwithjwttoken.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public APIResponse placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO,  @RequestHeader("Authorization") String token){
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(validateToken.equals(validateToken)){
            apiResponse = orderService.placeOrder(placeOrderDTO,validateToken);
        }else{
            apiResponse.setStatus(401);
            apiResponse.setMessage(null);
            apiResponse.setMessage("Unauthorized");
        }
        return apiResponse;
    }

    @GetMapping("/history")
    public APIResponse getHistory(@RequestHeader("Authorization") String token){
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(validateToken.equals(validateToken)){
            apiResponse = orderService.getHistory(validateToken);
        }else{
            apiResponse.setStatus(401);
            apiResponse.setMessage(null);
            apiResponse.setMessage("Unauthorized");
        }
        return apiResponse;
    }
}
