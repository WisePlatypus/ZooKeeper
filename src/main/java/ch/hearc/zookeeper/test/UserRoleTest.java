package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.entity.UserRole;

@SpringBootTest
public class UserRoleTest 
{
	@Test
	public void defaultConstructor()
	{
		UserRole role = new UserRole();
		assertTrue(role.getName() == null);
	}
	
	@Test
	public void setName()
	{
		UserRole role = new UserRole();
		
		role.setName("UserRoleTest");
		assertTrue(role.getName().equals("UserRoleTest"));
	}	
}
