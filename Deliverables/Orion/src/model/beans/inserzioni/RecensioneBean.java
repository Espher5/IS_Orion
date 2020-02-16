package model.beans.inserzioni;

import java.util.Date;

public class RecensioneBean {
	private long idRecensione, idCliente, idInserzione;
	private int punteggio;
	private String titolo, contenuto;
	private Date dataPubblicazione;
	
	public long getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(long idRecensione) {
		this.idRecensione = idRecensione;
	}
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
	public long getIdInserzione() {
		return idInserzione;
	}
	public void setIdInserzione(long idInserzione) {
		this.idInserzione = idInserzione;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
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