package com.ashokit.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnqStatusEntity;
import com.ashokit.repository.CourseRepo;
import com.ashokit.repository.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqRepo;
	
	@Override
	public void run(ApplicationArguments args)throws Exception  {
		
		courseRepo.deleteAll();
		// TODO: save course data in table
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java");

		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python");

		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("Devoops");

		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("Angular");
		
		CourseEntity c5= new CourseEntity();
		c5.setCourseName("React");

		courseRepo.saveAll(Arrays.asList(c1, c2, c3, c4,c5));
		
		
		enqRepo.deleteAll();
		//save enqStatus into db
		EnqStatusEntity e1=new EnqStatusEntity();
		e1.setEnqStatus("New");
		
		EnqStatusEntity e2=new EnqStatusEntity();
		e2.setEnqStatus("Lost");
		
		EnqStatusEntity e3=new EnqStatusEntity();
		e3.setEnqStatus("Enrolled");
		
		enqRepo.saveAll(Arrays.asList(e1, e2, e3));
		
		}

}
