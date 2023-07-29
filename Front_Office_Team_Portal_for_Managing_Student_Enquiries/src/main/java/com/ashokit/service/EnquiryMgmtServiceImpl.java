package com.ashokit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ashokit.binding.AddStudentEnqForm;
import com.ashokit.binding.DashboardResponseForm;
import com.ashokit.binding.EnqSearchCriteriaForm;
import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnqStatusEntity;
import com.ashokit.entity.StudentEnquiriesEntity;
import com.ashokit.entity.UserDetailsEntity;
import com.ashokit.repository.CourseRepo;
import com.ashokit.repository.EnqStatusRepo;
import com.ashokit.repository.StudentEnquiriesRepo;
import com.ashokit.repository.UserDetailsRepo;

@Service
public class EnquiryMgmtServiceImpl implements EnquiryMgmtService{
	
	@Autowired
	private UserDetailsRepo userRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqStausRepo;

	@Autowired
	private StudentEnquiriesRepo studEnqRepo; 
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponseForm getDashBoardData(Integer userId) {
		
		DashboardResponseForm dashboardResponse=new DashboardResponseForm();
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity entity = findById.get();
			List<StudentEnquiriesEntity> studEnquries = entity.getStudEnquries();
			int totalCount = studEnquries.size();
			int enrolledCount = studEnquries.stream().filter(e->e.getEnqStatus().equals("Enrolled"))
								.collect(Collectors.toList()).size();
			int lostCount = studEnquries.stream().filter(e->e.getEnqStatus().equals("Lost"))
								.collect(Collectors.toList()).size();
			dashboardResponse.setTotalEnq(totalCount);
			dashboardResponse.setEnrolledEnq(enrolledCount);
			dashboardResponse.setLostEnq(lostCount);
			
		}
		return dashboardResponse;
	}

	/*@Override
	public List<String> courseNames() {
		List<CourseEntity> courseEntityList = courseRepo.findAll();
		List<String> courseList=new ArrayList<>();
		for(CourseEntity courseEntity:courseEntityList) {
			courseList.add(courseEntity.getCourseName());
		}
		return courseList;
	}*/
	
	

	/*@Override
	public List<String> enqStatus() {
		List<EnqStatusEntity> enqStatusEntityList = enqRepo.findAll();
		List<String> enqStatusList=new ArrayList<>(); {}
		for(EnqStatusEntity entity:enqStatusEntityList) {
			enqStatusList.add(entity.getEnqStatus());
		}
		return enqStatusList;
	}
*/
	@Override
	public List<String> courseNames() {
		List<String> courseName = courseRepo.getCourseName();
		return courseName;
	}

	@Override
	public List<String> enqStatus() {
		List<String> enqStatus = enqStausRepo.getEnqStatus();
		return enqStatus;
	}

	@Override
	public boolean saveStudEnq(AddStudentEnqForm enqForm) {
		// TODO: create studentEnq entity obj and copy form data into that obj
		StudentEnquiriesEntity studeEnq = new StudentEnquiriesEntity();
		BeanUtils.copyProperties(enqForm, studeEnq);

		// TODO: set userId to StudentEnqEntity obj from session
		Integer userId = (Integer) session.getAttribute("userId");

		// TODO: get userId from userDtlsEntity object
		UserDetailsEntity userEntity = userRepo.findById(userId).get();

		// TODO: call stuEntityRepo entity repo to save data into db
				studeEnq.setUser(userEntity);

		// TODO: save user id into StudentEnqEntity obj
		studEnqRepo.save(studeEnq);

		return true;
	}

	@Override
	public List<StudentEnquiriesEntity> getEnquiries(Integer userId) {
		Optional<UserDetailsEntity> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent()) {
			UserDetailsEntity userDetailsEntity = userEntity.get();
			List<StudentEnquiriesEntity> studEnquries = userDetailsEntity.getStudEnquries();
			return studEnquries;
		}
		return null;
	}


	@Override
	public List<StudentEnquiriesEntity> getFilterEnquiries(EnqSearchCriteriaForm criteria,Integer userId) {
		Optional<UserDetailsEntity> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent()) {
			UserDetailsEntity userDetailsEntity = userEntity.get();
			List<StudentEnquiriesEntity> studEnquries = userDetailsEntity.getStudEnquries();
			if(null!=criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
				 studEnquries = studEnquries.stream()
				.filter(e->e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				studEnquries=studEnquries.stream()
				.filter(e->e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			
			if(null!=criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
				studEnquries=studEnquries.stream()
				.filter(e->e.getEnqStatus().equals(criteria.getEnqStatus()))
				.collect(Collectors.toList());
			}
		
		return studEnquries;
		}
		return null;
	}

	@Override
	public StudentEnquiriesEntity getStudentById(Integer id) {
		return studEnqRepo.findById(id).get();
	}

	@Override
	public boolean updateStudent(StudentEnquiriesEntity studEntity ) {
		studEnqRepo.save(studEntity);
			return true;
	}

	@Override
	public UserDetailsEntity getUserById(Integer userId) {
		return userRepo.findById(userId).get();
	}
		
}	
