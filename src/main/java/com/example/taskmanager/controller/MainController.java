package com.example.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.config.CustomAuthenticationManager;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.service.TmUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private TmUserService userService;

	@Autowired
	private CustomAuthenticationManager authenticationManager;

	@Autowired
	public MainController(TmUserService userService) {
		this.userService = userService;
	}

	 @RequestMapping("/")
	 public String index() {
	  return "redirect:/login";
	  }

	 @RequestMapping("/login")
	    public String loginPage() {
	        return "login.html";
	    }

	
	 @PostMapping("/userLogin")	  
	 @ResponseBody public String userLogin(@RequestBody TmUser user,
	 HttpServletRequest request) {
		 
		 List<GrantedAuthority> grantedAuths = new ArrayList<>();
	        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
	        
		 Authentication authentication = authenticationManager
				 			.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),grantedAuths));
		 
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 TmUser tmUser = userService.getUserByEmail(user.getEmail());
		 if (tmUser != null) { 
				 HttpSession session =request.getSession(true); 
				 session.setAttribute("userDetails",tmUser);
				 return "Successful Login";
		 }
			  
		else {
			return null;
		}
	 }
	 

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "Successful Logout";
	}

	@RequestMapping("/dashboard")
	public String dashboard(Task task, TmUser tmuser, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		TmUser user = (TmUser) session.getAttribute("userDetails");
		Integer userId = user.getId();
		String userName = user.getName();
		String userEmail = user.getEmail();

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userEmail", userEmail);
		return "dashboard";
	}

}
