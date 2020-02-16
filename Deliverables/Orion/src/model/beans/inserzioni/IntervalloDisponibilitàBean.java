package model.beans.inserzioni;

import java.util.Date;

public class IntervalloDisponibilit‡Bean {
	private long idInserzione;
	private Date dataInizio, dataFine;
	
	public long getIdInserzione() {
		return idInserzione;
	}
	public void setIdInserzione(long idInserzione) {
		this.idInserzione = idInserzione;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
}