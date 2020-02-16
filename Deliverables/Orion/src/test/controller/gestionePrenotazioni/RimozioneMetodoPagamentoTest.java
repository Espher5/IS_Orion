package test.controller.gestionePrenotazioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestionePrenotazioni.RimozioneMetodoPagamento;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.OrionException;
import controller.NotAuthorizedException;;


public class RimozioneMetodoPagamentoTest extends ServletTest {
	
	private RimozioneMetodoPagamento servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new RimozioneMetodoPagamento();
	}
	
	@Test 
	public void TC_P2_1() throws Exception {
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
	public void TC_P2_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(2);
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente amministratore");
		}
	}
	
	@Test 
	public void TC_P2_3() throws Exception {
		parameters.put("mp-numero-carta", "3482-7934-9126-8976");
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(4);
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) {}
		finally {
			assertEquals(response_writer.toString(), "Proprietario non corrispondente");
		}
	}
	
	@Test 
	public void TC_P2_4() throws Exception {
		parameters.put("mp-numero-carta", "4573-8523-6355-5532");
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(7);
			}
		});	
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Ultimo metodo proprietario");
		}
	}
	
	@Test 
	public void TC_P2_5() throws Exception {
		parameters.put("mp-numero-carta", "3482-7934-9126-8976");
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(3);
			}
		});	
		
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "RIMOZIONE_OK");		
	}
}
