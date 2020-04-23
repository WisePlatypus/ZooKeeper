package ch.hearc.zookeeper.dataform;

import java.sql.Date;
import java.util.Calendar;

import ch.hearc.zookeeper.entity.Command;


public class CommandData 
{
	private long id;
	private int quantity;
	private boolean validated;
	
	// Date
	private boolean date;
	private int year;
	private int month;
	private int day;
		
	private long equipment_id;

	public CommandData(Command command) {
		
		this.id = command.getId();
		this.quantity = command.getQuantity();
		this.validated = command.isValidated();
		this.equipment_id = command.getEquipement_id();
		
		if(command.getCommandDate() != null)
		{
			this.date = true;
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(command.getCommandDate());
			
			this.year = cal.get(Calendar.YEAR);
			this.month = cal.get(Calendar.MONTH);
			this.day = cal.get(Calendar.DATE);
		}
		else
		{
			this.date = false;
		}
	}

	public CommandData() {
		// TODO Auto-generated constructor stub
	}

	public long getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(long equipement_id) {
		this.equipment_id = equipement_id;
	}

	public boolean isDate() {
		return date;
	}

	public void setDate(boolean date) {
		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
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
