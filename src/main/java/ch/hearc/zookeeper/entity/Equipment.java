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

import ch.hearc.zookeeper.dataform.EquipmentData;

@Entity
@Table(name="equipments")
public class Equipment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
	private Sector sector;
	
	public Equipment(@Valid EquipmentData equipmentData) {
		// TODO Auto-generated constructor stub
	}

	public Sector getSector() 
	{
		return sector;
	}

	public void setSector(Sector sector) 
	{
		this.sector = sector;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
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

	public void setData(@Valid EquipmentData equipmentData) {
		// TODO Auto-generated method stub
		
	}
}
