package ch.hearc.zookeeper.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="commands")
public class Command 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private int quantity;
	
	@Column
	private boolean validated;
	
	@Column
	private Date commandDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private Equipment equipment;

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

	public boolean isValidated() 
	{
		return validated;
	}

	public void setValidated(boolean validated) 
	{
		this.validated = validated;
	}

	public Date getCommandTime() 
	{
		return commandDate;
	}

	public void setCommandTime(Date commandDate) 
	{
		this.commandDate = commandDate;
	}

	public Equipment getEquipment() 
	{
		return equipment;
	}

	public void setEquipment(Equipment equipment) 
	{
		this.equipment = equipment;
	}
}
