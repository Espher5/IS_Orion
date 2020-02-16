package controller.gestioneInserzioni;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RimozioneStile")
public class RimozioneStile extends BaseServlet {
	private static final long serialVersionUID = -5902557516051365631L;

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
		String nomeStile = request.getParameter("nome-stile");
		
		/*
		 * Verifica che colui che ha effettuato la richiesta è autorizzato a rimuovere lo stile
		 */
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if(nomeStile != null && utente != null && isAmministratore(utente.getIdUtente())) {			
			StileDao sd = new StileDaoImpl();
			sd.doDelete(nomeStile);
			response.sendRedirect("Amministrazione");
		}
		else {
			throw new NotAuthorizedException();
		}
	}
}
