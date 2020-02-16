package controller.gestioneUtenza;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;
import controller.OrionException;
import model.beans.prenotazioni.MetodoPagamentoBean;
import model.beans.utenza.*;
import model.dataAccessObjects.prenotazioni.*;
import model.dataAccessObjects.utenza.*;

/**
 * Servlet per la registrazione di clienti e proprietari
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/Registrazione")
public class Registrazione extends BaseServlet {
	private static final long serialVersionUID = -6926993897941318237L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		
		String email = request.getParameter("email"),
			   password = request.getParameter("password"),
			   confermaPassword = request.getParameter("conferma-password"),
			   nome = request.getParameter("nome"),
			   cognome = request.getParameter("cognome");
		
		if (!validaCredenzialiUtente(email, password, confermaPassword, nome, cognome)) {
			writer.write("Parametri invalidi");
			throw new OrionException("Parametri invalidi");
		}
		/**
		 * Verifica l'esistenza dell'utente
		 */
		UtenteDao ud = new UtenteDaoImpl();
		if(ud.doRetrieveByEmail(email) != null) {
			writer.write("Account esistente");
			throw new OrionException("Account esistente");
		}
		
		UtenteBean u = new UtenteBean();
		u.setEmail(email);
		u.setPassword(password);
		u.setNome(nome);
		u.setCognome(cognome);
		u.setDataRegistrazione(new Date());
		u.setStato(true);
	
		long idUtente = ud.doSave(u);
		u.setIdUtente(idUtente);

		HttpSession session = request.getSession();
		session.setAttribute("utente", u);

		/*
		 * Registrazione cliente
		 */
		boolean flagCliente = Boolean.parseBoolean(request.getParameter("cliente"));
		if(flagCliente) {
			ClienteBean c = new ClienteBean();
			c.setIdUtente(idUtente);
			c.setDataUltimaPrenotazione(null);
			ClienteDao cd = new ClienteDaoImpl();
			cd.doSave(c);

			session.setAttribute("cliente", true);
		}
		else {
			/*
			 * Registrazione proprietario
			 */
			String codiceFiscale = request.getParameter("codice-fiscale"),
					numeroCarta = request.getParameter("mp-numero-carta"),
					nomeTitolare = request.getParameter("mp-nome-titolare"),
					cognomeTitolare = request.getParameter("mp-cognome-titolare"),
					dataScadenza = request.getParameter("mp-data-scadenza");

			if(!checkParameters(codiceFiscale, numeroCarta, nomeTitolare, cognomeTitolare, dataScadenza)) {
				
			}
			ProprietarioBean p = new ProprietarioBean();
			p.setIdUtente(idUtente);
			p.setCodiceFiscale(codiceFiscale);
			p.setNumInserzioniInserite(0);
			ProprietarioDao pd = new ProprietarioDaoImpl();
			pd.doSave(p);

			MetodoPagamentoBean mp = new MetodoPagamentoBean();
			mp.setNumeroCarta(numeroCarta);
			mp.setIdUtente(idUtente);
			mp.setNomeTitolare(nomeTitolare);
			mp.setCognomeTitolare(cognomeTitolare);
			mp.setPreferito(true);
			try {
				mp.setDataScadenza(new SimpleDateFormat("yyyy-MM-dd").parse(dataScadenza));
			} catch (ParseException e) {
				throw new OrionException("Parametri immessi invalidi");
			}
			MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
			mpd.doSave(mp);

			session.setAttribute("proprietario", true);	
			
		}
		writer.write("REGISTRAZIONE_OK");
		response.sendRedirect(".");
	}
}