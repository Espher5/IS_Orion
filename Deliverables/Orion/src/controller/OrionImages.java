package controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/OrionImages/*")
public class OrionImages extends BaseServlet {
	private static final long serialVersionUID = 3365103732305626103L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		File file = new File("/OrionImages", fileName);
		response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline filename=\"" + file.getName() + "\"");
		Files.copy(file.toPath(), response.getOutputStream());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
