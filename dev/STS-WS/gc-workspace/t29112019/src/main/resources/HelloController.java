package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HelloController 
{

	@RequestMapping(value="/hello")
	
	public String SayHello2(@RequestParam(name="name", required=false, defaultValue="MyName"), String name, Model model)
	{
		model.addAttribute("name", name);
		
		return "hello";
	}
	
}
