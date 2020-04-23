package ch.hearc.zookeeper.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hearc.zookeeper.dataform.StockData;
import ch.hearc.zookeeper.entity.Stock;

@SpringBootTest
public class StockTest 
{
	@Test
	public void defaultConstructor()
	{
		Stock stock = new Stock();
		assertTrue(stock.getId() == 0);
		assertTrue(stock.getQuantity() == 0);
	}
	
	@Test
	public void setQuantity()
	{
		Stock stock = new Stock();
		
		stock.setQuantity(10);
		assertTrue(stock.getQuantity() == 10);
	}	
	
	@Test
	public void setData()
	{		
		Stock stock = new Stock();
		
		StockData data = new StockData();
		
		data.setQuantity(15);
		data.setId(10);
		
		stock.setData(data);
		
		assertTrue(stock.getId() == 10);
		assertTrue(stock.getQuantity() == 15);
	}	
}
