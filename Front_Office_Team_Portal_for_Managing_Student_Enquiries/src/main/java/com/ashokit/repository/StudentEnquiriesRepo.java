package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashokit.entity.StudentEnquiriesEntity;

@Repository
public interface StudentEnquiriesRepo extends JpaRepository<StudentEnquiriesEntity, Integer> {

}
