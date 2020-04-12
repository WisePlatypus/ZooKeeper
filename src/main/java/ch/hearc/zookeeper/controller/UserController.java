package ch.hearc.zookeeper.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import ch.hearc.zookeeper.dataform.PasswordChangeData;
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

	@PostMapping("/users/create")
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
	public String insert(Model model, @Valid @ModelAttribute("user") User user, BindingResult result)
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
		
		return "redirect:/users";
		
		//return users(model);
	}
	
	@GetMapping("/user")
	public String user(Model model)
	{
		Collection<SimpleGrantedAuthority> authorities = 
				(Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		model.addAttribute("authorities", authorities);
	
		return "/user/user";
	}
	
	@GetMapping("/users")
	public String users(Model model)
	{		
		model.addAttribute("users", userRepository.findAll());
		
		return "/user/users";
	}
	
	@PostMapping("/users/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) {
	    User user = userRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	    model.addAttribute("user", user);   
	    model.addAttribute("roles", userRoleRepository.findAll());
	    
	    return "/user/edit";
	}
	
	@PostMapping("/users/updatePasswordForce")
	public String updatePasswordForce(@Valid @ModelAttribute("user") User user, Model model) {
	    User currentUser = userRepository.findById(user.getId())
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getId()));

	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(currentUser);
		
		return "redirect:/users";
	}
	
	@PostMapping("/users/updatePassword/{id}")
	public String updatePassword(@PathVariable("id") long userId, @ModelAttribute("passwordData") PasswordChangeData passwordData, Model model) {
	    
		User currentUser = userRepository.findById(userId)
			      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(currentUser.getName().equals(currentUserName))
		{
			currentUser.setPassword(passwordEncoder.encode(passwordData.getNewPassword()));
	    	userRepository.save(currentUser);
			
			return "redirect:/user";
		}
	    
		return editPassword(currentUser.getName(), model);
	}
	
	@PostMapping("/users/editPasswordForce/{id}")
	public String editPasswordForce(@PathVariable("id") long id, Model model) {
	    User user = userRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	    model.addAttribute("user", user);   
		
		return "/user/editPasswordForce";
	}
	
	@PostMapping("/users/editPassword/{userName}")
	public String editPassword(@PathVariable("userName") String userName, Model model)
	{
		List<User> users = userRepository.findAll();
		
		for(User userTemp : users)
		{
			if(userTemp.getName().equals(userName))
			{
				model.addAttribute("userId", userTemp.getId());
				model.addAttribute("passwordData", new PasswordChangeData());
				
				return "/user/editPassword";
			}
		}
		
		return "/user/user";
	}
	
	@PostMapping("/users/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    User user = userRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

	    userRepository.delete(user);
	    
	    //return users(model);
	    
	    return "redirect:/users";
	}
	
	@PostMapping("/users/update")
	public String update(Model model, @Valid @ModelAttribute("user") User user, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		Optional<User> currentUserOpt = userRepository.findById(user.getId());
		
		if(currentUserOpt.isPresent())
		{
			User currentUser = currentUserOpt.get();
			
			currentUser.setName(user.getName());
			currentUser.setRoles_Id(user.getRoles_Id());
			userRepository.save(currentUser);
		}
		
		return "redirect:/users";
		
		//return users(model);
	}
}
