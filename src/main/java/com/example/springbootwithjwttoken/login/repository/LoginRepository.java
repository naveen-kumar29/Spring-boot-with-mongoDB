package com.example.springbootwithjwttoken.login.repository;

import com.example.springbootwithjwttoken.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer> {

    User findByEmailIdAndPassword(String email,String password);

    boolean existsById(int Id);
}
