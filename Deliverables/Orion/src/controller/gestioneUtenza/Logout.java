package controller.gestioneUtenza;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.BaseServlet;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/Logout")
public class Logout extends BaseServlet {
	private static final long serialVersionUID = 6130025740749045304L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Enumeration<String> attributes = session.getAttributeNames();
		while(attributes.hasMoreElements()) {
			session.removeAttribute(attributes.nextElement());
		}
		response.sendRedirect(".");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
