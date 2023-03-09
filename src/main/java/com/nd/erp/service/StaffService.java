package com.nd.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.nd.erp.domain.CodeSkill;
import com.nd.erp.domain.Staff;
import com.nd.erp.domain.StaffSkill;
import com.nd.erp.utils.paging.Pagination;

public interface StaffService {
	public Staff getStaffByStaffNo(int staffNo);
	public Staff getStaffByJuminNo(String juminNo);
	public CodeSkill getSkillBySkillName(String skillName);
	
	public List<StaffSkill> getStaffSkillByStaffNo(int staffNo);

	public List<Map<String,Object>> getAllStaff(Map<String, Object> map);
	public List<Map<String,Object>> getStaff(Map<String, Object> map);
	public List<Map<String, Object>> paginationStaff(Map<String, Object> map);
	
	public int addSkill(CodeSkill codeSkill);
	public int addStaff(Map<String, Object> map);
	public int addStaffSkill(Map<String, Object> map);
	
	public int fixStaff(Map<String, Object> map);
	
	public int delStaff(int staffNo);
	public int delStaffSkill(int staffNo);
	
	public int staffAllCount();
	public int staffCount(Map<String, Object> map);
}
