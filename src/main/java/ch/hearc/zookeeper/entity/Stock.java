package ch.hearc.zookeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
	
	@OneToOne
    @JoinColumn
	@MapsId
    private Equipment equipment;

	public Stock(@Valid StockData stockData) {
		setData(stockData);
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public long getId() 
	{
		return equipment_id;
	}

	public void setId(long id) 
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
		equipment_id = stockData.getId();
		quantity = stockData.getQuantity();
	}
}
