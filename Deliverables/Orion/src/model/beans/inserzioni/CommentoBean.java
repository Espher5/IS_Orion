package model.beans.inserzioni;

import java.util.Date;

public class CommentoBean {
	private long idRecensione;
	private String contenuto;
	private Date dataPubblicazione;
	
	public long getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(long idRecensione) {
		this.idRecensione = idRecensione;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
}