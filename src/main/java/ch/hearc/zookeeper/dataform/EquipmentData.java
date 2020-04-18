package ch.hearc.zookeeper.dataform;

import ch.hearc.zookeeper.entity.Equipment;

public class EquipmentData {
	
	private long id;
	private String name;
	private String description;
	private long sector_id;

	public EquipmentData(Equipment equipment) {
		id = equipment.getId();
		name = equipment.getName();
		description = equipment.getDescription();
		sector_id = equipment.getSector().getId();
	}

	public EquipmentData() {
		name = "";
		description = "";
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSector_id() {
		return sector_id;
	}

	public void setSector_id(long id_sector) {
		this.sector_id = id_sector;
	}

}
