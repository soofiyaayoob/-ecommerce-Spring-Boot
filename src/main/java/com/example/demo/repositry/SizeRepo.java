package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SizeEntity;
@Repository
public interface SizeRepo extends JpaRepository<SizeEntity, Long>{

}
