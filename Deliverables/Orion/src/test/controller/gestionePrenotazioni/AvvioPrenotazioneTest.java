package test.controller.gestionePrenotazioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestionePrenotazioni.AvvioPrenotazione;
import model.beans.prenotazioni.PrenotazioneBean;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.OrionException;
import controller.NotAuthorizedException;

public class AvvioPrenotazioneTest extends ServletTest {
	private AvvioPrenotazione servlet;
	
	@Before 
	public void setup() throws IOException {
		super.setup();
		servlet = new AvvioPrenotazione();
		
		when(request.getParameter("ID-inserzione")).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return new Integer(1).toString();
			}
		});	
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});		
	}
	
	@Test
	public void TC_P3_1() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return null;
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente non autenticato");
		}
	}
	
	@Test
	public void TC_P3_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(1);
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente non cliente");
		}
	}
	
	@Test
	public void TC_P3_3() throws Exception {
		when(session.getAttribute("prenotazioneAvviata")).thenAnswer(new Answer<PrenotazioneBean>() {
			@Override
			public PrenotazioneBean answer(InvocationOnMock invocation) {
				PrenotazioneBean pb = new PrenotazioneBean();
				pb.setStato(false);
				return pb;
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Prenotazione avviata in sospeso");
		}
	}
	
	@Test
	public void TC_P3_4() throws Exception {
		parameters.put("check-in", "2020-01-02");
		parameters.put("check-out", "2020-01-08");
		parameters.put("numero-ospiti", new Integer(5).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Data passata");
		}
	}
	
	@Test
	public void TC_P3_5() throws Exception {
		parameters.put("check-in", "2022-01-02");
		parameters.put("check-out", "2022-01-08");
		parameters.put("numero-ospiti", new Integer(5).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Data non disponibile");
		}
	}
	
	@Test
	public void TC_P3_6() throws Exception {
		parameters.put("check-in", "2020-01-02");
		parameters.put("check-out", "2020-01-08");
		parameters.put("numero-ospiti", new Integer(5).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Data passata");
		}
	}
	
	@Test
	public void TC_P3_7() throws Exception {
		parameters.put("check-in", "2022-01-02");
		parameters.put("check-out", "2022-01-08");
		parameters.put("numero-ospiti", new Integer(5).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Data non disponibile");
		}
	}
	
	@Test
	public void TC_P3_8() throws Exception {
		parameters.put("check-in", "2021-01-02");
		parameters.put("check-out", "2021-01-08");
		parameters.put("numero-ospiti", new Integer(100).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Numero ospiti invalido");
		}
	}
	
	@Test
	public void TC_P3_9() throws Exception {
		parameters.put("check-in", "2021-01-02");
		parameters.put("check-out", "2021-01-08");
		parameters.put("numero-ospiti", new Integer(5).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "AVVIO_PRENOTAZIONE_OK");
		}
	}
}