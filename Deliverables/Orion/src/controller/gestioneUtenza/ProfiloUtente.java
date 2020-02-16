package controller.gestioneUtenza;


import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import model.beans.prenotazioni.*;
import model.beans.utenza.*;
import model.dataAccessObjects.prenotazioni.*;
import model.dataAccessObjects.utenza.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/ProfiloUtente")
public class ProfiloUtente extends BaseServlet {
	private static final long serialVersionUID = 8762916009696097000L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean ub = (UtenteBean) request.getSession().getAttribute("utente");
		long idUtente = ub.getIdUtente();
		
		if(isProprietario(idUtente)) {
			ProprietarioDao pd = new ProprietarioDaoImpl();
			ProprietarioBean pb = pd.doRetrieveByKey(idUtente);
			request.setAttribute("proprietario", pb);
		}	
		
		MetodoPagamentoDao mpd = new MetodoPagamentoDaoImpl();
		List<MetodoPagamentoBean> metodiPagamento  = mpd.doRetrieveByIdUtente(idUtente);
		if(metodiPagamento != null) {
			request.setAttribute("metodiPagamento", metodiPagamento);
		}		
		request.getRequestDispatcher("/WEB-INF/view/utenza/profiloUtente.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
