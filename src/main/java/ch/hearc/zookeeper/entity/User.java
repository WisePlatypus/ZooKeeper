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

@Entity
@Table(name="users")
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@Column
	private long roles_Id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roles_Id", nullable = false, insertable = false, updatable = false)
    private UserRole role;
	
	public User()
	{
		
	}
	
	public long getId() 
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public UserRole getRole()
	{
		return role;
	}
	
	public long getRoles_Id() 
	{
		return roles_Id;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public void setRole(UserRole role) 
	{
		this.role = role;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public void setRoles_Id(long roles_Id) 
	{
		this.roles_Id = roles_Id;
	}
}
