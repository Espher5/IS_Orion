package controller.gestioneUtenza;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.*;

/**
 * Classe per la gestione della sospensione 
 * di un account
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/SospensioneAccount")
public class SospensioneAccount extends BaseServlet {
	private static final long serialVersionUID = 4088006577797724117L;

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
		String parametroIdUtente = request.getParameter("ID");
		long idUtente = 0;
		try {
			idUtente = Long.parseLong(parametroIdUtente);
		} catch(NumberFormatException e) {
			throw new OrionException("Formato URL incorretto");
		}
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if(utente == null) {
			writer.write("Utente non autenticato");
			throw new NotAuthorizedException("Utente non autenticato");
		}
		if(!isAmministratore(utente.getIdUtente())) {
			writer.write("Permessi incompatibili");
			throw new NotAuthorizedException("Permessi incompatibili");
		}
		
		UtenteDao ud = new UtenteDaoImpl();
		UtenteBean ub = ud.doRetrieveByKey(idUtente);
		if(ub == null || isAmministratore(ub.getIdUtente())) {
			writer.write("Utente inesistente");
			throw new OrionException("Utente inesistente");
		}
		
		if(!ub.getStato()) {
			writer.write("Utente già sospeso");
			throw new OrionException("Utente già sospeso");
		}
		ub.setStato(false);
		ud.doUpdate(ub);
		
		writer.write("SOSPENSIONE_OK");
		response.sendRedirect("Amministrazione");
	}
}
