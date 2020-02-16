package controller.gestionePrenotazioni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.InserzioneBean;
import model.beans.inserzioni.IntervalloDisponibilit‡Bean;
import model.beans.prenotazioni.*;
import model.beans.utenza.ClienteBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.prenotazioni.*;
import model.dataAccessObjects.utenza.ClienteDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/CompletamentoPrenotazione")
public class CompletamentoPrenotazione extends BaseServlet {
	private static final long serialVersionUID = 6755720112134519677L;

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
		HttpSession session = request.getSession();		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente");
			
		if(utente == null || !isCliente(utente.getIdUtente())) {
			writer.write("Permessi invalidi");
			throw new NotAuthorizedException("Permessi invalidi");
		}
		
		/*
		 * Verifica che il cliente abbia precedentemente avviato la prenotazione
		 */
		PrenotazioneBean pb = (PrenotazioneBean) session.getAttribute("prenotazioneAvviata");
		if(pb == null) {
			writer.write("Nessuna prenotazione avviata");
			throw new OrionException("Nessuna prenotazione avviata");
		}
		
		String numeroCarta = request.getParameter("numero-carta"); 		
		long idInserzione = Long.parseLong(request.getParameter("ID-inserzione"));			
		InserzioneDao id = new InserzioneDaoImpl();
		MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
		
		Date scadenzaDate = mpd.doRetrieveByKey(numeroCarta, utente.getIdUtente()).getDataScadenza();
		InserzioneBean ib = id.doRetrieveByKey(idInserzione);
		MetodoPagamentoBean metodoProprietario = mpd.doRetrievePreferito(ib.getIdProprietario());
		
		/*
		 * Verifica che i metodi di pagamento di cliente e proprietario non siano scaduti
		 */		
		if(metodoProprietario == null || 
				!validaData(metodoProprietario.getDataScadenza()) || !validaData(scadenzaDate)) {
			writer.write("Metodo scaduto");
			throw new OrionException("Errore durante la fase di pagamento");				
		}
		
		/*
		 * Verifica che il timer non sia scaduto
		 * 
		 */
		try {
			File fp = new File("C:\\Users\\ikime\\Desktop\\timer.txt");			
			Scanner scanner = new Scanner(fp);	
			try {
				SimpleDateFormat timerFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
				
				Date timerStart = timerFormat.parse(scanner.nextLine());
				Date today = new Date();
				
				long diff = Math.abs(today.getTime() - timerStart.getTime());
				long ore = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
				if(ore > 2) {	
					
					/*
					 * Aggiornamento disponibilit‡ e annullamento della prenotazione
					 */						
					IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
					IntervalloDisponibilit‡Bean idb = (IntervalloDisponibilit‡Bean) session.getAttribute("oldIntervallo");
					IntervalloDisponibilit‡Bean id1 = (IntervalloDisponibilit‡Bean) session.getAttribute("newIntervallo1");
					IntervalloDisponibilit‡Bean id2 = (IntervalloDisponibilit‡Bean) session.getAttribute("newIntervallo2");
					
					idd.doSave(idb);
					idd.doDelete(idInserzione, id1.getDataInizio(), id1.getDataFine());
					idd.doDelete(idInserzione, id2.getDataInizio(), id2.getDataFine());
					
					session.removeAttribute("oldIntervallo");
					session.removeAttribute("newIntervallo1");
					session.removeAttribute("newIntervallo2");
					session.removeAttribute("prenotazioneAvviata");
										
					writer.write("Timer scaduto");
					throw new OrionException("Timer scaduto");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			finally {
				scanner.close();
			}
		}
		catch(FileNotFoundException e) {
			writer.write("Timer scaduto");
			throw new OrionException("Timer scaduto");
		} 
		
		/*
		 * Completa l'inserimento delle informazioni della prenotazione e
		 * ne aggiorna la persistenza
		 */
		pb.setMpCliente(numeroCarta);
		pb.setMpProprietario(metodoProprietario.getNumeroCarta());
		pb.setDataPrenotazione(new Date());
		pb.setStato(true);
		
		PrenotazioneDao pd = new PrenotazioneDaoImpl();
		pd.doUpdate(pb);
				
		/*
		 * Rimuove la prenotazione avviata dalla sessione ed aggiorna la
		 * disponibilit‡ dell'inserzione 
		 */
		session.removeAttribute("prenotazioneAvviata");

		/*
		 * Aggiorna la data dell'ultima prenotazione del cliente
		 */
		ClienteBean cb = new ClienteDaoImpl().doRetrieveByKey(utente.getIdUtente());
		cb.setDataUltimaPrenotazione(new Date());
		
		archiviaPrenotazione(ib, pb);	
		writer.write("COMPLETAMENTO_OK");
		response.sendRedirect(".");
	}
	
	/**
	 * Archivia una prenotazione completata
	 * 
	 * @param ib l'inserzione da cui recuperare alcune informazioni per l'archiviazione
	 * @param pb la prenotazione da archiviare
	 */
	private void archiviaPrenotazione(InserzioneBean ib, PrenotazioneBean pb) {
		PrenotazioneArchiviataDao pad = new PrenotazioneArchiviataDaoImpl();
		PrenotazioneArchiviataBean pab = new PrenotazioneArchiviataBean();
		
		pab.setIDPrenotazione(pb.getIdPrenotazione());
		pab.setStato(ib.getStato());
		pab.setRegione(ib.getRegione());
		pab.setCitt‡(ib.getCitt‡());
		pab.setCap(ib.getCap());
		pab.setIndirizzo(ib.getStrada() + ib.getNumeroCivico());
		pab.setPrezzoGiornaliero(ib.getPrezzoGiornaliero());
		
		pad.doSave(pab);
	}
}