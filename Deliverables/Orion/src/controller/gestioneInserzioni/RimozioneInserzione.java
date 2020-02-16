package controller.gestioneInserzioni;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import model.beans.inserzioni.InserzioneBean;
import model.beans.utenza.ProprietarioBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.utenza.ProprietarioDao;
import model.dataAccessObjects.utenza.ProprietarioDaoImpl;

/**
 * Servlet per la rimozione di un'inserzione
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RimozioneInserzione")
public class RimozioneInserzione extends BaseServlet {
	private static final long serialVersionUID = -7125270938595991505L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		if(utente == null) {
			writer.write("Utente non autenticato");
			throw new NotAuthorizedException("Utente non autenticato");
		}
	
		long idInserzione = Long.parseLong(request.getParameter("ID"));		
		InserzioneDao id = new InserzioneDaoImpl();
		InserzioneBean ib = id.doRetrieveByKey(idInserzione);
		
		if(ib == null || !isAmministratore(utente.getIdUtente()) &&
				utente.getIdUtente() != ib.getIdProprietario()) {
			writer.write("Permessi invalidi");
			throw new NotAuthorizedException("Permessi invalidi");
		}
			
		id.doDelete(idInserzione);
		
		/*
		 * Aggiornamento numero inserzioni inserite
		 */
		ProprietarioDao pd = new ProprietarioDaoImpl();
		ProprietarioBean pb = pd.doRetrieveByKey(ib.getIdProprietario());
		pb.setNumInserzioniInserite(pb.getNumInserzioniInserite() - 1);
		pd.doUpdate(pb);
		
		writer.write("RIMOZIONE_OK");
		response.sendRedirect(".");
	}
}
