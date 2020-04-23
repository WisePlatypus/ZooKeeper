package ch.hearc.zookeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

import ch.hearc.zookeeper.dataform.SectorData;

@Entity
@Table(name="sectors")
public class Sector 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;

	public Sector(@Valid SectorData sectorData) {
		name = sectorData.getName();
	}
	
	public Sector()
	{
		
	}

	public Sector(String sectName) {
		this.name = sectName;
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

	public void setData(@Valid SectorData sectorData) {
		id = sectorData.getId();
		name = sectorData.getName();
	}
}
