package model.dataAccessObjects.utenza;

import model.beans.utenza.ProprietarioBean;


public interface ProprietarioDao {
	int doSave(ProprietarioBean proprietario);
	int doUpdate(ProprietarioBean proprietario);
	int doDelete(long idUtente);
	ProprietarioBean doRetrieveByKey(long idUtente);
}
