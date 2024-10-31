package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long>{
public UserEntity findByUsername(String username);


}
