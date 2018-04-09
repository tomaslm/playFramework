package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import akka.util.Crypt;
import play.data.validation.Constraints.Required;

@Entity
public class Usuario extends Model {
	@Id
	@GeneratedValue
	private Long id;

	@Required
	private String nome;

	@Required
	private String email;

	@Required
	private String senha;

	public void encriptaSenhaSalva() {
		setSenha(Crypt.sha1(getSenha()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
