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
	private Date executionDate;
	
	@Column
	private long user_Id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false, insertable = false, updatable = false)
    private User user;
	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}
	
	public long getUserId() 
	{
		return user_Id;
	}

	public void setUserId(long user_Id) 
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
