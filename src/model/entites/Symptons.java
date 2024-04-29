package model.entites;

public class Symptons {
	private Long id;
	private String transtorno;
	private String cid;
	private String sintomas_biologicos;
	private String consequencias_sociais;
	private String caracteristicas;
	
	public Symptons() {
	}
	
	public Symptons(Long id, String transtorno, String cid, String sintomas_biologicos, String consequencias_sociais,
			String caracteristicas) {
		super();
		this.id = id;
		this.transtorno = transtorno;
		this.cid = cid;
		this.sintomas_biologicos = sintomas_biologicos;
		this.consequencias_sociais = consequencias_sociais;
		this.caracteristicas = caracteristicas;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTranstorno() {
		return transtorno;
	}
	public void setTranstorno(String transtorno) {
		this.transtorno = transtorno;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getSintomas_biologicos() {
		return sintomas_biologicos;
	}
	public void setSintomas_biologicos(String sintomas_biologicos) {
		this.sintomas_biologicos = sintomas_biologicos;
	}
	public String getConsequencias_sociais() {
		return consequencias_sociais;
	}
	public void setConsequencias_sociais(String consequencias_sociais) {
		this.consequencias_sociais = consequencias_sociais;
	}
	public String getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	@Override
	public String toString() {
		return "Symptons [id=" + id + ", transtorno=" + transtorno + ", cid=" + cid + ", sintomas_biologicos="
				+ sintomas_biologicos + ", consequencias_sociais=" + consequencias_sociais + ", caracteristicas="
				+ caracteristicas + "]";
	}
	
	
}
