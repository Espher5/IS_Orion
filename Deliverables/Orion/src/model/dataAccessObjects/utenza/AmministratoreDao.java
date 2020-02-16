package model.dataAccessObjects.utenza;

import model.beans.utenza.AmministratoreBean;


public interface AmministratoreDao {
	/**
	 * 
	 * @param amministratore bean contente le informazioni da salvare
	 * @return
	 */
	int doSave(AmministratoreBean amministratore);
	int doUpdate(AmministratoreBean amministratore);
	int doDelete(long idUtente);
	
	/**
	 * Recupera
	 * 
	 * @param idUtente l'ID dell'amministratore da recuperare
	 * @return l'oggetto AmministratoreBean contente i dati recuperati
	 * o null in caso di 
	 * 
	 * @see JdbcManager.doSelect()
	 */
	AmministratoreBean doRetrieveByKey(long idUtente);
}
