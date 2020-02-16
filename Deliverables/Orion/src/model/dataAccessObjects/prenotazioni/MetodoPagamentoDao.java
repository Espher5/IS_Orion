package model.dataAccessObjects.prenotazioni;

import java.util.List;

import model.beans.prenotazioni.MetodoPagamentoBean;


public interface MetodoPagamentoDao {
	int doSave(MetodoPagamentoBean metodoPagamento);
	int doUpdate(MetodoPagamentoBean metodoPagamento);
	int doDelete(String numeroCarta);
	MetodoPagamentoBean doRetrieveByKey(String numeroCarta, long idUtente);
	MetodoPagamentoBean doRetrievePreferito(long idUtente);
	List<MetodoPagamentoBean> doRetrieveByIdUtente(long idUtente);
}
