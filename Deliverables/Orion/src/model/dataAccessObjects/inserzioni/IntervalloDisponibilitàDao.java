package model.dataAccessObjects.inserzioni;

import java.util.Date;
import java.util.List;

import model.beans.inserzioni.IntervalloDisponibilitÓBean;


public interface IntervalloDisponibilitÓDao {
	int doSave(IntervalloDisponibilitÓBean intervalloDisponibilitÓ);
	int doDelete(long idInserzione, Date dataInizio, Date dataFine);
	IntervalloDisponibilitÓBean doRetrieveByKey(long idInserzione, Date dataInizio, Date dataFine);
	List<IntervalloDisponibilitÓBean> doRetrieveByIdInserzione(long idInserzione);
	IntervalloDisponibilitÓBean doRetrieveByDate(long idInserzione, Date dataInizio, Date dataFine);
}
