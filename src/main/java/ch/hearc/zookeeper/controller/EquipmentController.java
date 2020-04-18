package ch.hearc.zookeeper.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import ch.hearc.zookeeper.dataform.EquipmentData;
import ch.hearc.zookeeper.entity.Equipment;
import ch.hearc.zookeeper.entity.EquipmentRepository;

import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class EquipmentController 
{	
	private static String keeperRole = "keeper";	// name of the role for the ones who are concern by equipment
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EquipmentRepository equipmentRepository;
	
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
	 
	@PostMapping("/equipment/create")
	public String create(Model model) 
	{		
		model.addAttribute("equipmentData", new EquipmentData());
		model.addAttribute("users", getAgents());
		
		return "equipment/create";
	}
	
	@RequestMapping(value = "/equipment/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("equipmentData") EquipmentData equipmentData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Equipment equipment = new Equipment(equipmentData);
			
			equipmentRepository.save(equipment);
	    }
		
		return "redirect:/equipment";
	}
	
	@GetMapping("/equipment")
	public String users(Model model)
	{		
		model.addAttribute("equipments", equipmentRepository.findAll());
		
		return "/equipment/equipment";
	}
	
	@PostMapping("/equipment/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    Equipment equipment = equipmentRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid equipment Id:" + id));

	    equipmentRepository.delete(equipment);
	    
	    //return users(model);
	    
	    return "redirect:/equipment";
	}
	
	@PostMapping("/equipment/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
	    Equipment equipment = equipmentRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid equipment Id:" + id));
	    
	    model.addAttribute("equipmentData", new EquipmentData(equipment));
	    model.addAttribute("users", getAgents());
	    
	    return "/equipment/edit";
	}
	
	@PostMapping("/equipment/update")
	public String update(Model model, @Valid @ModelAttribute("equipmentData") EquipmentData equipmentData, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentData.getId());
		
		if(equipmentOpt.isPresent())
		{
			Equipment equipment = equipmentOpt.get();
			equipment.setData(equipmentData);
			
			equipmentRepository.save(equipment);
		}
		
		return "redirect:/equipment";
	}
}
