package model.dataAccessObjects.inserzioni;

import java.util.List;

import model.beans.inserzioni.InserzioneBean;


public interface InserzioneDao {
	long doSave(InserzioneBean inserzione);
	int doUpdate(InserzioneBean inserzione);
	int doDelete(long idInserzione);
	InserzioneBean doRetrieveByKey(long idInserzione);
	List<InserzioneBean> doRetrieveByVisibility(boolean visibilità);
	List<InserzioneBean> doRetrieveAll(int limit, int offset);
	List<InserzioneBean> doSearch(List<Object> parameters, String[] stili, int offset);
	List<InserzioneBean> doRetrieveByIdUtente(long idUtente);
}
