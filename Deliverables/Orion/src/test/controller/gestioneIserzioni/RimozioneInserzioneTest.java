package test.controller.gestioneIserzioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneInserzioni.RimozioneInserzione;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.NotAuthorizedException;

public class RimozioneInserzioneTest extends ServletTest {
	
	private RimozioneInserzione servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		
		servlet = new RimozioneInserzione();
	}
	
	@Test
	public void TC_I2_1() throws Exception {
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
	public void TC_I2_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});	
		parameters.put("ID", "1");
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Permessi invalidi");
		}
	}
	
	@Test
	public void TC_I2_3() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(2);
			}
		});	
		parameters.put("ID", "1");
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "RIMOZIONE_OK");
	}
}
