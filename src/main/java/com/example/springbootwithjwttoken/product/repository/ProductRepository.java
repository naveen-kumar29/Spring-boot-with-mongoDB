package com.example.springbootwithjwttoken.product.repository;

import com.example.springbootwithjwttoken.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


}
