package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.entity.User;

@SpringBootTest
public class UserTest 
{
	@Test
	public void defaultConstructor()
	{
		User user = new User();
		assertTrue(user.getName() == null);
		assertTrue(user.getPassword() == null);
		assertTrue(user.getRoles_Id() == 0);
	}
	
	@Test
	public void setName()
	{
		User user = new User();
		
		user.setName("UserTest");
		assertTrue(user.getName().equals("UserTest"));
	}	
	
	@Test
	public void setPassword()
	{
		User user = new User();
		
		user.setPassword("UserTest");
		assertTrue(user.getPassword().equals("UserTest"));
	}
	
	@Test
	public void setRoles_Id()
	{
		User user = new User();
		
		user.setRoles_Id(1);
		assertTrue(user.getRoles_Id() == 1);
	}	
}
