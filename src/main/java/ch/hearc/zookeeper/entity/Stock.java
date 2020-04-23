package ch.hearc.zookeeper.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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

import org.springframework.beans.factory.annotation.Autowired;

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
