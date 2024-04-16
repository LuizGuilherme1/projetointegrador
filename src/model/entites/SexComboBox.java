package model.entites;

public class SexComboBox {
	private String desc;
	private String content;
	
	public SexComboBox() {
	}

	public SexComboBox(String desc, String content) {
		super();
		this.desc = desc;
		this.content = content;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SexComboBox [desc=" + desc + ", content=" + content + "]";
	}
	
	
}
