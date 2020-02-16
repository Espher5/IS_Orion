package test.controller.gestioneIserzioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneInserzioni.InserimentoInserzione;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.NotAuthorizedException;
import controller.OrionException;

public class InserimentoInserzioneTest extends ServletTest {

	private InserimentoInserzione servlet;
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new InserimentoInserzione();
		setParameters();
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(1);
			}
		});	
	}
	
	private void setParameters() {		
		parameters.put("stato", "Italia");
		parameters.put("regione", "Campania");
		parameters.put("citta", "Salerno");
		parameters.put("cap", "12345");
		parameters.put("strada", "Via Roma");
		parameters.put("numero-civico", "24");
		parameters.put("prezzo", "120.50");
		parameters.put("numero-ospiti", "10");
		parameters.put("metratura", "340");
		parameters.put("descrizione", "Appartamento luminoso e spazioso");
	}
	
	@Test
	public void TC_I1_1() throws Exception {
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
	public void TC_I1_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
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
	public void TC_I1_3() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(6);
			}
		});			
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Troppe inserzioni inserite");
		}
	}
	
	@Test
	public void TC_I1_4() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("stato", "");
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_5() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("regione", "");	
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_6() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("citta", "");	
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_7() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("cap", "123456");		
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_8() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("strada", "");		
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_9() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("numero-civico", "-5");
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_10() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("prezzo", "-5");		
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_11() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("numero-ospiti", "-5");			
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_12() throws Exception {
		parameters.put("metratura", "-5");					
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}
	
	@Test
	public void TC_I1_13() throws Exception {
		parameters.put("id-proprietario", "1");
		parameters.put("descrizione", "");
		try {
			servlet.doPost(request, response);
		}
		catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}
	}

	@Test
	public void TC_I1_14() throws Exception {	
		parameters.put("id-proprietario", "1");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "INSERIMENTO_OK");
	}
}
