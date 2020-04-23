package ch.hearc.zookeeper.dataform;

import ch.hearc.zookeeper.entity.Stock;

public class StockData 
{
	private long equipment_id;
	
	private int quantity;

	public StockData(Stock stock) {
		this.equipment_id = stock.getEquipment_id();
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

	public long getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(long id) {
		this.equipment_id = id;
	}


}
