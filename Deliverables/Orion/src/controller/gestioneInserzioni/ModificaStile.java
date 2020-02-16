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
import model.dataAccessObjects.inserzioni.StileDao;
import model.dataAccessObjects.inserzioni.StileDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/ModificaStile")
public class ModificaStile extends BaseServlet {
	private static final long serialVersionUID = -7206151082668745220L;

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
			StileDao sd = new StileDaoImpl();
			StileBean sb = sd.doRetrieveByKey(nomeStile);
			
			sb.setDescrizione(descrizione);
			sd.doUpdate(sb);

			response.sendRedirect("Amministrazione");
		}
		else {
			throw new NotAuthorizedException();
		}
	}
}
