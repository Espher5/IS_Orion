package model.dataAccessObjects.inserzioni;

import java.util.List;

import model.beans.inserzioni.RecensioneBean;


public interface RecensioneDao {
	long doSave(RecensioneBean recensione);
	int doUpdate(RecensioneBean recensione);
	int doDelete(long idRecensione);
	RecensioneBean doRetrieveByKey(long idRecensione);
	RecensioneBean doRetrieveByIdClienteAndIdInserzione(long idCliente, long idInserzione);
	List<RecensioneBean> doRetrieveByIdInserzione(long idInserzione);
}
