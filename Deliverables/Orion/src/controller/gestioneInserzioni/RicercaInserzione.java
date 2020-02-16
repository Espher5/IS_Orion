package controller.gestioneInserzioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.OrionException;
import model.beans.inserzioni.ImmagineBean;
import model.beans.inserzioni.InserzioneBean;
import model.dataAccessObjects.inserzioni.*;

/**
 * Servlet per la gestione della ricerca inserzioni
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/RicercaInserzione")
public class RicercaInserzione extends BaseServlet {
	private static final long serialVersionUID = -5076727282550690767L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stato = request.getParameter("stato"),		
			   città = request.getParameter("citta"),
			   dataCheckIn = request.getParameter("check-in"),
			   dataCheckOut = request.getParameter("check-out"),
			   numeroOspiti = request.getParameter("numero-ospiti"),
			   prezzoMinimo = request.getParameter("prezzo-minimo"),
			   prezzoMassimo = request.getParameter("prezzo-massimo");
		String[] stili = request.getParameterValues("stili");

		
		/*
		 * Inserisce i paraemtri immessi dall'utente o 
		 * li sostituisce con valori di default, in caso non
		 * sia stato immesso nulla
		 */
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(stato.equals("") ? "%" : stato);
		parameters.add(città.equals("") ? "%" : città);
		parameters.add(prezzoMinimo.equals("") ? 0 : Double.parseDouble(prezzoMinimo));
		parameters.add(prezzoMassimo.equals("") ? Integer.MAX_VALUE : Double.parseDouble(prezzoMassimo));
		parameters.add(numeroOspiti.equals("") ? 1 : Integer.parseInt(numeroOspiti));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(!dataCheckIn.equals("") && !dataCheckOut.equals("")) {
				Date checkInDate = format.parse(dataCheckIn);
				Date checkOutDate = format.parse(dataCheckOut);
				parameters.add(checkInDate);
				parameters.add(checkOutDate);
			}
		} catch(ParseException e) {
			throw new OrionException("Parametri invalidi");
		}
		
		InserzioneDao id = new InserzioneDaoImpl();
		List<InserzioneBean> risultati = id.doSearch(parameters, stili, 0);;	

		Map<InserzioneBean, List<ImmagineBean>> mappaRisultati = new HashMap<>();
		if(risultati != null) {
			ImmagineDao imd = new ImmagineDaoImpl();
			for(InserzioneBean ib : risultati) {
				mappaRisultati.put(ib, imd.doRetrieveByIdInserzione(ib.getIdInserzione()));
			}
		}
		
		request.setAttribute("mappaRisultati", mappaRisultati);
		request.getRequestDispatcher("/WEB-INF/view/inserzioni/listaInserzioni.jsp").forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
