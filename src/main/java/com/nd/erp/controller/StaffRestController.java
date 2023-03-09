package com.nd.erp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nd.erp.domain.CodeSkill;
import com.nd.erp.domain.Staff;
import com.nd.erp.domain.StaffSkill;
import com.nd.erp.service.StaffService;
import com.nd.erp.utils.ObjectToInt;
import com.nd.erp.utils.paging.Pagination;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {
	@Autowired StaffService staffService;
	
	/* GET */
	// ��� ��ȣ ������ ��� ������ ��ȯ�Ѵ�.
	@GetMapping("")
	public Staff getStaff(@RequestParam int staffNo) {
		return staffService.getStaffByStaffNo(staffNo);
	}
	
	// ��� ��ȣ ������ ����� ��ų ������ ��ȯ�Ѵ�.
	@GetMapping("/skill")
	public List<StaffSkill> getSkill(@RequestParam int staffNo) {
		return staffService.getStaffSkillByStaffNo(staffNo);
	}
	
	/* POST */
	// �˻� ������ map���� �޾� page ������ �Բ� mapper�� ���� �� �˻� ����� ��ȯ�Ѵ�.
	@PostMapping("")
	public List<Map<String,Object>> getStaff(@RequestBody Map<String, Object> resultMap) {

		int currentPage = ObjectToInt.parseInt(resultMap.get("currentPage"), 1);
		int cntPerPage = ObjectToInt.parseInt(resultMap.get("cntPerPage"), 5);
		int pageSize = ObjectToInt.parseInt(resultMap.get("pageSize"), 5);
		
		int listCnt = staffService.staffCount(resultMap);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		pagination.setTotalRecordCount(listCnt);
		
		resultMap.put("pagination", pagination);

		List<Map<String,Object>> result = staffService.getStaff(resultMap);
		result.add(resultMap);
		
		return result;
	}
	
	// page������ mapper�� �ѱ� �� �������� �´� ��� ��ü ������ ��ȯ�Ѵ�. 
	@PostMapping("/all")
	public List<Map<String,Object>> getAllStaff(@RequestBody Map<String, Object> resultMap) {

		int currentPage = ObjectToInt.parseInt(resultMap.get("currentPage"), 1);
		int cntPerPage = ObjectToInt.parseInt(resultMap.get("cntPerPage"), 5);
		int pageSize = ObjectToInt.parseInt(resultMap.get("pageSize"), 5);
		
		int listCnt = staffService.staffAllCount();
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		pagination.setTotalRecordCount(listCnt);
		
		resultMap.put("pagination", pagination);
		
		List<Map<String, Object>> result = staffService.getAllStaff(resultMap);
		result.add(resultMap);
		
		return result;
	}
	
	// ��� ������ �߰��Ѵ�.
	@PostMapping("/add")
	public int addStaff(@RequestBody Map<String, Object> resultMap) {
		Staff isStaff = staffService.getStaffByJuminNo(resultMap.get("juminNo").toString());
		int cnt = -1;
		
		if(isStaff == null) {
			cnt = staffService.addStaff(resultMap);
			
			if(cnt > 0) {
				ArrayList skills = (ArrayList)resultMap.get("skill");
				List<Integer> skillNo = new ArrayList<Integer>();
				
				for(int i = 0; i < skills.size(); i++) {
					CodeSkill cs = staffService.getSkillBySkillName(skills.get(i).toString());
	
					if(cs == null) {
						cs = new CodeSkill(0, skills.get(i).toString());
						staffService.addSkill(cs);
					}
					skillNo.add(cs.getSkillCode());
				}
				resultMap.put("skillNo", skillNo);
				cnt = staffService.addStaffSkill(resultMap);
			}
			else cnt = 0;
		}
		return cnt;
	}

	/* PUT */
	// ��� ������ �����Ѵ�.
	@PutMapping("")
	public int fixStaff(@RequestBody Map<String, Object> resultMap) {
		int cnt = -1;
		
		cnt = staffService.fixStaff(resultMap);
		
		if(cnt > 0) {
			cnt = staffService.delStaffSkill(Integer.parseInt(resultMap.get("staffNo").toString()));
			
			ArrayList skills = (ArrayList)resultMap.get("skill");
			List<Integer> skillNo = new ArrayList<Integer>();
			
			for(int i = 0; i < skills.size(); i++) {
				CodeSkill cs = staffService.getSkillBySkillName(skills.get(i).toString());

				if(cs == null) {
					cs = new CodeSkill(0, skills.get(i).toString());
					staffService.addSkill(cs);
				}
				skillNo.add(cs.getSkillCode());
			}
			resultMap.put("skillNo", skillNo);
			cnt = staffService.addStaffSkill(resultMap);
		}
		return cnt;
	}

	/* DELETE */
	// ��� ������ �����Ѵ�.
	@DeleteMapping("")
	public int delStaff(@RequestParam int staffNo) {
		return staffService.delStaff(staffNo);
	}
}
