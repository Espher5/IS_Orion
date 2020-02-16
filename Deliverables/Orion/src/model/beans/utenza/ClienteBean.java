package model.beans.utenza;

import java.util.Date;

public class ClienteBean {
	private long idUtente;
	private Date dataUltimaPrenotazione;
	
	public long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	public Date getDataUltimaPrenotazione() {
		return this.dataUltimaPrenotazione;
	}
	public void setDataUltimaPrenotazione(Date dataUltimaPrenotazione) {
		this.dataUltimaPrenotazione = dataUltimaPrenotazione;
	}
}