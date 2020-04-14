package ch.hearc.zookeeper.dataform;

import java.util.Calendar;

import ch.hearc.zookeeper.entity.Task;

public class TaskData {
	
	private long id;
	private String name;
	private String description;
	private long user_Id;
	private boolean executed;

	// Date
	private boolean date;
	private int year;
	private int month;
	private int day;

	public TaskData()
	{
		this.executed = false;
	}
	
	public TaskData(Task task)
	{
		this.id = task.getId();
		this.name = task.getName();
		this.description = task.getDescription();
		this.executed = task.isExecuted();
		this.user_Id = task.getUser_Id();
		
		if(task.getExecutionDate() != null)
		{
			this.date = true;
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(task.getExecutionDate());
			
			this.year = cal.get(Calendar.YEAR);
			this.month = cal.get(Calendar.MONTH);
			this.day = cal.get(Calendar.DATE);
		}
		else
		{
			this.date = false;
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public long getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(long user_Id) {
		this.user_Id = user_Id;
	}
}
