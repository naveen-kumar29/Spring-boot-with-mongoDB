package com.example.springbootwithjwttoken.product.service;


import com.example.springbootwithjwttoken.common.APIResponse;
import com.example.springbootwithjwttoken.product.dto.AddProductRequestDTO;
import com.example.springbootwithjwttoken.product.model.Product;
import com.example.springbootwithjwttoken.product.repository.ProductRepository;
import com.example.springbootwithjwttoken.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ProductRepository productRepository;

    public APIResponse addProduct(AddProductRequestDTO addProductRequestDTO) {
        APIResponse apiResponse = new APIResponse();
        //validation
        if(
                commonUtils.stringValidation(addProductRequestDTO.getProduct_name()) &&
                commonUtils.stringValidation(addProductRequestDTO.getProduct_description()) &&
                commonUtils.intValidation(addProductRequestDTO.getProduct_price()) &&
                commonUtils.intValidation(addProductRequestDTO.getProduct_quantity()) &&
                commonUtils.stringValidation(addProductRequestDTO.getProduct_brand()) &&
                commonUtils.stringValidation(addProductRequestDTO.getProduct_category()) &&
                commonUtils.stringValidation(addProductRequestDTO.getProduct_status()) &&
                commonUtils.stringValidation(addProductRequestDTO.getProduct_discount())
        ){
            //dto to entity
            Product product = new Product();
            product.setProduct_name(addProductRequestDTO.getProduct_name());
            product.setProduct_description(addProductRequestDTO.getProduct_description());
            product.setProduct_price(addProductRequestDTO.getProduct_price());
            product.setProduct_quantity(addProductRequestDTO.getProduct_quantity());
            product.setProduct_brand(addProductRequestDTO.getProduct_brand());
            product.setProduct_category(addProductRequestDTO.getProduct_category());
            product.setProduct_status(addProductRequestDTO.getProduct_status());
            product.setProduct_discount(addProductRequestDTO.getProduct_discount());
            //store entity
            productRepository.save(product);

            //return
            apiResponse.setStatus(200);
            apiResponse.setData(product);
            apiResponse.setMessage("Data Inserted Successfully");
        }else{
            apiResponse.setStatus(200);
            apiResponse.setData(null);
            apiResponse.setMessage("Missing some information");
        }


        return apiResponse;
    }


    public APIResponse getProduct() {
        APIResponse apiResponse = new APIResponse();
        List<Product> allProducts = productRepository.findAll();
        if(!allProducts.isEmpty()) {
            apiResponse.setData(allProducts);
            apiResponse.setStatus(200);
            apiResponse.setMessage("Data Fetched Successfully");
        }else{
            apiResponse.setStatus(404);
            apiResponse.setMessage("No products found");
            apiResponse.setData(null);
        }
        return apiResponse;
    }

    public APIResponse getProductById(int productId) {
        APIResponse apiResponse = new APIResponse();
        Product product = productRepository.findById(productId).get();
       if(product !=null){
           apiResponse.setData(productRepository.findById(productId).get());
           apiResponse.setStatus(200);
           apiResponse.setMessage("Data Fetched Successfully");
       }else{
           apiResponse.setStatus(404);
           apiResponse.setMessage("Product Not Found");
           apiResponse.setData(null);
       }
        return apiResponse;
    }

    public APIResponse getProductFilter(int price) {
        APIResponse apiResponse = new APIResponse();
        List<Product> allProducts = productRepository.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : allProducts) {
            if(product.getProduct_price() > price){
                filteredProducts.add(product);
            }
        }
        if(!filteredProducts.isEmpty()){
            apiResponse.setData(filteredProducts);
            apiResponse.setStatus(200);
            apiResponse.setMessage("Data Fetched Successfully");
        }else{
            apiResponse.setStatus(404);
            apiResponse.setMessage("Product Not Found");
            apiResponse.setData(null);
        }
        return apiResponse;
    }
}
