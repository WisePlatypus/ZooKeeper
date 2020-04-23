package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.dataform.EquipmentData;
import ch.hearc.zookeeper.entity.Equipment;

@SpringBootTest
public class EquipmentTest 
{
	@Test
	public void defaultConstructor()
	{
		Equipment equipment = new Equipment();
		assertTrue(equipment.getName() == null);
		assertTrue(equipment.getDescription() == null);
		assertTrue(equipment.getSector_id() == 0);
	}
	
	@Test
	public void setName()
	{
		Equipment equipment = new Equipment();
		
		equipment.setName("EquipmentTest");
		assertTrue(equipment.getName().equals("EquipmentTest"));
	}
	
	@Test
	public void setDescription()
	{
		Equipment equipment = new Equipment();
		
		equipment.setDescription("EquipmentTest");
		assertTrue(equipment.getDescription().equals("EquipmentTest"));
	}
	
	@Test
	public void setSector_Id()
	{
		Equipment equipment = new Equipment();
		
		equipment.setSector_id(1);
		assertTrue(equipment.getSector_id() == 1);
	}	
	
	@Test
	public void setData()
	{		
		Equipment equipment = new Equipment();
		
		EquipmentData data = new EquipmentData();
		
		data.setName("EquipmentTest");
		data.setId(0);
		data.setDescription("description");
		data.setSector_id(10);
		
		equipment.setData(data);
		
		assertTrue(equipment.getName().equals("EquipmentTest"));
		assertTrue(equipment.getDescription().equals("description"));
		assertTrue(equipment.getSector_id() == 10);
		assertTrue(equipment.getId() == 0);
	}	
}
