package ch.hearc.zookeeper.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hearc.zookeeper.dataform.TaskData;
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
	
	@InitBinder
	public void initBinder (WebDataBinder binder) 
	{
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	public List<User> getAgents()
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
		
		return agents;
	}
	 
	@PostMapping("/tasks/create")
	public String create(Model model) 
	{		
		model.addAttribute("taskData", new TaskData());
		model.addAttribute("users", getAgents());
		
		return "task/create";
	}
	
	@RequestMapping(value = "/tasks/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("taskData") TaskData taskData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Task task = new Task(taskData);
			
			taskRepository.save(task);
	    }
		
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks")
	public String users(Model model)
	{		
		model.addAttribute("tasks", taskRepository.findAll());
		
		return "/task/tasks";
	}
	
	@PostMapping("/tasks/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    Task task = taskRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));

	    taskRepository.delete(task);
	    
	    //return users(model);
	    
	    return "redirect:/tasks";
	}
	
	@PostMapping("/tasks/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
	    Task task = taskRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
	    
	    model.addAttribute("taskData", new TaskData(task));
	    model.addAttribute("users", getAgents());
	    
	    return "/task/edit";
	}
	
	@PostMapping("/tasks/update")
	public String update(Model model, @Valid @ModelAttribute("taskData") TaskData taskData, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		Optional<Task> taskOpt = taskRepository.findById(taskData.getId());
		
		if(taskOpt.isPresent())
		{
			Task task = taskOpt.get();
			task.setData(taskData);
			
			taskRepository.save(task);
		}
		
		return "redirect:/tasks";
	}
}
