package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.dataform.CommandData;
import ch.hearc.zookeeper.dataform.TaskData;
import ch.hearc.zookeeper.entity.Command;
import ch.hearc.zookeeper.entity.Stock;
import ch.hearc.zookeeper.entity.Task;

@SpringBootTest
public class CommandTest
{
	@Test
	public void defaultConstructor()
	{
		Command command = new Command();
		
		assertTrue(command.getQuantity() == 0);
		assertTrue(command.getCommandDate() == null);
		assertTrue(command.getEquipement_id() == 0);
		assertTrue(!command.isValidated());
	}

	@Test
	public void setQuantity()
	{
		Command command = new Command();
		
		command.setQuantity(10);
		assertTrue(command.getQuantity() == 10);
	}	
	
	@Test
	public void setEquipment_Id()
	{
		Command command = new Command();
		
		command.setEquipement_id(1);
		assertTrue(command.getEquipement_id() == 1);
	}	
	
	@Test
	public void setValidate()
	{
		Command command = new Command();
		
		command.setValidated(true);
		assertTrue(command.isValidated());
	}
	
	@Test
	public void setCommandDate()
	{
		Command command = new Command();
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, 0); // January = 0, February = 1, ... 
		cal.set(Calendar.DATE, 16);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 1);
		
		command.setCommandDate(new Date(cal.getTimeInMillis()));
		assertTrue(command.getCommandDate().toString().equals("2020-01-16"));
	}
	
	@Test
	public void dataConstructor()
	{
		CommandData data = new CommandData();
		
		int day = 10;
		int month = 10;
		int year = 2010;
		
		data.setDate(true);
		data.setDay(day);
		data.setMonth(month);
		data.setYear(year);
		data.setEquipment_id(10);
		data.setQuantity(121);
		data.setValidated(true);
		
		Command command = new Command(data);
		
		assertTrue(command.getQuantity() == 121);
		assertTrue(command.getEquipement_id() == 10);
		assertTrue(command.isValidated());
		assertTrue(command.getCommandDate().toString().equals("2010-10-10"));
		
		data.setDate(false);
		
		Command command2 = new Command(data);
		
		assertTrue(command2.getCommandDate() != null);
	}
}
