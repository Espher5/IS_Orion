package controller.gestioneInserzioni;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.InserzioneBean;
import model.beans.utenza.AmministratoreBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.InserzioneDao;
import model.dataAccessObjects.inserzioni.InserzioneDaoImpl;
import model.dataAccessObjects.utenza.AmministratoreDao;
import model.dataAccessObjects.utenza.AmministratoreDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/ApprovaInserzione")
public class ApprovaInserzione extends BaseServlet {
	private static final long serialVersionUID = -3957686480741808322L;

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
		String parametroInserzione = request.getParameter("ID");
		
		long idInserzione = 0;
		try {
			idInserzione = Long.parseLong(parametroInserzione);
		} catch(NumberFormatException e) {
			throw new OrionException("Formato URL incorretto");
		}
		
		
		/*
		 * Verifica che colui che ha effettuato la richiesta è autorizzato a rimuovere lo stile
		 */
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if(parametroInserzione != null && utente != null && isAmministratore(utente.getIdUtente())) {			
			InserzioneDao id = new InserzioneDaoImpl();
			InserzioneBean ib = id.doRetrieveByKey(idInserzione);
			ib.setVisibilità(true);
			id.doUpdate(ib);
			
			/*
			 * Aggiorna il numero di inserzioni revisionate
			 */
			AmministratoreDao ad = new AmministratoreDaoImpl();
			AmministratoreBean ab = ad.doRetrieveByKey(utente.getIdUtente());
			ab.setNumInserzioniRevisionate(ab.getNumInserzioniRevisionate() + 1);
			ad.doUpdate(ab);
			
			response.sendRedirect("Amministrazione");
		}
		else {
			throw new NotAuthorizedException();
		}
	}

}
