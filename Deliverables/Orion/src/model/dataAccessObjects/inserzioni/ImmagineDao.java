package model.dataAccessObjects.inserzioni;

import java.util.List;

import model.beans.inserzioni.ImmagineBean;


public interface ImmagineDao {
	int doSave(ImmagineBean immagine);
	int doDelete(String pathname);
	ImmagineBean doRetrieveByKey(String pathname);
	List<ImmagineBean> doRetrieveByIdInserzione(long idInserzione);
}
