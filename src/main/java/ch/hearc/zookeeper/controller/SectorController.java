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

import ch.hearc.zookeeper.dataform.SectorData;
import ch.hearc.zookeeper.entity.Sector;
import ch.hearc.zookeeper.entity.SectorRepository;

import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class SectorController 
{	
	private static String keeperRole = "keeper";	// name of the role for the ones who are concern by sector
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SectorRepository sectorRepository;
	
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
	 
	@PostMapping("/sector/create")
	public String create(Model model) 
	{		
		model.addAttribute("sectorData", new SectorData());
		model.addAttribute("users", getAgents());
		
		return "sector/create";
	}
	
	@RequestMapping(value = "/sector/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("sectorData") SectorData sectorData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Sector sector = new Sector(sectorData);
			
			sectorRepository.save(sector);
	    }
		
		return "redirect:/sector";
	}
	
	@GetMapping("/sector")
	public String users(Model model)
	{		
		model.addAttribute("sectors", sectorRepository.findAll());
		
		return "/sector/sector";
	}
	
	@PostMapping("/sector/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    Sector sector = sectorRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid sector Id:" + id));

	    sectorRepository.delete(sector);
	    
	    //return users(model);
	    
	    return "redirect:/sector";
	}
	
	@PostMapping("/sector/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
	    Sector sector = sectorRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid sector Id:" + id));
	    
	    model.addAttribute("sectorData", new SectorData(sector));
	    model.addAttribute("users", getAgents());
	    
	    return "/sector/edit";
	}
	
	@PostMapping("/sector/update")
	public String update(Model model, @Valid @ModelAttribute("sectorData") SectorData sectorData, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		Optional<Sector> sectorOpt = sectorRepository.findById(sectorData.getId());
		
		if(sectorOpt.isPresent())
		{
			Sector sector = sectorOpt.get();
			sector.setData(sectorData);
			
			sectorRepository.save(sector);
		}
		
		return "redirect:/sector";
	}
}
