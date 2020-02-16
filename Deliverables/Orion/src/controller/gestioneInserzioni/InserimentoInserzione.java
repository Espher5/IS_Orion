package controller.gestioneInserzioni;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.BaseServlet;
import controller.NotAuthorizedException;
import controller.OrionException;
import model.beans.inserzioni.*;
import model.beans.utenza.ProprietarioBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.inserzioni.*;
import model.dataAccessObjects.utenza.ProprietarioDao;
import model.dataAccessObjects.utenza.ProprietarioDaoImpl;

/**
 * 
 * @author Michelangelo Esposito
 *
 */
@MultipartConfig
@WebServlet("/InserimentoInserzione")
public class InserimentoInserzione extends BaseServlet {
	private static final long serialVersionUID = 683427959010146207L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("redirectFlag") != null) {
			StileDao sd = new StileDaoImpl();
			List<StileBean> stili = sd.doRetrieveAll();
			if(stili != null) {
				request.setAttribute("stiliInserzione", stili);
			}		
			request.getRequestDispatcher("/WEB-INF/view/inserzioni/inserimentoInserzione.jsp").forward(request, response);
			return;
		}
		
		PrintWriter writer = response.getWriter();
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
			
		if(utente == null || !isProprietario(utente.getIdUtente())) {
			writer.write("Permessi invalidi");
			throw new NotAuthorizedException("Permessi invalidi");	
		}
		ProprietarioDao pd = new ProprietarioDaoImpl();
		ProprietarioBean pb = pd.doRetrieveByKey(utente.getIdUtente());
		if(pb.getNumInserzioniInserite() >= 10) {
			writer.write("Troppe inserzioni inserite");
			throw new OrionException("Troppe inserzioni inserite");	
		}
		
		String idProprietario = request.getParameter("id-proprietario"),
				   stato = request.getParameter("stato"),
				   regione = request.getParameter("regione"),
				   citt‡ = request.getParameter("citta"),
				   cap = request.getParameter("cap"),
				   strada = request.getParameter("strada"),
				   numeroCivico = request.getParameter("numero-civico"),
				   prezzo = request.getParameter("prezzo"),
				   numeroOspiti = request.getParameter("numero-ospiti"),
				   metratura = request.getParameter("metratura"),
				   descrizione = request.getParameter("descrizione");	
		String[] stili = request.getParameterValues("stili");
		
		if(!validaParametriInserzione(idProprietario, stato, regione, citt‡, cap, strada, 
				numeroCivico, prezzo, numeroOspiti, metratura, descrizione) 
				) {
			writer.write("Parametri invalidi");
			throw new OrionException("Parametri invalidi");
		}
		else {
			InserzioneBean ib = new InserzioneBean();
			ib.setIdProprietario(Long.parseLong(idProprietario));
			ib.setStato(stato);
			ib.setRegione(regione);
			ib.setCitt‡(citt‡);
			ib.setCap(cap);
			ib.setStrada(strada);
			ib.setNumeroCivico(Integer.parseInt(numeroCivico));
			ib.setPrezzoGiornaliero(Double.parseDouble(prezzo));
			ib.setMaxNumeroOspiti(Integer.parseInt(numeroOspiti));
			ib.setMetratura(Double.parseDouble(metratura));
			ib.setDescrizione(descrizione);
			ib.setDataInserimento(new Date());
			ib.setVisibilit‡(false);

			InserzioneDao id = new InserzioneDaoImpl();
			long idInserzione = id.doSave(ib);
			ib.setIdInserzione(idInserzione);

			if(!saveImages(request, idInserzione)) {
				writer.write("Parametri invalidi");
				throw new OrionException("Parametri invalidi");		
			}
			
			/**
			 * Rende l'inserzione disponibile per il prossimo anno
			 */
			IntervalloDisponibilit‡Bean idb = new IntervalloDisponibilit‡Bean();
			idb.setIdInserzione(idInserzione);
			
			
			Calendar c = Calendar.getInstance();
			Date today = c.getTime();
			c.add(Calendar.YEAR, 1);
			idb.setDataInizio(today);
			idb.setDataFine(c.getTime());
			
			IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
			idd.doSave(idb);
			
			if(stili != null) {
				for(String s: stili) {
					AppartenenzaStileDao asd = new AppartenenzaStileDaoImpl();
					AppartenenzaStileBean asb = new AppartenenzaStileBean();
					asb.setIdInserzione(idInserzione);
					asb.setNomeStile(s);
					asd.doSave(asb);
				}
			}

			/*
			 * Aggiorna il numero di inserzioni inserite del proprietario
			 */
			pb.setNumInserzioniInserite(pb.getNumInserzioniInserite() + 1);
			pd.doUpdate(pb);
			
			writer.write("INSERIMENTO_OK");
			response.sendRedirect("Inserzione?ID=" + idInserzione);
		}
	}

	private boolean saveImages(HttpServletRequest request, long idInserzione) throws ServletException, IOException {
		List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
		if(fileParts.size() >= 10) {
			return false;
		}
		
		for(Part filePart : fileParts) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			
			if(!fileName.equals("")) {
				Random r = new Random();
				int randomValue = r.nextInt(2000000);
				File uploads = new File(getServletContext().getInitParameter("imagePath"));
				File file = new File(uploads, randomValue + fileName);
				
				try (InputStream input = filePart.getInputStream()) {
					Files.copy(input, file.toPath());
				}
				
				ImmagineDao id = new ImmagineDaoImpl();
				ImmagineBean ib = new ImmagineBean();
				ib.setIdInserzione(idInserzione);
				ib.setPathname("." + file.toPath().toString());
				id.doSave(ib);
			}
		}
		return true;
	}
}
