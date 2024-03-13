package com.example.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	//@Autowired
	//private CustomAuthenticationManager authenticationManager;

	@Autowired
	public MainController(TmUserService userService) {
		this.userService = userService;
	}

	 @RequestMapping("/")
	 public String index() {
	  return "redirect:/loginPage";
	  }

	@GetMapping("/loginPage")
	public String loginPage(TmUser user, HttpServletRequest request, Model model) {
		model.addAttribute("error", request.getParameter("error"));
		return "login.html";
	}

	/*
	 * @PostMapping("/login") public String login(@RequestBody TmUser
	 * user,HttpServletRequest request) { TmUser authenticatedUser =
	 * userService.authenticateUser(user.getEmail(), user.getPassword()); if
	 * (authenticatedUser != null) { request.getSession().setAttribute("user",
	 * authenticatedUser); return "success"; } else { return "fail"; } }
	 */

	
	 @PostMapping("/login")	  
	 @ResponseBody public String userLogin(@RequestBody TmUser user,
	 HttpServletRequest request) {
		 TmUser authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
		 if (authenticatedUser != null) { 
				 HttpSession session =request.getSession(true); 
				 session.setAttribute("userDetails",authenticatedUser);
			 return "Successful Login"; 
		 } else {
			 return null; 
		 } 
	 }
	 

		/*
		 @PostMapping("/process_login") public String
		 processLogin(@ModelAttribute("user") TmUser user, RedirectAttributes
		 redirectAttributes, HttpSession session) { try { Authentication
		 authentication = SecurityContextHolder.getContext().getAuthentication();
		 authenticationManager.authenticate(authentication);
		 session.setAttribute("username", user.getEmail()); // Store user information
		 in session redirectAttributes.addFlashAttribute("success",
		 "You have been logged in successfully!"); return "redirect:/dashboard"; }
		 catch (AuthenticationException e) {
		 redirectAttributes.addFlashAttribute("error", "Bad Credentials"); return
		 "redirect:/login"; } }
		 
		 @PostMapping("/login") public String userLogin(HttpServletRequest
		 request, @RequestBody TmUser user) { Authentication authentication =
		 SecurityContextHolder.getContext().getAuthentication();
		 
		 if (authentication != null && authentication.isAuthenticated()) { return
		 "Already authenticated"; } else { return "Authentication in progress"; } }
		 */

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
