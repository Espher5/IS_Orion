package model.dataAccessObjects.inserzioni;

import java.util.List;

import model.beans.inserzioni.StileBean;


public interface StileDao {
	int doSave(StileBean stile);
	int doUpdate(StileBean stile);
	int doDelete(String nomeStile);
	StileBean doRetrieveByKey(String nomeStile);
	List<StileBean> doRetrieveAll();
}
