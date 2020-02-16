package model.dataAccessObjects.prenotazioni;

import java.util.List;

import model.beans.prenotazioni.PrenotazioneBean;


public interface PrenotazioneDao {
	long doSave(PrenotazioneBean prenotazione);
	int doUpdate(PrenotazioneBean prenotazione);
	int doDelete(long idPrenotazione);
	PrenotazioneBean doRetrieveByKey(long idPrenotazione);
	PrenotazioneBean doRetrieveInSospeso(long idCliente);
	List<PrenotazioneBean> doRetrieveByIdClienteAndIdInserzione(long idCliente, long idInserzione);
	List<PrenotazioneBean> doRetrieveByIdCliente(long idCliente);
	List<PrenotazioneBean> doRetrieveByIdInserzione(long idInserzione);
}
