package com.example.springbootwithjwttoken.product.repository;

import com.example.springbootwithjwttoken.product.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.product_quantity = :productQuantity WHERE p.product_id = :productId")
    int updateProductAmount(int productId, int productQuantity);

}
