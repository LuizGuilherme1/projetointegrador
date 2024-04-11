package model.entites;

import java.util.Date;
import java.util.Objects;

public class Pacientes {
	private Integer id;
	private String name;
	private Integer idade;
	private Date birthdate;
	private String gender;
	private String cns;
	private String cpf;
	private String rg;
	private String cep;
	private String endereco;
	private String complemento;
	
	public Pacientes() {
	}
	
	public Pacientes(Integer id, String name, int idade, Date birthdate, String gender, String cns, String cpf, String rg,
			String cep, String endereco, String complemento) {
		super();
		this.id = id;
		this.name = name;
		this.idade = idade;
		this.birthdate = birthdate;
		this.gender = gender;
		this.cns = cns;
		this.cpf = cpf;
		this.rg = rg;
		this.cep = cep;
		this.endereco = endereco;
		this.complemento = complemento;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCns() {
		return cns;
	}
	public void setCns(String cns) {
		this.cns = cns;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereço) {
		this.endereco = endereço;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthdate, cep, cns, complemento, cpf, endereco, gender, id, idade, name, rg);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pacientes other = (Pacientes) obj;
		return Objects.equals(birthdate, other.birthdate) && Objects.equals(cep, other.cep)
				&& Objects.equals(cns, other.cns) && Objects.equals(complemento, other.complemento)
				&& Objects.equals(cpf, other.cpf) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(gender, other.gender) && id == other.id && idade == other.idade
				&& Objects.equals(name, other.name) && Objects.equals(rg, other.rg);
	}
	
	
	
	
}
