package ch.hearc.zookeeper.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer 
{

    public void addViewControllers(ViewControllerRegistry registry) 
    {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/users/create").setViewName("user/create");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/users").setViewName("users");
    }

}
