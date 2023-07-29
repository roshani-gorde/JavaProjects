package com.ashokit.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ashokit.binding.AddStudentEnqForm;
import com.ashokit.binding.DashboardResponseForm;
import com.ashokit.binding.EnqSearchCriteriaForm;
import com.ashokit.entity.StudentEnquiriesEntity;
import com.ashokit.entity.UserDetailsEntity;
import com.ashokit.service.EnquiryMgmtService;

@Controller
public class EnquiryMgmtController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryMgmtService enqService;

	@GetMapping("/logout")
	public String logout(Model model) {
		session.invalidate();
		return "index";
	}

	
	@GetMapping("/dashboard")
	public String loadDashboardPage(Model model) {
		Integer userId= (Integer)session.getAttribute("userId");
		DashboardResponseForm dashBoardData = enqService.getDashBoardData(userId);
		model.addAttribute("dashBoardData", dashBoardData);
		
		return "dashboard";
	}
	
	public String init(Model model) {
		// create addEnqStatus form binding obj
		AddStudentEnqForm studEnqForm=new AddStudentEnqForm();
		// get course names from dropdown
		List<String> courseNames = enqService.courseNames();
		// get enqStatus from dropdown
		List<String> enqStatus = enqService.enqStatus();
		model.addAttribute("courseNames", courseNames);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("studEnqForm", studEnqForm);
		return "add-enquiry";
		
	}
	

	@GetMapping("/add-enquiry")
	public String loadAddStudEnqPage(Model model) {
		init(model);
		return "add-enquiry";
	}
	
	@PostMapping("/add-enquiry")
	public String handleSaveStudEnqFun(@ModelAttribute("studEnqForm") AddStudentEnqForm form, Model model) {
		init(model);
		boolean saveStudEnq = enqService.saveStudEnq(form);
		if(saveStudEnq) {
			model.addAttribute("succMsg", "Enquiry saved sucessfully");
		}else {
			model.addAttribute("errMsg", "Enquiry not saved");
		}
		return "add-enquiry";
		
	}
	
	
	
	/*public String searchInit(Model model) {
		EnqSearchCriteriaForm eqnSearch=new EnqSearchCriteriaForm();
		List<String> courseNames = enqService.courseNames();
		List<String> enqStatus = enqService.enqStatus();
		model.addAttribute("courseNames", courseNames);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("eqnSearch", eqnSearch);
		return "view-enquiries";
		
	}*/
	
	@GetMapping("/view-enquiries")
	public String getEnquiries(Model model) {
		init(model);
		Integer userId = (Integer)session.getAttribute("userId");
		List<StudentEnquiriesEntity> enquiries = enqService.getEnquiries(userId);
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilterEnq(@RequestParam String cname, 
							   @RequestParam String estatus,
							   @RequestParam String cmode, 
							   Model model) {
		
		EnqSearchCriteriaForm enqSearchCriteria=new EnqSearchCriteriaForm();
		enqSearchCriteria.setCourseName(cname);
		enqSearchCriteria.setEnqStatus(estatus);
		enqSearchCriteria.setClassMode(cmode);
		System.out.println(enqSearchCriteria);
		Integer userId = (Integer)session.getAttribute("userId");
		List<StudentEnquiriesEntity> filterEnquiries = enqService.getFilterEnquiries(enqSearchCriteria, userId);
		model.addAttribute("filterEnquiries", filterEnquiries);
		return "filter-enq";
	}

	@GetMapping("/editEnq")
	public String loadEditEnqPage(@RequestParam("enqId") Integer id, Model model) {
		StudentEnquiriesEntity student = enqService.getStudentById(id);
		model.addAttribute("student", student);
		return "editStudEnquiry";
	}
	
	@PostMapping("/editEnq")
	public String updateStudEnq(@RequestParam("enqId") Integer id,
								@ModelAttribute("student") StudentEnquiriesEntity studEntity,
								Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		UserDetailsEntity user = enqService.getUserById(userId);
		studEntity.setUser(user);
		
		StudentEnquiriesEntity existingStud = enqService.getStudentById(id);
		existingStud.setCourseName(studEntity.getCourseName());
		existingStud.setStudName(studEntity.getStudName());
		existingStud.setContactNo(studEntity.getContactNo());
		//existingStud.setCreatedDate(studEntity.getCreatedDate());
		existingStud.setEnqStatus(studEntity.getEnqStatus());
		existingStud.setClassMode(studEntity.getClassMode());
		existingStud.setUser(studEntity.getUser());
		
		boolean status = enqService.updateStudent(existingStud);
		if(status) {
			return "redirect:/view-enquiries";
		
		}else {
			model.addAttribute("errMsg", "student not updated");
		}
		return "editStudEnquiry";
		
	}

}
