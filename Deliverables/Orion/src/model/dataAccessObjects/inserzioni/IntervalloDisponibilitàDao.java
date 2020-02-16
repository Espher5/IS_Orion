package model.dataAccessObjects.inserzioni;

import java.util.Date;
import java.util.List;

import model.beans.inserzioni.IntervalloDisponibilit‡Bean;


public interface IntervalloDisponibilit‡Dao {
	int doSave(IntervalloDisponibilit‡Bean intervalloDisponibilit‡);
	int doDelete(long idInserzione, Date dataInizio, Date dataFine);
	IntervalloDisponibilit‡Bean doRetrieveByKey(long idInserzione, Date dataInizio, Date dataFine);
	List<IntervalloDisponibilit‡Bean> doRetrieveByIdInserzione(long idInserzione);
	IntervalloDisponibilit‡Bean doRetrieveByDate(long idInserzione, Date dataInizio, Date dataFine);
}
