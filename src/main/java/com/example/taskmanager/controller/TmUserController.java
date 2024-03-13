
	package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.taskmanager.model.TmUser;
import com.example.taskmanager.service.TmUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class TmUserController {
	
	@Autowired
	private TmUserService userService;
	
	@GetMapping
    public List<TmUser> getAllUsers() {    	
        return userService.getAllUsers();
    }
	
	@PostMapping("/userLogin")
    @ResponseBody
    public String userLogin(@RequestBody TmUser user, HttpServletRequest request) {
	 TmUser authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (authenticatedUser != null) { 
            HttpSession session = request.getSession();
            session.setAttribute("userDetails", user);
        }
            return "authenticatedUser";
    }
	
	@PostMapping
	    public String createUser(@RequestBody TmUser user) {
		 if (userService.existsByEmail(user.getEmail())) {
	            return "user exists";
	        }
		 else {
		    userService.createUser(user);
	        return "user added";
	        }
	    }
	
	 @GetMapping("/check")
	    public String checkUserExistsByEmail(@RequestParam("email") String email) {
	        TmUser user = userService.getUserByEmail(email);
	        if (user != null) {
	            return "true";
	        } else {
	            return null;
	        }
	    }
	 
	 @PostMapping("/lookup")
	    public List<Integer> lookupCollaboratorIdsByEmails(@RequestBody List<String> emails) {
	        // Call the service method to lookup collaborator IDs by emails
		        return userService.lookupCollaboratorIdsByEmails(emails);
		    }
		
}
