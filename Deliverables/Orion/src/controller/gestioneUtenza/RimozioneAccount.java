package controller.gestioneUtenza;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RimozioneAccount")
public class RimozioneAccount extends BaseServlet {
	private static final long serialVersionUID = -843423944533825387L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parametroIdUtente = request.getParameter("ID");
		long idUtente = 0;
		try {
			idUtente = Long.parseLong(parametroIdUtente);
		} catch(NumberFormatException e) {
			throw new OrionException("Formato URL incorretto");
		}
		
		/*
		 * Verifica che colui che ha effettuato la richiesta è autorizzato a rimuovere l'account
		 */
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");		
		if(utente != null) {
			if(isAmministratore(utente.getIdUtente()) || utente.getIdUtente() == idUtente) {
				UtenteDao ud = new UtenteDaoImpl();
				ud.doDelete(idUtente);
			}
			response.sendRedirect(isAmministratore(idUtente) ? "Amministrazione" : "Logout");
		}
		else {
			throw new NotAuthorizedException();
		}
	}
}
