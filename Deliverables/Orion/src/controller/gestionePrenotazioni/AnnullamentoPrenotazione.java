package controller.gestionePrenotazioni;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;
import model.beans.inserzioni.IntervalloDisponibilit‡Bean;
import model.dataAccessObjects.inserzioni.IntervalloDisponibilit‡Dao;
import model.dataAccessObjects.inserzioni.IntervalloDisponibilit‡DaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/AnnullamentoPrenotazione")
public class AnnullamentoPrenotazione extends BaseServlet {
	private static final long serialVersionUID = -6316027431564991332L;

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
		HttpSession session = request.getSession();
		
		long idInserzione = Long.parseLong(request.getParameter("ID-inserzione"));
		
		IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
		IntervalloDisponibilit‡Bean idb = (IntervalloDisponibilit‡Bean) session.getAttribute("oldIntervallo");
		IntervalloDisponibilit‡Bean id1 = (IntervalloDisponibilit‡Bean) session.getAttribute("newIntervallo1");
		IntervalloDisponibilit‡Bean id2 = (IntervalloDisponibilit‡Bean) session.getAttribute("newIntervallo2");
		
		idd.doSave(idb);
		idd.doDelete(idInserzione, id1.getDataInizio(), id1.getDataFine());
		idd.doDelete(idInserzione, id2.getDataInizio(), id2.getDataFine());				
		
		session.removeAttribute("oldIntervallo");
		session.removeAttribute("newIntervallo1");
		session.removeAttribute("newIntervallo2");
		session.removeAttribute("prenotazioneAvviata");
		response.sendRedirect("Inserzione?ID=" + idInserzione);
	}
}
