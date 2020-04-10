package ch.hearc.zookeeper.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

//	@PostMapping("/users/insert")
//	public String insertPerson(@ModelAttribute User user)
//	{
//		userRepository.save(user);
//		return "/user/login";
//	}
	
//	@PostMapping("/users/insert")
//	public String insertPerson(@Valid User user, 
//			BindingResult bindingResult, 
//			Model model )
//	{
//		userRepository.save(user);
//		return "/user/login";
//	}
	
	@RequestMapping(value = "/users/insert", method = RequestMethod.POST)
	public String create(Model model, @Valid @ModelAttribute("user") User user, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
		return "/user/login";
	}
}
