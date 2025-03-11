package com.example.springbootwithjwttoken.repository;

import com.example.springbootwithjwttoken.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    User findByEmailIdAndPassword(String email,String password);
}
