package model.entites;

public class Symptons {
	private String type;
	private String name;
	private String desc;
	
	public Symptons() {
	}

	public Symptons(String type, String name, String desc) {
		super();
		this.type = type;
		this.name = name;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
