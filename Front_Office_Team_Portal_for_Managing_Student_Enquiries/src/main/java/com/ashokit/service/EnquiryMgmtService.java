package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.AddStudentEnqForm;
import com.ashokit.binding.DashboardResponseForm;
import com.ashokit.binding.EnqSearchCriteriaForm;
import com.ashokit.entity.StudentEnquiriesEntity;
import com.ashokit.entity.UserDetailsEntity;

public interface EnquiryMgmtService {

	public DashboardResponseForm getDashBoardData(Integer userId);
	
	public List<String> courseNames();
	
	public List<String> enqStatus();
	
	public boolean saveStudEnq(AddStudentEnqForm enqForm);
	
	public List<StudentEnquiriesEntity> getEnquiries(Integer userId);
	
	//public boolean upsert(AddStudentEnqForm enqForm);
	
	public List<StudentEnquiriesEntity> getFilterEnquiries(EnqSearchCriteriaForm criteria,Integer userId);;

	public StudentEnquiriesEntity getStudentById(Integer id);
	
	public boolean updateStudent(StudentEnquiriesEntity studEntity );
	
	public UserDetailsEntity getUserById(Integer userId);
}
