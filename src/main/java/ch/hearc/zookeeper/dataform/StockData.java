package ch.hearc.zookeeper.dataform;

import ch.hearc.zookeeper.entity.Stock;

public class StockData 
{
	private long id;
	
	private int quantity;

	public StockData(Stock stock) {
		this.id = stock.getId();
		this.quantity = stock.getQuantity();
	}

	public StockData() {
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
