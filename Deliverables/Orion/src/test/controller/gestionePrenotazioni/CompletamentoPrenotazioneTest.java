package test.controller.gestionePrenotazioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

import test.controller.ServletTest;
import controller.gestionePrenotazioni.CompletamentoPrenotazione;
import model.beans.inserzioni.IntervalloDisponibilit‡Bean;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.prenotazioni.PrenotazioneDao;
import model.dataAccessObjects.prenotazioni.PrenotazioneDaoImpl;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.NotAuthorizedException;
import controller.OrionException;

public class CompletamentoPrenotazioneTest extends ServletTest {

	private CompletamentoPrenotazione servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new CompletamentoPrenotazione();
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});	
		when(session.getAttribute("prenotazioneAvviata")).thenAnswer(new Answer<PrenotazioneBean>() {
			@Override
			public PrenotazioneBean answer(InvocationOnMock invocation) {
				return new PrenotazioneBean();
			}
		});	
	}
	
	@Test
	public void TC_P4_1() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return null;
			}
		});	
		try {
			servlet.doPost(request, response);
		}
		catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Permessi invalidi");
		}
	}
	
	@Test
	public void TC_P4_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(1);
			}
		});	
		try {
			servlet.doPost(request, response);
		}
		catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Permessi invalidi");
		}
	}
	
	@Test
	public void TC_P4_3() throws Exception {	
		when(session.getAttribute("prenotazioneAvviata")).thenAnswer(new Answer<PrenotazioneBean>() {
			@Override
			public PrenotazioneBean answer(InvocationOnMock invocation) {
				return null;
			}
		});	
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Nessuna prenotazione avviata");
		}
	}
	
	@Test
	public void TC_P4_4() throws Exception {
		parameters.put("ID-inserzione", "1");
		parameters.put("numero-carta", "7777-7777-7777-7777");
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Metodo scaduto");
		}
	}
	
	@Test
	public void TC_P4_5() throws Exception {
		parameters.put("ID-inserzione", "2");
		parameters.put("numero-carta", "7537-7534-5636-0465");
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Metodo scaduto");
		}
	}
	
	@Test
	public void TC_P4_6() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		parameters.put("ID-inserzione", "1");
		parameters.put("numero-carta", "7537-7534-5636-0465");
		
		File fp = new File("C:\\Users\\ikime\\Desktop\\timer.txt");
		PrintWriter out = new PrintWriter(fp);
		out.write("10-02-20 02-04-22");
		out.close();
		
		try {
			when(request.getSession().getAttribute("oldIntervallo")).thenAnswer(new Answer<IntervalloDisponibilit‡Bean>() {
				@Override
				public IntervalloDisponibilit‡Bean answer(InvocationOnMock arg0) throws Throwable {
					IntervalloDisponibilit‡Bean idb = new IntervalloDisponibilit‡Bean();
					idb.setIdInserzione(1);
					idb.setDataInizio(format.parse("2021-01-01"));
					idb.setDataFine(format.parse("2021-02-02"));
					return idb;
				}		
			});
			when(request.getSession().getAttribute("newIntervallo1")).thenAnswer(new Answer<IntervalloDisponibilit‡Bean>() {
				@Override
				public IntervalloDisponibilit‡Bean answer(InvocationOnMock arg0) throws Throwable {
					IntervalloDisponibilit‡Bean idb = new IntervalloDisponibilit‡Bean();
					idb.setIdInserzione(1);
					idb.setDataInizio(format.parse("2021-01-01"));
					idb.setDataFine(format.parse("2021-01-01"));
					return idb;
				}		
			});
			when(request.getSession().getAttribute("newIntervallo2")).thenAnswer(new Answer<IntervalloDisponibilit‡Bean>() {
				@Override
				public IntervalloDisponibilit‡Bean answer(InvocationOnMock arg0) throws Throwable {
					IntervalloDisponibilit‡Bean idb = new IntervalloDisponibilit‡Bean();
					idb.setIdInserzione(1);
					idb.setDataInizio(format.parse("2021-01-09"));
					idb.setDataFine(format.parse("2021-02-02"));
					return idb;
				}		
			});
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Timer scaduto");
		}
	}
	
	@Test
	public void TC_P4_7() throws Exception {
		when(session.getAttribute("prenotazioneAvviata")).thenAnswer(new Answer<PrenotazioneBean>() {
			@Override
			public PrenotazioneBean answer(InvocationOnMock invocation) {
				PrenotazioneDao pd = new PrenotazioneDaoImpl();
				PrenotazioneBean pb = new PrenotazioneBean();
				pb.setIdInserzione(1);
				pb.setIdCliente(3);				
				pb.setIdPrenotazione(pd.doSave(pb));
				return pb;
			}
		});	
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});	
		
		File fp = new File("C:\\Users\\ikime\\Desktop\\timer.txt");
		PrintWriter out = new PrintWriter(fp);	
		SimpleDateFormat timerFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
		out.write(timerFormat.format(new Date()));
		out.close();
		
		parameters.put("ID-inserzione", "1");
		parameters.put("numero-carta", "7537-7534-5636-0465");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "COMPLETAMENTO_OK");
	}
	
}