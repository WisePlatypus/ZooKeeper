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

import ch.hearc.zookeeper.dataform.StockData;
import ch.hearc.zookeeper.entity.Stock;
import ch.hearc.zookeeper.entity.StockRepository;

import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class StockController 
{	
	private static String keeperRole = "keeper";	// name of the role for the ones who are concern by stock
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StockRepository stockRepository;
	
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
	 
	
	@RequestMapping(value = "/stock/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("stockData") StockData stockData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Stock stock = new Stock(stockData);
			
			stockRepository.save(stock);
	    }
		
		return "redirect:/stock";
	}
	
	@GetMapping("/stock")
	public String users(Model model)
	{		
		model.addAttribute("stock", stockRepository.findAll());
		
		return "/stock/stock";
	}
	
	@PostMapping("/stock/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    Stock stock = stockRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid stock Id:" + id));

	    stockRepository.delete(stock);
	    
	    //return users(model);
	    
	    return "redirect:/stock";
	}
	
	@PostMapping("/stock/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
	    Stock stock = stockRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid stock Id:" + id));
	    
	    model.addAttribute("stockData", new StockData(stock));
	    model.addAttribute("users", getAgents());
	    
	    return "/stock/edit";
	}
	
	@PostMapping("/stock/update")
	public String update(Model model, @Valid @ModelAttribute("stockData") StockData stockData, BindingResult result)
	{
//		if(result.hasErrors()){
//	        //error handling  
//	        ....
//	    }else {
//	        //or calling the repository to save the newProduct
//	        productService.save(newProduct);
//	        ....
//	    }
		
		Optional<Stock> stockOpt = stockRepository.findById(stockData.getId());
		
		if(stockOpt.isPresent())
		{
			Stock stock = stockOpt.get();
			stock.setData(stockData);
			
			stockRepository.save(stock);
		}
		
		return "redirect:/stock";
	}
}
