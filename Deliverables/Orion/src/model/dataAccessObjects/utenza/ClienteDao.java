package model.dataAccessObjects.utenza;

import model.beans.utenza.ClienteBean;


public interface ClienteDao {
	int doSave(ClienteBean cliente);
	int doUpdate(ClienteBean cliente);
	int doDelete(long idUtente);
	ClienteBean doRetrieveByKey(long idUtente);
}
