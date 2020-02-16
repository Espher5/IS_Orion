package test.controller.gestioneUtenza;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneUtenza.SospensioneAccount;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.OrionException;
import controller.NotAuthorizedException;

public class SospensioneUtenteTest extends ServletTest {
	
	private SospensioneAccount servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();		
		servlet = new SospensioneAccount();		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(2);
			}
		});	
	}
	
	@Test
	public void TC_U3_1() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return null;
			}
		});			
		parameters.put("ID", new Integer(1).toString());				
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente non autenticato");
		}		
	}
	
	@Test
	public void TC_U3_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(1);
			}
		});			
		parameters.put("ID", new Integer(1).toString());
		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Permessi incompatibili");
		}
	}
	
	@Test
	public void TC_U3_3() throws Exception {	
		parameters.put("ID", new Integer(20).toString());		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente inesistente");
		}
	}
	
	@Test
	public void TC_U3_4() throws Exception {		
		parameters.put("ID", new Integer(4).toString());	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente già sospeso");
		}
	}
	
	@Test
	public void TC_U3_5() throws Exception {		
		parameters.put("ID", new Integer(1).toString());
		servlet.doPost(request, response);
		
		assertEquals(response_writer.toString(), "SOSPENSIONE_OK");
	}
}
