package com.example.springbootwithjwttoken.product.controller;

import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.product.dto.AddProductRequestDTO;
import com.example.springbootwithjwttoken.product.service.ProductService;
import com.example.springbootwithjwttoken.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/add-product")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequestDTO addProductRequestDTO, @RequestHeader("Authorization") String token) {
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(jwtUtils.validateJwt(validateToken)) {
            apiResponse = productService.addProduct(addProductRequestDTO);
        }else{
            apiResponse.setMessage("Unauthorized");
            apiResponse.setData(null);
            apiResponse.setStatus(401);
        }

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get-products")
    public ResponseEntity<APIResponse> getProduct(@RequestHeader("Authorization") String token){
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(jwtUtils.validateJwt(validateToken)) {
             apiResponse = productService.getProduct();
        }else{
            apiResponse.setMessage("Unauthorized");
            apiResponse.setData(null);
            apiResponse.setStatus(401);
        }

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get-product/{product_id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable("product_id") int productId, @RequestHeader("Authorization") String token){
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(jwtUtils.validateJwt(validateToken)) {
             apiResponse = productService.getProductById(productId);
        }else{
            apiResponse.setMessage("Unauthorized");
            apiResponse.setData(null);
            apiResponse.setStatus(401);
        }

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get_filter/{product_price}")
    public ResponseEntity<APIResponse> getProductFilter(@PathVariable("product_price") int productPrice, @RequestHeader("Authorization") String token){
        APIResponse apiResponse = new APIResponse();
        String validateToken = token.substring(7);
        if(jwtUtils.validateJwt(validateToken)) {
             apiResponse = productService.getProductFilter(productPrice);
        }else{
            apiResponse.setMessage("Unauthorized");
            apiResponse.setData(null);
            apiResponse.setStatus(401);
        }

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
