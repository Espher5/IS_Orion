package model.dataAccessObjects.inserzioni;

import model.beans.inserzioni.CommentoBean;


public interface CommentoDao {
	int doSave(CommentoBean commento);
	int doUpdate(CommentoBean commento);
	int doDelete(long idRecensione);
	CommentoBean doRetrieveByKey(long idRecensione);
}
