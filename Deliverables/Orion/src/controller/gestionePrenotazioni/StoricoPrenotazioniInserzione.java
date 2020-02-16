package controller.gestionePrenotazioni;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.PrenotazioneDao;
import model.dataAccessObjects.prenotazioni.PrenotazioneDaoImpl;
import model.dataAccessObjects.utenza.UtenteDao;
import model.dataAccessObjects.utenza.UtenteDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/StoricoPrenotazioniInserzione")
public class StoricoPrenotazioniInserzione extends BaseServlet {
	private static final long serialVersionUID = 81834423219788080L;

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
		long idInserzione = Long.parseLong(request.getParameter("ID"));
		
		PrenotazioneDao pd = new PrenotazioneDaoImpl();
		UtenteDao ud = new UtenteDaoImpl();
		List<PrenotazioneBean> prenotazioni = pd.doRetrieveByIdInserzione(idInserzione);
		
		Map<PrenotazioneBean, UtenteBean> mappaPrenotazioni = new HashMap<>();
		
		if(prenotazioni != null) {
			prenotazioni.forEach(prenotazione 
					-> mappaPrenotazioni.put(prenotazione, ud.doRetrieveByKey(prenotazione.getIdCliente())));
		}		
		request.setAttribute("mappaPrenotazioni", mappaPrenotazioni);
		request.getRequestDispatcher("/WEB-INF/view/prenotazioni/storicoPrenotazioniInserzione.jsp").forward(request, response);
	}
}
