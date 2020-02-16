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
import model.beans.prenotazioni.PrenotazioneArchiviataBean;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/StoricoPrenotazioniUtente")
public class StoricoPrenotazioniUtente extends BaseServlet {
	private static final long serialVersionUID = -1378067778999559647L;

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
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		PrenotazioneDao pd = new PrenotazioneDaoImpl();
		PrenotazioneArchiviataDao pad = new PrenotazioneArchiviataDaoImpl();
		List<PrenotazioneBean> prenotazioni = pd.doRetrieveByIdCliente(utente.getIdUtente());
		
		Map<PrenotazioneBean, PrenotazioneArchiviataBean> mappaPrenotazioni = new HashMap<>();
		if(prenotazioni != null) {
			prenotazioni.forEach(prenotazione 
					-> mappaPrenotazioni.put(prenotazione, pad.doRetrieveByKey(prenotazione.getIdPrenotazione())));
		}
		
		request.setAttribute("mappaPrenotazioni", mappaPrenotazioni);
		request.getRequestDispatcher("/WEB-INF/view/prenotazioni/storicoPrenotazioniUtente.jsp").forward(request, response);;
	}
}
