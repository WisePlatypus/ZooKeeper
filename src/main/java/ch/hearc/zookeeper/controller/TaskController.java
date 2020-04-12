package ch.hearc.zookeeper.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.zookeeper.entity.Task;
import ch.hearc.zookeeper.entity.TaskRepository;
import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class TaskController 
{	
	private static String keeperRole = "keeper";	// name of the role for the ones who are concern by tasks
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@PostMapping("/tasks/create")
	public String create(Model model) 
	{
		List<UserRole> roles = userRoleRepository.findAll();
		List<User> allUsers = userRepository.findAll();
		
		List<User> agents = new ArrayList<User>();
		
		long keeperRoleId = -1;
		
		for(UserRole role : roles)
		{
			if(role.getName().equals(keeperRole))
			{
				keeperRoleId = role.getId();
				break;
			}
		}
		
		if(keeperRoleId != -1)
		{
			for(User user : allUsers)
			{
				if(user.getRoles_Id() == keeperRoleId)
				{
					agents.add(user);
				}
			}
		}
		
		model.addAttribute("task", new Task());
		model.addAttribute("users", agents);
		return "task/create";
	}
	
	@GetMapping("/tasks")
	public String users(Model model)
	{		
		model.addAttribute("tasks", taskRepository.findAll());
		
		return "/task/tasks";
	}
}
