package com.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>{

	@Query(value="select es.enqStatus from EnqStatusEntity es")
	public List<String> getEnqStatus();
}
