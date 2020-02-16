package controller.gestionePrenotazioni;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.prenotazioni.MetodoPagamentoBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.MetodoPagamentoDao;
import model.dataAccessObjects.prenotazioni.MetodoPagamentoDaoImpl;

/**
 * Servlet per la rimozione di un'inserzione
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/InserimentoMetodoPagamento")
public class InserimentoMetodoPagamento extends BaseServlet {
	private static final long serialVersionUID = 1879468474865130048L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		String numeroCarta = request.getParameter("mp-numero-carta"),
			   nomeTitolare = request.getParameter("mp-nome-titolare"),
			   cognomeTitolare = request.getParameter("mp-cognome-titolare"),
			   dataScadenza = request.getParameter("mp-data-scadenza"),
			   preferito = request.getParameter("preferito");
		
		if(!validaNumeroCarta(numeroCarta)) {
			writer.write("Numero carta invalido");
			throw new OrionException("Numero carta invalido");
		}
		
		MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
		if(mpd.doRetrieveByKey(numeroCarta, utente.getIdUtente()) != null) {
			writer.write("Metodo già inserito");
			throw new OrionException("Metodo già inserito");
		}
		
		if(nomeTitolare.equals("")) {
			writer.write("Nome titolare vuoto");
			throw new OrionException("Nome titolare vuoto");
		}		
		if(cognomeTitolare.equals("")) {
			writer.write("Cognome titolare vuoto");
			throw new OrionException("Cognome titolare vuoto");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = format.parse(dataScadenza);
			if(!validaData(data)) {
				writer.write("Data di scadenza invalida");
				throw new OrionException("Data di scadenza invalida");
			}
		} catch (ParseException e) {
			throw new OrionException("Data di scadenza invalida");
		}
		
		
		MetodoPagamentoBean mpb = new MetodoPagamentoBean();
		mpb.setIdUtente(utente.getIdUtente());
		mpb.setNumeroCarta(numeroCarta);
		mpb.setNomeTitolare(nomeTitolare);
		mpb.setCognomeTitolare(cognomeTitolare);

		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DATE, 1);
		mpb.setDataScadenza(c.getTime());

				
		if(preferito.equals("si")) {
			MetodoPagamentoBean oldPref = mpd.doRetrievePreferito(utente.getIdUtente());
			if(oldPref != null) {
				oldPref.setPreferito(false);
				mpd.doUpdate(oldPref);
			}	
			mpb.setPreferito(true);
		}
		else {
			mpb.setPreferito(false);
		}
		mpd.doSave(mpb);
		writer.write("INSERIMENTO_OK");
		response.sendRedirect("ProfiloUtente");
	}	
}
