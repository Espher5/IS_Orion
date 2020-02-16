package controller.gestioneInserzioni;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.RecensioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.RecensioneDao;
import model.dataAccessObjects.inserzioni.RecensioneDaoImpl;
import model.dataAccessObjects.prenotazioni.PrenotazioneDao;
import model.dataAccessObjects.prenotazioni.PrenotazioneDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/InserimentoRecensione")
public class InserimentoRecensione extends BaseServlet {
	private static final long serialVersionUID = -3131704599204365632L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();	
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
			
		if(utente == null || !isCliente(utente.getIdUtente())) {
			writer.write("Permessi invalidi");
			throw new NotAuthorizedException("Permessi invalidi");
		}
		
		long idUtente = utente.getIdUtente();
		long idInserzione = Long.parseLong(request.getParameter("ID-inserzione"));
		
		PrenotazioneDao pd = new PrenotazioneDaoImpl();
		if(pd.doRetrieveByIdClienteAndIdInserzione(idUtente, idInserzione) == null) {
			writer.write("Prenotazione non effettuata");
			throw new OrionException("Prenotazione non effettuata");			
		}
		
		RecensioneDao rd = new RecensioneDaoImpl();
		if(rd.doRetrieveByIdClienteAndIdInserzione(idUtente, idInserzione) != null) {
			writer.write("Recensione già scritta");
			throw new OrionException("Recensione già scritta");
		}
		
		String punteggio = request.getParameter("punteggio"),
			   titolo = request.getParameter("titolo"),
			   contenuto = request.getParameter("contenuto");
		
		if(!validaRecensione(punteggio, titolo, contenuto)) {
			writer.write("Parametri invalidi");
			throw new OrionException("Parametri invalidi");
		}
		
		int p = Integer.parseInt(punteggio);

		RecensioneBean rb = new RecensioneBean();
		rb.setIdCliente(utente.getIdUtente());
		rb.setIdInserzione(idInserzione);
		rb.setPunteggio(p);
		rb.setTitolo(titolo);
		rb.setContenuto(contenuto);
		rb.setDataPubblicazione(new Date());

		rd.doSave(rb);
		writer.write("INSERIMENTO_OK");
		response.sendRedirect("Inserzione?ID=" + idInserzione);
	}
}
