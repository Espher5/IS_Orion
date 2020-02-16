package controller.gestioneInserzioni;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import model.beans.inserzioni.ImmagineBean;
import model.beans.inserzioni.InserzioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.ImmagineDao;
import model.dataAccessObjects.inserzioni.ImmagineDaoImpl;
import model.dataAccessObjects.inserzioni.InserzioneDao;
import model.dataAccessObjects.inserzioni.InserzioneDaoImpl;

/**
 * Recupera la lista delle inserzioni inserite dal proprietario
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/InserzioniProprietario")
public class InserzioniProprietario extends BaseServlet {
	private static final long serialVersionUID = 1L;

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
		
		if(utente != null) {
			InserzioneDao id = new InserzioneDaoImpl();
			ImmagineDao imd = new ImmagineDaoImpl();
			
			Map<InserzioneBean, List<ImmagineBean>> mappaRisultati = new HashMap<>();
			List<InserzioneBean> inserzioni = id.doRetrieveByIdUtente(utente.getIdUtente());
			
			if(inserzioni != null) {
				inserzioni.forEach(inserzione 
						-> mappaRisultati.put(inserzione, imd.doRetrieveByIdInserzione(inserzione.getIdInserzione())));
			}
			
			request.setAttribute("mappaRisultati", mappaRisultati);
			request.getRequestDispatcher("/WEB-INF/view/inserzioni/listaInserzioni.jsp").forward(request, response); 
		}
		else {
			response.sendRedirect(".");
		}
	}

}
