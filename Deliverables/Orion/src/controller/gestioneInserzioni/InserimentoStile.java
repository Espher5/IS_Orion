package controller.gestioneInserzioni;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import model.beans.inserzioni.StileBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/InserimentoStile")
public class InserimentoStile extends BaseServlet {
	private static final long serialVersionUID = 8582093457590521502L;
	
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
		String nomeStile = request.getParameter("nome-stile"),
		       descrizione = request.getParameter("descrizione-stile");
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if(checkParameters(nomeStile, descrizione) && utente != null && isAmministratore(utente.getIdUtente())) {
			StileBean sb = new StileBean();
			sb.setNomeStile(nomeStile);
			sb.setDescrizione(descrizione);
			
			StileDao sd = new StileDaoImpl();
			sd.doSave(sb);
			response.sendRedirect("Amministrazione");
		}
		else {
			throw new NotAuthorizedException();
		}
	}
}
