package controller.gestioneUtenza;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;
import controller.OrionException;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.PrenotazioneDao;
import model.dataAccessObjects.prenotazioni.PrenotazioneDaoImpl;
import model.dataAccessObjects.utenza.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/Accesso")
public class Accesso extends BaseServlet {
	private static final long serialVersionUID = -7473397627406564316L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String email = request.getParameter("email"),
			   password = request.getParameter("password");
		
		
		if(!checkParameters(email, password) ||
		   !validaEmail(email) ||
		   !validaPassword(password)) {
			writer.write("Parametri invalidi");
			throw new OrionException("Parametri invalidi");
		}
		
		UtenteDao ud = new UtenteDaoImpl();
		UtenteBean ub = ud.doRetrieveByEmail(email);
		
		if(ub == null) {
			writer.write("Utente inesistente");
			throw new OrionException("Utente inesistente");
		}
		if(!ub.getPassword().equals(password)) {
			writer.write("Password non corrispondente");
			throw new OrionException("Password non corrispondente");
		}
		if(!ub.getStato()) {
			writer.write("Utente sospeso");
			throw new OrionException("L'account è attualmente sospeso e non può effettuare l'accesso");
		}

		HttpSession session = request.getSession();
		session.setAttribute("utente", ub);

		long idUtente = ub.getIdUtente();
		if(isProprietario(idUtente)) {
			session.setAttribute("proprietario", true);
		}
		else if(isAmministratore(idUtente)) {
			session.setAttribute("amministratore", true);
		}	
		else {
			session.setAttribute("cliente", true);						
			/**
			 * Controlla se il cliente ha un prenotazione in sospeso
			 */
			PrenotazioneDao pd = new PrenotazioneDaoImpl();
			PrenotazioneBean prenotazioneAvviata = pd.doRetrieveInSospeso(idUtente);
			if(prenotazioneAvviata != null) {
				session.setAttribute("prenotazioneAvviata", prenotazioneAvviata);
			}
		}
		response.getWriter().write("Accesso_OK");
		response.sendRedirect(".");

	}
}