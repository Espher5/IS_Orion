package model.beans.prenotazioni;

public class PrenotazioneArchiviataBean {
	private long idPrenotazione;
	private String stato, regione, città, cap, indirizzo;
	private double prezzoGiornaliero;
	
	public long getIdPrenotazione() {
		return idPrenotazione;
	}
	public void setIDPrenotazione(long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getCittà() {
		return città;
	}
	public void setCittà(String città) {
		this.città = città;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public double getPrezzoGiornaliero() {
		return prezzoGiornaliero;
	}
	public void setPrezzoGiornaliero(double prezzoGiornaliero) {
		this.prezzoGiornaliero = prezzoGiornaliero;
	}
}