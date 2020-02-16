package controller.gestioneInserzioni;

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
import model.dataAccessObjects.inserzioni.RecensioneDao;
import model.dataAccessObjects.inserzioni.RecensioneDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RimozioneRecensione")
public class RimozioneRecensione extends BaseServlet {
	private static final long serialVersionUID = -7911006087026746766L;

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
		String parametroRecensione = request.getParameter("ID-recensione"),
			   parametroCliente = request.getParameter("ID-cliente"),
			   parametroInserzione = request.getParameter("ID-inserzione");
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		long idRecensione = 0;
		long idCliente = 0;
		long idInserzione = 0;
		
		try {
			idRecensione = Long.parseLong(parametroRecensione);
			idCliente = Long.parseLong(parametroCliente);
			idInserzione = Long.parseLong(parametroInserzione);
		} catch(NumberFormatException e) {
			throw new OrionException();
		}
		
		if(utente != null && utente.getIdUtente() == idCliente) {
			RecensioneDao rd = new RecensioneDaoImpl();
			
			rd.doDelete(idRecensione);
			response.sendRedirect("Inserzione?ID=" + idInserzione);
		}
		else {
			throw new NotAuthorizedException("Non si dispone dei permessi necessari per eliminare questa recensione");
		}
	}
}
