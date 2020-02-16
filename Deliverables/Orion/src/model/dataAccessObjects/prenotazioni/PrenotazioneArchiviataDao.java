package model.dataAccessObjects.prenotazioni;

import java.util.List;

import model.beans.prenotazioni.PrenotazioneArchiviataBean;


public interface PrenotazioneArchiviataDao {
	int doSave(PrenotazioneArchiviataBean prenotazioneArchivivata);
	int doDelete(long idPrenotazione);
	PrenotazioneArchiviataBean doRetrieveByKey(long idPrenotazione);
	List<PrenotazioneArchiviataBean> doRetrieveByIdUtente(long idUtente);
	List<PrenotazioneArchiviataBean> doRetrieveByIdInserzione(long idInserzione);
}
