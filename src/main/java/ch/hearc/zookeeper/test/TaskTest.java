package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.dataform.TaskData;
import ch.hearc.zookeeper.entity.Task;

@SpringBootTest
public class TaskTest 
{
	@Test
	public void defaultConstructor()
	{
		Task task = new Task();
		
		assertTrue(task.getName() == null);
		assertTrue(task.getDescription() == null);
		assertTrue(task.getUser_Id() == 0);
		assertTrue(!task.isExecuted());
		assertTrue(task.getExecutionDate() == null);
	}
	
	@Test
	public void setName()
	{
		Task task = new Task();
		
		task.setName("TaskTest");
		assertTrue(task.getName().equals("TaskTest"));
	}	
	
	@Test
	public void setDescription()
	{
		Task task = new Task();
		
		task.setDescription("description");
		assertTrue(task.getDescription().equals("description"));
	}
	
	@Test
	public void setUser_Id()
	{
		Task task = new Task();
		
		task.setUser_Id(1);
		assertTrue(task.getUser_Id() == 1);
	}	
	
	@Test
	public void setExcutioned()
	{
		Task task = new Task();
		
		task.setExecuted(true);
		assertTrue(task.isExecuted());
	}
	
	@Test
	public void setExecutionDate()
	{
		Task task = new Task();
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 0); // January = 0, February = 1, ... 
		cal.set(Calendar.DATE, 16);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 1);
		
		task.setExecutionDate(new Date(cal.getTimeInMillis()));
		assertTrue(task.getExecutionDate().toString().equals("2020-01-16"));
	}
	
	@Test
	public void dataConstructor()
	{
		TaskData data = new TaskData();
		
		int day = 10;
		int month = 10;
		int year = 2010;
		String name = "task";
		String description = "description";	
		long user_Id = 3;
		
		data.setDate(true);
		data.setDay(day);
		data.setMonth(month);
		data.setYear(year);
		data.setName(name);
		data.setUser_Id(user_Id);
		data.setDescription(description);
		
		Task task = new Task(data);
		
		assertTrue(task.getName() == name);
		assertTrue(task.getDescription() == description);
		assertTrue(task.getUser_Id() == user_Id);
		assertTrue(!task.isExecuted());
		assertTrue(task.getExecutionDate().toString().equals("2010-10-10"));
		
		data.setDate(false);
		data.setExecuted(true);
		
		Task task2 = new Task(data);
		
		assertTrue(task2.isExecuted());
		assertTrue(task2.getExecutionDate() == null);
	}
}
