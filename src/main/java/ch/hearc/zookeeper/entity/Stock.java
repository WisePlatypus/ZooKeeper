package ch.hearc.zookeeper.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import ch.hearc.zookeeper.dataform.StockData;

@Entity
@Table(name="stocks")
public class Stock 
{	
	@Id
	@Column
	private long equipment_id;
	
	@Column
	private int quantity;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

	public Stock(@Valid StockData stockData) {
		setData(stockData);
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public Stock(String quantity, String equname, String sectName, String id) 
	{
		this.equipment_id=Integer.parseInt(id);
		this.quantity=Integer.parseInt(quantity);
		this.equipment = new Equipment(equname, sectName);
	}

	public long getEquipment_id() 
	{
		return equipment_id;
	}

	public void setEquipment_id(long id) 
	{
		this.equipment_id = id;
	}

	public int getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}

	public Equipment getEquipment() 
	{
		return equipment;
	}

	public void setEquipment(Equipment equipment) 
	{
		this.equipment = equipment;
	}

	public void setData(@Valid StockData stockData) {
		equipment_id = stockData.getEquipment_id();
		quantity = stockData.getQuantity();
	}
}
