package com.ashokit.repository;

import java.util.List;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

	@Query(value="select ce.courseName from CourseEntity ce")
	public List<String> getCourseName();
}
