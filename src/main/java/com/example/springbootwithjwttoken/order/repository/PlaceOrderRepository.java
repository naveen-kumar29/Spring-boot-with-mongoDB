package com.example.springbootwithjwttoken.order.repository;


import com.example.springbootwithjwttoken.order.model.PlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOrderRepository extends JpaRepository<PlaceOrder, Integer> {
}
