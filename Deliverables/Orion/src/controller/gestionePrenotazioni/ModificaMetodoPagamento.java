package controller.gestionePrenotazioni;

import java.util.List;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.OrionException;
import model.beans.prenotazioni.MetodoPagamentoBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.MetodoPagamentoDao;
import model.dataAccessObjects.prenotazioni.MetodoPagamentoDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/ModificaMetodoPagamento")
public class ModificaMetodoPagamento extends BaseServlet {
	private static final long serialVersionUID = -5600622553315859739L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numeroCarta = request.getParameter("mp-numero-carta"),
				   nomeTitolare = request.getParameter("mp-nome-titolare"),
				   cognomeTitolare = request.getParameter("mp-cognome-titolare"),
				   dataScadenza = request.getParameter("mp-data-scadenza"),
				   preferito = request.getParameter("preferito");
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date data;
		try {
			data = format.parse(dataScadenza);
		} catch (ParseException e) {
			throw new OrionException("Formato data invalido");
		}
		
		if(utente != null && checkParameters(numeroCarta, nomeTitolare, cognomeTitolare, dataScadenza)
				&& validaNumeroCarta(numeroCarta) && validaData(data)) {
			MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
			MetodoPagamentoBean mpb = mpd.doRetrieveByKey(numeroCarta, utente.getIdUtente());
			mpb.setNomeTitolare(nomeTitolare);
			mpb.setCognomeTitolare(cognomeTitolare);
			
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DATE, 1);
			mpb.setDataScadenza(c.getTime());
			
			if(preferito.equals("si")) {			
				MetodoPagamentoBean oldPref = mpd.doRetrievePreferito(utente.getIdUtente());
				if(oldPref != null) {
					oldPref.setPreferito(false);
					mpd.doUpdate(oldPref);
				}	
				mpb.setPreferito(true);
			}
			else {
				List<MetodoPagamentoBean> metodi = mpd.doRetrieveByIdUtente(utente.getIdUtente());
				if(metodi != null && metodi.size() > 1) {
					mpb.setPreferito(false);
				}
			}
			mpd.doUpdate(mpb);
			response.sendRedirect("ProfiloUtente");
		}
		else {
			throw new OrionException("Formato parametri incorretto");
		}
	}
}
