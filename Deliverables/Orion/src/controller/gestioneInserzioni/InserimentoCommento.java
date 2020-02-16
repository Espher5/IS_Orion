package controller.gestioneInserzioni;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.CommentoBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;

/**
 * Servlet implementation class InserimentoCommento
 */
@WebServlet("/InserimentoCommento")
public class InserimentoCommento extends BaseServlet {
	private static final long serialVersionUID = -6599629037738376519L;

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
			   parametroProprietario = request.getParameter("ID-proprietario"),
			   parametroInserzione = request.getParameter("ID-inserzione");
		String contenuto = request.getParameter("commento");
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		long idRecensione = 0;
		long idProprietario = 0;
		long idInserzione = 0;
		
		try {
			idRecensione = Long.parseLong(parametroRecensione);
			idProprietario = Long.parseLong(parametroProprietario);
			idInserzione = Long.parseLong(parametroInserzione);
		} catch(NumberFormatException e) {
			throw new OrionException();
		}
		
		if(utente != null && utente.getIdUtente() == idProprietario && checkParameters(contenuto)) {
			CommentoDao cd = new CommentoDaoImpl();
			CommentoBean cb = new CommentoBean();
			
			cb.setIdRecensione(idRecensione);
			cb.setContenuto(contenuto);
			cb.setDataPubblicazione(new Date());
			cd.doSave(cb);
			
			response.sendRedirect("Inserzione?ID=" + idInserzione);
		}
		else {
			throw new NotAuthorizedException("Non si dispone dei permessi necessari per eliminare questa recensione");
		}
	}
}
