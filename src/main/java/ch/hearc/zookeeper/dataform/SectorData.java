package ch.hearc.zookeeper.dataform;

import ch.hearc.zookeeper.entity.Sector;

public class SectorData 
{
	private long id;
	private String name;
	
	
	public SectorData(Sector sector) {
		id = sector.getId();
		name = sector.getName();
	}
	public SectorData() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
