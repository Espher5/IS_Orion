package controller.gestionePrenotazioni;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.*;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.prenotazioni.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/AvvioPrenotazione")
public class AvvioPrenotazione extends BaseServlet {
	private static final long serialVersionUID = -4994463878676548064L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();	
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		if(utente == null) {
			writer.write("Utente non autenticato");
			throw new NotAuthorizedException("Utente non autenticato");
		}
		if(!isCliente(utente.getIdUtente())) {
			writer.write("Utente non cliente");
			throw new NotAuthorizedException("Utente non cliente");
		}
		if(session.getAttribute("prenotazioneAvviata") != null) {
			writer.write("Prenotazione avviata in sospeso");
			throw new OrionException("Prenotazione avviata in sospeso");
		}
				
		String dataCheckIn = request.getParameter("check-in"),
			   dataCheckOut = request.getParameter("check-out");	
		long idInserzione = Long.parseLong(request.getParameter("ID-inserzione"));	
		
		/*
		 * Formatta e valida le date, assicurandosi che check-in e check-out 
		 * non siano passati e che le rispettive date siano disponibili
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = null,
			 checkOutDate = null;
		try {
			checkInDate = format.parse(dataCheckIn);
			checkOutDate = format.parse(dataCheckOut);
		
			if(!validaData(checkInDate) || !validaData(checkOutDate)) {
				writer.write("Data passata");
				throw new OrionException("Errore durante l'avvio della prenotazione");
			}

			/*
			 * Verifica che le date di check-in e check-out siano disponibili
			 */
			IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
			IntervalloDisponibilit‡Bean idb = idd.doRetrieveByDate(idInserzione, checkInDate, checkOutDate);
			if(idb == null) {
				writer.write("Data non disponibile");
				throw new OrionException("Errore durante l'avvio della prenotazione; data non disponibile.");
			}			
		} catch (ParseException e) {
			writer.write("Formato data incorretto");
			throw new OrionException("Errore durante l'avvio della prenotazione; formato data incorretto.");
		}

		InserzioneDao id = new InserzioneDaoImpl();
		InserzioneBean ib = id.doRetrieveByKey(idInserzione);

		String numeroOspiti = request.getParameter("numero-ospiti");	
		int nOspiti = 0;
		try {
			nOspiti = Integer.parseInt(numeroOspiti);
			if(nOspiti < 0 || nOspiti > ib.getMaxNumeroOspiti()) {
				writer.write("Numero ospiti invalido");
				throw new OrionException("Numero ospiti invalido");
			}
		} catch(NumberFormatException e) {
			throw new OrionException("Errore durante l'avvio della prenotazione");
		}
		
		int numGiorni = (int) TimeUnit.DAYS.
				convert(checkOutDate.getTime() - checkInDate.getTime(),TimeUnit.MILLISECONDS);				

		/*
		 * Aggiornamento disponibilit‡ inserzione
		 */
		IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
		IntervalloDisponibilit‡Bean idb = idd.doRetrieveByDate(idInserzione, checkInDate, checkOutDate);
		request.getSession().setAttribute("oldIntervallo", idb);
		
		idd.doDelete(idInserzione, idb.getDataInizio(), idb.getDataFine());
		
		Calendar c = Calendar.getInstance();
		c.setTime(checkInDate);
		c.add(Calendar.DATE, 0);
		
		IntervalloDisponibilit‡Bean id1 = new IntervalloDisponibilit‡Bean();
		id1.setIdInserzione(idInserzione);
		id1.setDataInizio(idb.getDataInizio());
		id1.setDataFine(c.getTime());
		idd.doSave(id1);
		request.getSession().setAttribute("newIntervallo1", id1);
		
		c = Calendar.getInstance();
		c.setTime(checkOutDate);
		c.add(Calendar.DATE, 2);
		IntervalloDisponibilit‡Bean id2 = new IntervalloDisponibilit‡Bean();
		id2.setIdInserzione(idInserzione);
		id2.setDataInizio(c.getTime());
		id2.setDataFine(idb.getDataFine());
		idd.doSave(id2);
		request.getSession().setAttribute("newIntervallo2", id2);
		
		/*
		 * Avvio timer
		 */
		File fp = new File("C:\\Users\\ikime\\Desktop\\timer.txt");
		PrintWriter out = new PrintWriter(fp);
		
		SimpleDateFormat timerFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
		out.write(timerFormat.format(new Date()));
		out.close();
		
		
		/**
		 * Salvataggio prenotazione
		 */
		PrenotazioneDao pd = new PrenotazioneDaoImpl();
		PrenotazioneBean pb = new PrenotazioneBean();
		pb.setIdCliente(utente.getIdUtente());
		pb.setIdInserzione(idInserzione);
		pb.setDataCheckIn(checkInDate);
		pb.setDataCheckOut(checkOutDate);
		pb.setTotale(numGiorni * ib.getPrezzoGiornaliero());
		pb.setNumeroOspiti(nOspiti);

		long idPrenotazione = pd.doSave(pb);
		pb.setIdPrenotazione(idPrenotazione);

		session.setAttribute("prenotazioneAvviata", pb);
		writer.write("AVVIO_PRENOTAZIONE_OK");
		response.sendRedirect("Inserzione?ID=" + idInserzione);
	}
}
