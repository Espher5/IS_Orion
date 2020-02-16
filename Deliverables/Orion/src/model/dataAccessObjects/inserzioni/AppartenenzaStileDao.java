package model.dataAccessObjects.inserzioni;

import java.util.List;

import model.beans.inserzioni.AppartenenzaStileBean;


public interface AppartenenzaStileDao {	
	int doSave(AppartenenzaStileBean appartenenzaStile);
	int doDelete(long idInserzione, String nomeStile);
	AppartenenzaStileBean doRetrieveByKey(long idInserzione, String nomeStile);
	List<AppartenenzaStileBean> doRetrieveByIdInserzione(long idInserzione);
}
