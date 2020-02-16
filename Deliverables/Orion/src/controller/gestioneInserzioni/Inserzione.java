package controller.gestioneInserzioni;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.OrionException;
import model.beans.inserzioni.*;
import model.beans.prenotazioni.MetodoPagamentoBean;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.prenotazioni.*;

/**
 * Servlet per il recupero delle informazioni di un'inserzione
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/Inserzione")
public class Inserzione extends BaseServlet {
	private static final long serialVersionUID = 7008577728866419546L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long idInserzione = Long.parseLong(request.getParameter("ID"));
		
		InserzioneDao id = new InserzioneDaoImpl();	
		InserzioneBean ib = id.doRetrieveByKey(idInserzione);
		if(ib == null) {
			throw new OrionException("L'inserzione che cerchi non Ë presente nel nostro catalogo");
		}
		request.setAttribute("inserzione", ib);
				
		/*
		 * Recupera immagini, stili, disponibilit‡,
		 * recensioni e commenti dell'inserzione
		 *  
		 */
		recuperaImmagini(request, idInserzione);
		recuperaStili(request, idInserzione);
		recuperaDisponibilit‡(request, idInserzione);
		recuperaRecensioni(request, idInserzione);
			
	
		/*
		 * Attributi specifici per utente
		 * cliente -> metodi pagamento e verifica recensione
		 * proprietario -> flag per dare la possibilit‡ di modificare / rimuovere l'inserzione
		 */
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");	
		if(utente != null) {
			long idUtente = utente.getIdUtente();
			if(isCliente(idUtente)) {
				MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
				List<MetodoPagamentoBean> metodiPagamento = mpd.doRetrieveByIdUtente((utente.getIdUtente()));
				List<String> numeriCarta = new ArrayList<String>();	
				
				if(metodiPagamento != null) {
					metodiPagamento.forEach(metodo -> numeriCarta.add(metodo.getNumeroCarta()));
				}
				request.setAttribute("flagCliente", true);
				request.setAttribute("numeriCarta", numeriCarta);
				
				/**
				 *  Verifica la disponibilit‡ di inserire una recensione
				 */
				PrenotazioneDao pd = new PrenotazioneDaoImpl();
				List<PrenotazioneBean> pb = pd.doRetrieveByIdClienteAndIdInserzione(idUtente, idInserzione);
				
				RecensioneDao rd = new RecensioneDaoImpl();
				RecensioneBean rb = rd.doRetrieveByIdClienteAndIdInserzione(idUtente, idInserzione);
				if(pb != null && pb.get(0).getStato() == false && rb == null) {
					request.setAttribute("flagRecensione", true);
				}		
			}
		}			
		request.getRequestDispatcher("/WEB-INF/view/inserzioni/inserzione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void recuperaImmagini(HttpServletRequest request, long idInserzione) {
		ImmagineDao imgd = new ImmagineDaoImpl();
		List<ImmagineBean> immagini = imgd.doRetrieveByIdInserzione(idInserzione);
		request.setAttribute("immagini", immagini);
	}
	
	private void recuperaStili(HttpServletRequest request, long idInserzione) {
		AppartenenzaStileDao asd = new AppartenenzaStileDaoImpl();
		List<AppartenenzaStileBean> stili = asd.doRetrieveByIdInserzione(idInserzione);
		request.setAttribute("stiliInserzione", stili);
	}
	
	private void recuperaDisponibilit‡(HttpServletRequest request, long idInserzione) {
		IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
		List<IntervalloDisponibilit‡Bean> intervalliDisponibilit‡ = idd.doRetrieveByIdInserzione(idInserzione);
		request.setAttribute("intervalliDisponibilit‡", intervalliDisponibilit‡);		
	}
	
	private void recuperaRecensioni(HttpServletRequest request, long idInserzione) {
		RecensioneDao rd = new RecensioneDaoImpl();
		CommentoDao cd = new CommentoDaoImpl();
		List<RecensioneBean> recensioni = rd.doRetrieveByIdInserzione(idInserzione);	
		Map<RecensioneBean, CommentoBean> mappaRecensioni = new HashMap<>();
		if(recensioni != null) {
			recensioni.forEach(recensione 
				-> mappaRecensioni.put(recensione, cd.doRetrieveByKey(recensione.getIdRecensione())));
			
		}			
		request.setAttribute("mappaRecensioni", mappaRecensioni);
	}
}
