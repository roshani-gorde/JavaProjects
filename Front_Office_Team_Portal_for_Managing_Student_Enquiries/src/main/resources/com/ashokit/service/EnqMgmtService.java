package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.AddEnqForm;
import com.ashokit.binding.DashboardResponseForm;
import com.ashokit.binding.EnqSearchCriteriaForm;

public interface EnqMgmtService {
	
	public List<String> getCourseNames();

	public List<String> getStatus();

	public String upsertEnq(AddEnqForm addEnq);
	
	public DashboardResponseForm getDashboardData(Integer userId);
	
	public List<AddEnqForm> getEnquiries(Integer userId, EnqSearchCriteriaForm criteria);
	
	public AddEnqForm getEnq(Integer enqId);

}
