package ch.hearc.zookeeper.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class UserController
{
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/users/create")
	public String create(Model model) 
	{
		model.addAttribute("roles", userRoleRepository.findAll());
		model.addAttribute("user", new User());
		return "user/create";
	}

	@PostMapping("/users/insert")
	public String insertPerson(@ModelAttribute User user, Model model)
	{
		userRepository.save(user);
		return "/user/login";
	}
}
