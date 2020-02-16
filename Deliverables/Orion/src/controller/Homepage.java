package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.beans.inserzioni.*;
import model.dataAccessObjects.inserzioni.*;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("")
public class Homepage extends BaseServlet {
	private static final long serialVersionUID = 4518458496830601026L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InserzioneDao id = new InserzioneDaoImpl();
		ImmagineDao imd = new ImmagineDaoImpl(); 
		
		Map<InserzioneBean, List<ImmagineBean>> mappaInserzioni = new HashMap<InserzioneBean, List<ImmagineBean>>();
		List<InserzioneBean> inserzioni = id.doRetrieveAll(100, 0);
		if(inserzioni != null) {
			for(InserzioneBean ib : inserzioni) {
				mappaInserzioni.put(ib, imd.doRetrieveByIdInserzione(ib.getIdInserzione()));
			}
		}
		StileDao sd = new StileDaoImpl();
		List<StileBean> stili = sd.doRetrieveAll();
		
		request.setAttribute("mappaInserzioni", mappaInserzioni);
		request.setAttribute("stili", stili);
		request.getRequestDispatcher("/WEB-INF/view/homepage.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
