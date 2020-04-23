package ch.hearc.zookeeper.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
import ch.hearc.zookeeper.dataform.StockData;
import ch.hearc.zookeeper.entity.EquipmentRepository;
import ch.hearc.zookeeper.entity.QuerySearchData;
import ch.hearc.zookeeper.entity.Stock;
import ch.hearc.zookeeper.entity.StockRepository;

import ch.hearc.zookeeper.entity.User;
import ch.hearc.zookeeper.entity.UserRepository;
import ch.hearc.zookeeper.entity.UserRole;
import ch.hearc.zookeeper.entity.UserRoleRepository;

@Controller
public class StockController 
{	
	@Autowired
	EquipmentRepository equipmentRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	EntityManager em;
	
	
	@InitBinder
	public void initBinder (WebDataBinder binder) 
	{
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	 
	
	@RequestMapping(value = "/stock/insert", method = RequestMethod.POST)
	public String insert(Model model, @Valid @ModelAttribute("stockData") StockData stockData, BindingResult result)
	{
		if(!result.hasErrors())
		{
			Stock stock = new Stock(stockData);
			/*Query q = em.createNativeQuery("INSERT INTO STOCKS (equipment_id, quantity) VALUES (" 
					+ stock.getEquipment_id() + ", " 
					+ stock.getQuantity() + ");");*/
			System.out.println("******************************************");
			System.out.println("INSERT INTO stocks (equipment_id, quantity) VALUES (" 
					+ stock.getEquipment_id() + ", " 
					+ stock.getQuantity() + ");");
			//q.executeUpdate();
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
	    
	    
	    return "redirect:/stock";
	}
	
	@PostMapping("/stock/create")
	public String create(Model model) 
	{		
		model.addAttribute("stockData", new CommandData());
		model.addAttribute("equipments", equipmentRepository.findAll());
		
		return "/stock/create";
	}
	
	
	@GetMapping("/stock/search")
	public String search(Model model) 
	{
		model.addAttribute("querySearchData", new QuerySearchData());
		
	    return "/stock/search";
	}
	
	@RequestMapping(value = "/stock/find", method = RequestMethod.POST)
	public String find(Model model, @Valid @ModelAttribute("equipmentName") QuerySearchData queryName, BindingResult result)
	{
		Query q = em.createNativeQuery("SELECT st.quantity as squantity, e.name as ename, se.name as sename, st.equipment_id as id\n" + 
				"FROM stocks AS st, equipments AS e, sectors as se\n" + 
				"WHERE st.equipment_id=e.id AND e.sector_id = se.id AND e.name=?;");
		q.setParameter(1, queryName.getSearchData());
		
		
		List<Object[]> trucs = q.getResultList();
		
		List<Stock> stocks = new ArrayList<Stock>();
		
		System.out.println("***********************************");
		for(Object[] truc : trucs)
		{
			stocks.add(new Stock(""+truc[0],""+truc[1], ""+truc[2], ""+truc[3]));
			System.out.println("yay:" + truc[0] + truc[1]);
		}
		//Object[] queryRes = [Object[]) 
		
		//System.out.println(queryRes[0] + queryRes[1] + queryRes[2], queryRes[3]);
		
		model.addAttribute("stock", stocks);
		
		return "/stock/find";
	}
}
