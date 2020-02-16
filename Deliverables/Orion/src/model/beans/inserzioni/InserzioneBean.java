package model.beans.inserzioni;

import java.util.Date;

public class InserzioneBean {
	private long idInserzione, idProprietario;
	private String stato, regione, città, cap, strada, descrizione;
	private int numeroCivico, maxNumeroOspiti;
	private double prezzoGiornaliero, metratura;
	private Date dataInserimento;
	private boolean visibilità;
	
	public long getIdInserzione() {
		return idInserzione;
	}
	public void setIdInserzione(long idInserzione) {
		this.idInserzione = idInserzione;
	}
	public long getIdProprietario() {
		return idProprietario;
	}
	public void setIdProprietario(long idProprietario) {
		this.idProprietario = idProprietario;
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
	public String getStrada() {
		return strada;
	}
	public void setStrada(String strada) {
		this.strada = strada;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(int numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	public int getMaxNumeroOspiti() {
		return maxNumeroOspiti;
	}
	public void setMaxNumeroOspiti(int maxNumeroOspiti) {
		this.maxNumeroOspiti = maxNumeroOspiti;
	}
	public double getPrezzoGiornaliero() {
		return prezzoGiornaliero;
	}
	public void setPrezzoGiornaliero(double prezzoGiornaliero) {
		this.prezzoGiornaliero = prezzoGiornaliero;
	}
	public double getMetratura() {
		return metratura;
	}
	public void setMetratura(double metratura) {
		this.metratura = metratura;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public boolean getVisibilità() {
		return visibilità;
	}
	public void setVisibilità(boolean visibilità) {
		this.visibilità = visibilità;
	}
}