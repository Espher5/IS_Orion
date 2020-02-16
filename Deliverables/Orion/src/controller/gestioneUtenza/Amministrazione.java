package controller.gestioneUtenza;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import model.beans.inserzioni.InserzioneBean;
import model.beans.inserzioni.StileBean;
import model.beans.utenza.ClienteBean;
import model.beans.utenza.ProprietarioBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.utenza.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/Amministrazione")
public class Amministrazione extends BaseServlet {
	private static final long serialVersionUID = -8151586650624685964L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		if(utente != null && isAmministratore(utente.getIdUtente())) {
			/*
			 * Recupera la lista di inserzioni da revisionare
			 */
			InserzioneDao id = new InserzioneDaoImpl();
			List<InserzioneBean> inserzioni = id.doRetrieveByVisibility(false);
			request.setAttribute("inserzioni", inserzioni);
			
			StileDao sd = new StileDaoImpl();
			List<StileBean> stili = sd.doRetrieveAll();
			request.setAttribute("stili", stili);
			
			/*
			 * 
			 */
			UtenteDao ud = new UtenteDaoImpl();
			List<UtenteBean> utenti = ud.doRetrieveAll(10, 0);
			
			/*
			 * Per ognuno degli utenti recuperati, preleva le rispettive credenziali da
			 * cliente o proprietario
			 */
			ClienteDao cd = new ClienteDaoImpl();
			Map<UtenteBean, ClienteBean> mappaClienti = new HashMap<UtenteBean, ClienteBean>();
			ProprietarioDao pd = new ProprietarioDaoImpl();
			Map<UtenteBean, ProprietarioBean> mappaProprietari = new HashMap<UtenteBean, ProprietarioBean>();
			
			for(UtenteBean ub : utenti) {
				long idUtente = ub.getIdUtente();
				if(isCliente(idUtente)) {
					mappaClienti.put(ub, cd.doRetrieveByKey(idUtente));
				}
				else if(isProprietario(idUtente)) {
					mappaProprietari.put(ub, pd.doRetrieveByKey(idUtente));
				}
			}
			
			request.setAttribute("mappaClienti", mappaClienti);
			request.setAttribute("mappaProprietari", mappaProprietari);
			request.getRequestDispatcher("/WEB-INF/view/utenza/paginaAmministrazione.jsp").forward(request, response);
		}
		else {
			throw new NotAuthorizedException();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
