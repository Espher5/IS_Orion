package controller.gestionePrenotazioni;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.prenotazioni.MetodoPagamentoBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.*;


/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RimozioneMetodoPagamento")
public class RimozioneMetodoPagamento extends BaseServlet {
	private static final long serialVersionUID = 7621258675774929046L;

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

		if(utente == null) {
			writer.write("Utente non autenticato");
			throw new NotAuthorizedException("Utente non autenticato");
		}
		if(isAmministratore(utente.getIdUtente())) {
			writer.write("Utente amministratore");
			throw new NotAuthorizedException("Utente amministratore");
		}
		
		long idUtente = utente.getIdUtente();	
		String numeroCarta = request.getParameter("mp-numero-carta");

		MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
		MetodoPagamentoBean mpb = mpd.doRetrieveByKey(numeroCarta, idUtente);

		/*
		 * Verifica che colui che ha effettuato la richiesta è autorizzato a rimuovere 
		 * il metodo di pagamento
		 */
		if(mpb == null) {
			writer.write("Proprietario non corrispondente");
			throw new NotAuthorizedException("Proprietario non corrispondente");
		}
		
		/*
		 * Verifica che il metodo da rimuovere non sia l'ultimo metodo
		 * del proprietario
		 */
		List<MetodoPagamentoBean> metodi = mpd.doRetrieveByIdUtente(idUtente);
		if(metodi.size() == 1 && isProprietario(idUtente)) {
			writer.write("Ultimo metodo proprietario");
			throw new OrionException("Non è possibile rimuovere l'unico metodo di pagamento!");
		}

		/*
		 * Eventuale aggiornamento del metodo preferito 
		 */
		if(mpb.isPreferito() && metodi.size() > 1) {
			MetodoPagamentoBean temp = null;
			temp = numeroCarta.equals(metodi.get(0).getNumeroCarta()) ? metodi.get(1) : metodi.get(0);
			temp.setPreferito(true);
			mpd.doUpdate(temp);
		}

		mpd.doDelete(numeroCarta);
		writer.write("RIMOZIONE_OK");
		response.sendRedirect("ProfiloUtente");
	}
}
