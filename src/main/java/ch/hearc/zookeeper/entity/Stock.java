package ch.hearc.zookeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import ch.hearc.zookeeper.dataform.StockData;

@Entity
@Table(name="stocks")
public class Stock 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private Equipment equipment;

	public Stock(@Valid StockData stockData) {
		// TODO Auto-generated constructor stub
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
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
		// TODO Auto-generated method stub
		
	}
}
