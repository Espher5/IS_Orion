package model.dataAccessObjects.utenza;

import java.util.List;

import model.beans.utenza.UtenteBean;


public interface UtenteDao {
	long doSave(UtenteBean utente);
	int doUpdate(UtenteBean utente);
	int doDelete(long idUtente);
	UtenteBean doRetrieveByKey(long idUtente);
	UtenteBean doRetrieveByEmail(String email);
	List<UtenteBean> doRetrieveAll(int limit, int offset);
}
