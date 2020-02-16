package controller.gestioneUtenza;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BaseServlet;
import model.dataAccessObjects.utenza.UtenteDao;
import model.dataAccessObjects.utenza.UtenteDaoImpl;

/**
 * Servlet per la gestione di chiamate AJAX che si occupa di verificare se un
 * indirizzo email è già stato utilizzato o meno
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/AjaxEmail")
public class AjaxEmail extends BaseServlet {
	private static final long serialVersionUID = -6215158463912127149L;	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		
		if(email == null) {
			response.sendRedirect("");
		}
		else {
			UtenteDao ud = new UtenteDaoImpl();
			if(ud.doRetrieveByEmail(email) != null) {
				out.append("NO");
			}
			else {
				out.append("OK");
			}
		}
	}
}
