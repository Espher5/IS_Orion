package model.beans.utenza;

import java.util.Date;

public class UtenteBean {
	private long idUtente;
	private String email, password, nome, cognome;
	private Date dataRegistrazione;
	private boolean stato;
	
	public long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date data_registrazione) {
		this.dataRegistrazione = data_registrazione;
	}
	public boolean getStato() {
		return stato;
	}
	public void setStato(boolean stato) {
		this.stato = stato;
	}
}