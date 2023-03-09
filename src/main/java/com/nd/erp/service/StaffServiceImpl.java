package com.nd.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.nd.erp.domain.CodeSkill;
import com.nd.erp.domain.Staff;
import com.nd.erp.domain.StaffSkill;
import com.nd.erp.mapper.StaffMapper;
import com.nd.erp.utils.paging.Pagination;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired private StaffMapper staffMapper;

	@Override
	public Staff getStaffByStaffNo(int staffNo) {
		return staffMapper.selectStaffByStaffNo(staffNo);
	}

	@Override
	public Staff getStaffByJuminNo(String juminNo) {
		return staffMapper.selectStaffByJuminNo(juminNo);
	}

	@Override
	public CodeSkill getSkillBySkillName(String skillName) {
		return staffMapper.selectSkillBySkillName(skillName);
	}
	
	@Override
	public List<StaffSkill> getStaffSkillByStaffNo(int staffNo) {
		return staffMapper.selectStaffSkillByStaffNo(staffNo);
	}
	
	@Override
	public List<Map<String, Object>> getAllStaff(Map<String, Object> map) {
		return staffMapper.selectAllStaff(map);
	}

	@Override
	public List<Map<String, Object>> getStaff(Map<String, Object> map) {
		return staffMapper.selectStaff(map);
	}

	@Override
	public List<Map<String, Object>> paginationStaff(Map<String, Object> map) {
		return staffMapper.paginationStaff(map);
	}

	@Override
	public int addSkill(CodeSkill codeSkill) {
		return staffMapper.insertSkill(codeSkill);
	}

	@Override
	public int addStaff(Map<String, Object> map) {
		return staffMapper.insertStaff(map);
	}

	@Override
	public int addStaffSkill(Map<String, Object> map) {
		return staffMapper.insertStaffSkill(map);
	}

	@Override
	public int fixStaff(Map<String, Object> map) {
		return staffMapper.updateStaff(map);
	}

	@Override
	public int delStaff(int staffNo) {
		return staffMapper.deleteStaff(staffNo);
	}

	@Override
	public int delStaffSkill(int staffNo) {
		return staffMapper.deleteStaffSkill(staffNo);
	}

	@Override
	public int staffAllCount() {
		return staffMapper.staffAllCount();
	}

	@Override
	public int staffCount(Map<String, Object> map) {
		return staffMapper.staffCount(map);
	}
}
