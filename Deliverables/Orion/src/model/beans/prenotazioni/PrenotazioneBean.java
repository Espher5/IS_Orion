package model.beans.prenotazioni;

import java.util.Date;

public class PrenotazioneBean {
	private long idPrenotazione, idCliente, idInserzione;
	private String mpCliente, mpProprietario;
	private Date dataPrenotazione, dataCheckIn, dataCheckOut;
	private double totale;
	private int numeroOspiti;
	private boolean stato;
	
	public long getIdPrenotazione() {
		return idPrenotazione;
	}
	public void setIdPrenotazione(long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
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
	public String getMpCliente() {
		return mpCliente;
	}
	public void setMpCliente(String mpCliente) {
		this.mpCliente = mpCliente;
	}
	public String getMpProprietario() {
		return mpProprietario;
	}
	public void setMpProprietario(String mpProprietario) {
		this.mpProprietario = mpProprietario;
	}
	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(Date dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public Date getDataCheckIn() {
		return dataCheckIn;
	}
	public void setDataCheckIn(Date dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}
	public Date getDataCheckOut() {
		return dataCheckOut;
	}
	public void setDataCheckOut(Date dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}
	public double getTotale() {
		return totale;
	}
	public void setTotale(double totale) {
		this.totale = totale;
	}
	public int getNumeroOspiti() {
		return numeroOspiti;
	}
	public void setNumeroOspiti(int numeroOspiti) {
		this.numeroOspiti = numeroOspiti;
	}
	public boolean getStato() {
		return stato;
	}
	public void setStato(boolean stato) {
		this.stato = stato;
	}
	
}