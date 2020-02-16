package model.beans.utenza;

public class ProprietarioBean {
	private long idUtente;
	private String codiceFiscale;
	private int numInserzioniInserite;
	
	public long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public int getNumInserzioniInserite() {
		return numInserzioniInserite;
	}
	public void setNumInserzioniInserite(int numInserzioniInserite) {
		this.numInserzioniInserite = numInserzioniInserite;
	}
}