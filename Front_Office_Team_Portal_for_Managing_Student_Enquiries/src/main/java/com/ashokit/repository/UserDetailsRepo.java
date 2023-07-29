package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashokit.entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer>{
	
	public UserDetailsEntity findByEmail(String emial);
	
	public UserDetailsEntity findByEmailAndPwd(String email, String pwd);
	
	
}
