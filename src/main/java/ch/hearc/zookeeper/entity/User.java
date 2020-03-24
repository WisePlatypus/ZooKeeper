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

@Entity
@Table(name="users")
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private UserRole role;
	
	public User()
	{
		
	}
	
	public Integer getId() 
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
	
	public void setId(Integer id) 
	{
		this.id = id;
	}
}
