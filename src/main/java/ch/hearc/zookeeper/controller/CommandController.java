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

import ch.hearc.zookeeper.dataform.CommandData;
import ch.hearc.zookeeper.entity.CommandRepository;
import ch.hearc.zookeeper.entity.EquipmentRepository;
import ch.hearc.zookeeper.entity.Stock;
import ch.hearc.zookeeper.entity.StockRepository;
import ch.hearc.zookeeper.entity.Command;


@Controller
public class CommandController 
{	

	@Autowired
	EquipmentRepository equipmentRepository;
	
	@Autowired
	CommandRepository commandRepository;
	
	@Autowired
	StockRepository stockRepository;
	

	@InitBinder
	public void initBinder (WebDataBinder binder) 
	{
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	@PostMapping("/command/create")
	public String create(Model model) 
	{		
		model.addAttribute("commandData", new CommandData());
		model.addAttribute("equipments", equipmentRepository.findAll());
		
		return "command/create";
	}
	
	@RequestMapping(value = "/command/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("commandData") CommandData commandData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Command command = new Command(commandData);
			
			command.setValidated(false);
			
			commandRepository.save(command);
	    }
		
		return "redirect:/command";
	}
	
	@GetMapping("/command")
	public String users(Model model)
	{		
		model.addAttribute("commands", commandRepository.findAll());
		
		return "/command/command";
	}
	
	@GetMapping("/command/validate")
	public String validate(Model model)
	{		
		model.addAttribute("commands", commandRepository.findAll());
		
		return "/command/validate";
	}
	
	//confirmValidation
	
	@PostMapping("/command/confirmValidation/{id}")
	public String confirmValidation(@PathVariable("id") long id, Model model)
	{
	    Command command = commandRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid command Id:" + id));

	    command.setValidated(true);
	    
	    commandRepository.save(command);
	    
	    
	    return "redirect:/command";
	}
	
	
	@PostMapping("/command/reception/{id}")
	public String reception(@PathVariable("id") long id, Model model)
	{
	    Command command = commandRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid command Id:" + id));

	    commandRepository.delete(command);
	    
	    Optional<Stock> opStock =  stockRepository.findById(command.getEquipement_id());
	    
	    if(opStock.isPresent())
	    {
	    	Stock stock = opStock.get();
	    	stock.setQuantity(stock.getQuantity() + command.getQuantity());
	    	
	    	stockRepository.save(stock);
	    }
	    else
	    {
	    	Stock stock = new Stock();
	    	stock.setId(command.getEquipement_id());
	    	stock.setQuantity(command.getQuantity());
	    	
	    	stockRepository.save(stock);
	    }

	    return "redirect:/command";
	}
	
	@PostMapping("/command/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model)
	{
	    Command command = commandRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid command Id:" + id));

	    commandRepository.delete(command);
	    
	    //return users(model);
	    
	    return "redirect:/command";
	}
	
	@PostMapping("/command/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) 
	{
	    Command command = commandRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid command Id:" + id));
	    
	    model.addAttribute("commandData", new CommandData(command));
	    model.addAttribute("equipments", equipmentRepository.findAll());
	    
	    return "/command/edit";
	}
	
	@PostMapping("/command/update")
	public String update(Model model, @Valid @ModelAttribute("commandData") CommandData commandData, BindingResult result)
	{
		
		Optional<Command> commandOpt = commandRepository.findById(commandData.getId());
		
		if(commandOpt.isPresent())
		{
			Command command = commandOpt.get();
			if(!command.isValidated())
			{
				command.setData(commandData);				
				commandRepository.save(command);
			}			
		}
		
		return "redirect:/command";
	}
}
