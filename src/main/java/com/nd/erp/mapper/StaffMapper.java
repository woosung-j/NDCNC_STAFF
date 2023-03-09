package com.nd.erp.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.nd.erp.domain.CodeSkill;
import com.nd.erp.domain.Staff;
import com.nd.erp.domain.StaffSkill;
import com.nd.erp.utils.paging.Pagination;

public interface StaffMapper {
	public CodeSkill selectTest();
	
	public Staff selectStaffByStaffNo(int staffNo);
	public Staff selectStaffByJuminNo(String juminNo);
	public CodeSkill selectSkillBySkillName(String skillName);
	
	public List<StaffSkill> selectStaffSkillByStaffNo(int staffNo);
	
	public List<Map<String, Object>> selectAllStaff(Map<String, Object> map);
	public List<Map<String, Object>> selectStaff(Map<String, Object> map);
	public List<Map<String, Object>> paginationStaff(Map<String, Object> map);
	
	public int insertSkill(CodeSkill codeSkill);
	public int insertStaff(Map<String, Object> map);
	public int insertStaffSkill(Map<String, Object> map);
	
	public int updateStaff(Map<String, Object> map);
	
	public int deleteStaff(int staffNo);
	public int deleteStaffSkill(int staffNo);
	
	public int staffAllCount();
	public int staffCount(Map<String, Object> map);
}
