package com.example.springbootwithjwttoken.order.service;

import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.order.dto.PlaceOrderDTO;
import com.example.springbootwithjwttoken.order.model.PlaceOrder;
import com.example.springbootwithjwttoken.order.repository.PlaceOrderRepository;
import com.example.springbootwithjwttoken.product.model.Product;
import com.example.springbootwithjwttoken.product.repository.ProductRepository;
import com.example.springbootwithjwttoken.product.service.ProductService;
import com.example.springbootwithjwttoken.util.CommonUtils;
import com.example.springbootwithjwttoken.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    @Autowired
    private ProductRepository productRepository;


    public APIResponse placeOrder(PlaceOrderDTO placeOrderDTO,String validateToken) {
        APIResponse apiResponse = new APIResponse();

        //validation
        if(
           commonUtils.intValidation(placeOrderDTO.getProduct_id())&&
           commonUtils.stringValidation(placeOrderDTO.getProduct_name())&&
           commonUtils.intValidation(placeOrderDTO.getProduct_price())&&
           commonUtils.intValidation(placeOrderDTO.getProduct_quantity())&&
           commonUtils.stringValidation(placeOrderDTO.getProduct_discount())
        ){
           int userId = jwtUtils.getUserId(validateToken);
           try {


           PlaceOrder placeOrder = new PlaceOrder();
           placeOrder.setProduct_id(placeOrderDTO.getProduct_id());
           placeOrder.setUser_id(userId);
           placeOrder.setProduct_name(placeOrderDTO.getProduct_name());
           placeOrder.setProduct_price(placeOrderDTO.getProduct_price());
           placeOrder.setProduct_quantity(placeOrderDTO.getProduct_quantity());
           placeOrder.setProduct_discount(placeOrderDTO.getProduct_discount());
           placeOrderRepository.save(placeOrder);
               Product product = productRepository.findById(placeOrderDTO.getProduct_id()).get();
               int quantity = product.getProduct_quantity() - placeOrderDTO.getProduct_quantity();
           productRepository.updateProductAmount(placeOrderDTO.getProduct_id(),quantity);
           apiResponse.setStatus(200);
           apiResponse.setData(placeOrder);
           apiResponse.setMessage("Order Placed Successfully");
           }catch (Exception e){
               e.printStackTrace();
               apiResponse.setStatus(500);
               apiResponse.setMessage(e.getMessage());
               apiResponse.setData(null);
               return apiResponse;
           }

        }else{
            apiResponse.setStatus(404);
            apiResponse.setData(null);
            apiResponse.setMessage("Some Field is Missing");
        }
        return apiResponse;
    }

    public APIResponse getHistory(String validateToken) {
        APIResponse apiResponse = new APIResponse();
        int userId = jwtUtils.getUserId(validateToken);
        List<PlaceOrder> allProducts = placeOrderRepository.findAll();

        List<PlaceOrder> placeOrders = new ArrayList<>();
        for (PlaceOrder placeOrder : allProducts) {
            if(placeOrder.getUser_id() == userId){
                placeOrders.add(placeOrder);
            }
        }

        apiResponse.setData(placeOrders);
        return apiResponse;
    }
}
