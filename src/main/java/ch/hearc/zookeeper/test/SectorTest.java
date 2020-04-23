package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.dataform.SectorData;
import ch.hearc.zookeeper.entity.Sector;

@SpringBootTest
public class SectorTest 
{
	@Test
	public void defaultConstructor()
	{
		Sector sector = new Sector();
		assertTrue(sector.getName() == null);
	}
	
	@Test
	public void setName()
	{
		Sector sector = new Sector();
		
		sector.setName("SectorTest");
		assertTrue(sector.getName().equals("SectorTest"));
	}	
	
	@Test
	public void setData()
	{		
		Sector sector = new Sector();
		
		SectorData data = new SectorData();
		
		data.setName("SectorTest");
		data.setId(0);
		
		sector.setData(data);
		
		assertTrue(sector.getName().equals("SectorTest"));
		assertTrue(sector.getId() == 0);
	}	
}
