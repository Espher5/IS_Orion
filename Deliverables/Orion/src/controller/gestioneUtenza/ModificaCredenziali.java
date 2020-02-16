package controller.gestioneUtenza;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDao;
import model.dataAccessObjects.utenza.UtenteDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/ModificaCredenziali")
public class ModificaCredenziali extends BaseServlet {
	private static final long serialVersionUID = 4554278149647779L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email"),
			   password = request.getParameter("password"),
			   confermaPassword = request.getParameter("conferma-password"),
			   nome = request.getParameter("nome"),
			   cognome = request.getParameter("cognome");
	
		long idUtente = Long.parseLong(request.getParameter("ID"));
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if (utente != null && utente.getIdUtente() == idUtente &&
				validaCredenzialiUtente(email, password, confermaPassword, nome, cognome)) {
			UtenteBean u = new UtenteBean();
			u.setIdUtente(utente.getIdUtente());
			u.setEmail(email);
			u.setPassword(password);
			u.setNome(nome);
			u.setCognome(cognome);
			u.setDataRegistrazione(new Date());
			u.setStato(true);
						
			UtenteDao ud = new UtenteDaoImpl();
			ud.doUpdate(u);
			
			request.getSession().setAttribute("utente", u);
			response.sendRedirect("ProfiloUtente");
		}
		else {
			throw new NotAuthorizedException();
		}
	}
}
