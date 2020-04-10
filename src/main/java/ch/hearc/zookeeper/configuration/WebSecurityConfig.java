package ch.hearc.zookeeper.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http        
            .authorizeRequests()
                .antMatchers("/", "/home", "/users").permitAll()
                .antMatchers("/users/create").hasAuthority("administrator")
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    
    @Autowired
    DataSource dataSource;
   
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception 
    {
      auth.jdbcAuthentication().dataSource(dataSource)
          .usersByUsernameQuery("select name, password, enabled from users where name=?")
          .authoritiesByUsernameQuery("SELECT users.name, roles.name FROM users INNER JOIN roles "
          		+ "ON users.roles_Id=roles.id "
          		+ "where users.name=?");
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}
