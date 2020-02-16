package model.beans.prenotazioni;

import java.util.Date;

public class MetodoPagamentoBean {
	private String numeroCarta, nomeTitolare, cognomeTitolare;
	private long idUtente;
	private Date dataScadenza;
	private boolean preferito;
	
	public String getNumeroCarta() {
		return numeroCarta;
	}
	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}
	public String getNomeTitolare() {
		return nomeTitolare;
	}
	public void setNomeTitolare(String nomeTitolare) {
		this.nomeTitolare = nomeTitolare;
	}
	public String getCognomeTitolare() {
		return cognomeTitolare;
	}
	public void setCognomeTitolare(String cognomeTitolare) {
		this.cognomeTitolare = cognomeTitolare;
	}
	public long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public boolean isPreferito() {
		return this.preferito;
	}
	public void setPreferito(boolean preferito) {
		this.preferito = preferito;
	}
}