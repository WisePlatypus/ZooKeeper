package ch.hearc.zookeeper.entity;

import java.sql.Date;
import java.util.Calendar;

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

import org.springframework.format.annotation.DateTimeFormat;

import ch.hearc.zookeeper.dataform.CommandData;

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
	@DateTimeFormat (pattern="yyyy-MM-dd") 
	private Date commandDate;
	
	@Column
	private long equipement_id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipement_id", nullable = false, insertable = false, updatable = false)
    private Equipment equipment;

	public Command(@Valid CommandData commandData) {
		setData(commandData);
	}
	
	public Command()
	{
		
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

	public boolean isValidated() 
	{
		return validated;
	}

	public void setValidated(boolean validated) 
	{
		this.validated = validated;
	}

	public Date getCommandDate() 
	{
		return commandDate;
	}

	public void setCommandDate(Date commandDate) 
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

	public void setData(@Valid CommandData commandData) {
		this.setEquipement_id(commandData.getEquipment_id());
		this.quantity = commandData.getQuantity();
		this.validated = commandData.isValidated();
		
		if(commandData.isDate())
		{
			Calendar cal = Calendar.getInstance();
			
			cal.set(Calendar.YEAR, commandData.getYear());
			cal.set(Calendar.MONTH, commandData.getMonth() - 1); // January = 0, February = 1, ... 
			cal.set(Calendar.DATE, commandData.getDay());
			cal.set(Calendar.HOUR, 1);
			cal.set(Calendar.MINUTE, 30);
			cal.set(Calendar.SECOND, 1);
			
			this.commandDate = new Date(cal.getTimeInMillis());
		}
		else
		{
			this.commandDate = new Date(new java.util.Date().getTime());
		}
		
	}

	public long getEquipement_id() {
		return equipement_id;
	}

	public void setEquipement_id(long equipement_id) {
		this.equipement_id = equipement_id;
	}
}
