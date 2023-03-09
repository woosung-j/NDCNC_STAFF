package com.nd.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nd.erp.service.StaffService;

@Controller
@RequestMapping("/")
public class StaffController {
	@Autowired StaffService staffService;
	
	@GetMapping("")
	public String home() {
		return "redirect:/staff_search_form.jsp";
	}
	
	@GetMapping("staff_input_form")
	public String input() {
		return "forward:/staff_updel_form.jsp";
	}

	@GetMapping("staff_search_form")
	public String search() {
		return "redirect:/staff_search_form.jsp";
	}
	
	@GetMapping("staff_updel_form/{staffNo}")
	public String upDel(@PathVariable int staffNo, Model model) {
		model.addAttribute(staffNo);
		
		return "forward:/staff_updel_form.jsp";
	}
}
