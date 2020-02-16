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
import model.dataAccessObjects.utenza.UtenteDao;
import model.dataAccessObjects.utenza.UtenteDaoImpl;


/**
 * Classe per la gestione della riabilitazione 
 * di un account
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RiabilitazioneAccount")
public class RiabilitazioneAccount extends BaseServlet {
	private static final long serialVersionUID = 8031524930018030321L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		
		if(ub.getStato()) {
			writer.write("Utente non sospeso");
			throw new OrionException("Utente non sospeso");
		}
		ub.setStato(true);
		ud.doUpdate(ub);

		response.sendRedirect("Amministrazione");
	}

}
