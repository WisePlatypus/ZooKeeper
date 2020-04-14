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

import org.springframework.format.annotation.DateTimeFormat;

import ch.hearc.zookeeper.dataform.TaskData;

@Entity
@Table(name="tasks")
public class Task 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private boolean executed;
	
	@Column
	@DateTimeFormat (pattern="yyyy-MM-dd") 
	private Date executionDate;
	
	@Column
	private long user_Id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false, insertable = false, updatable = false)
    private User user;
	
	public Task()
	{
		this.executed = false;
	}
	
	public Task(TaskData taskData)
	{
		this.setData(taskData);
	}
	
	public void setData(TaskData taskData)
	{
		this.name = taskData.getName();
		this.description = taskData.getDescription();
		this.executed = taskData.isExecuted();
		this.user_Id = taskData.getUser_Id();
		
		if(taskData.isDate())
		{
			Calendar cal = Calendar.getInstance();
			
			cal.set(Calendar.YEAR, taskData.getYear());
			cal.set(Calendar.MONTH, taskData.getMonth() - 1); // January = 0, February = 1, ... 
			cal.set(Calendar.DATE, taskData.getDay());
			cal.set(Calendar.HOUR, 1);
			cal.set(Calendar.MINUTE, 30);
			cal.set(Calendar.SECOND, 1);
			
			this.executionDate = new Date(cal.getTimeInMillis());
		}
	}
	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}
	
	public long getUser_Id() 
	{
		return user_Id;
	}

	public void setUser_Id(long user_Id) 
	{
		this.user_Id = user_Id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public boolean isExecuted() 
	{
		return executed;
	}

	public void setExecuted(boolean executed) 
	{
		this.executed = executed;
	}

	public Date getExecutionDate() 
	{
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) 
	{
		this.executionDate = executionDate;
	}

	public User getUser() 
	{
		return user;
	}

	public void setUser(User user) 
	{
		this.user = user;
	}
}
