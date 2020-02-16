package test.controller.gestioneIserzioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneInserzioni.InserimentoRecensione;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.NotAuthorizedException;
import controller.OrionException;


public class InserimentoRecensioneTest extends ServletTest {
	private InserimentoRecensione servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		setParameters();
		servlet = new InserimentoRecensione();
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});	
	}
	
	private void setParameters() {
		parameters.put("ID-inserzione", "1");
		parameters.put("punteggio", "5");
		parameters.put("titolo", "Esperienza fantastica");
		parameters.put("contenuto", "Appartamento elegante e luminoso!");
	}
	
	@Test
	public void TC_I4_1() throws Exception {
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
			assertEquals(response_writer.toString(), "Permessi invalidi");
		}
	}
	
	@Test
	public void TC_I4_2() throws Exception {
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
			assertEquals(response_writer.toString(), "Permessi invalidi");
		}
	}
	
	@Test
	public void TC_I4_3() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(4);
			}
		});	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Prenotazione non effettuata");
		}
	}
	
	@Test
	public void TC_I4_4() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(8);
			}
		});	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Recensione già scritta");
		}
	}
	
	@Test
	public void TC_I4_5() throws Exception {
		parameters.put("punteggio", "");
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I4_6() throws Exception {
		parameters.put("punteggio", "10");
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I4_7() throws Exception {
		parameters.put("titolo", "");
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I4_8() throws Exception {
		parameters.put("contenuto", "");
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I4_9() throws Exception {
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "INSERIMENTO_OK");
	}
}
