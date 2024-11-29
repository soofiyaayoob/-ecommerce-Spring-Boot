package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long>{
	
	 UserEntity findByEmail(String email);

public Optional<UserEntity> findByUsername(String username);
//@Query("SELECT u FROM UserEntity u JOIN FETCH u.role WHERE u.username = :username")
//Optional<UserEntity> findByUsername(@Param("username") String username);

UserEntity findByToken(String token);


}
