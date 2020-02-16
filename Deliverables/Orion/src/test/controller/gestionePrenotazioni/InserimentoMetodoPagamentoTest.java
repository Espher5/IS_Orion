package test.controller.gestionePrenotazioni;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestionePrenotazioni.InserimentoMetodoPagamento;
import model.beans.utenza.UtenteBean;
import model.dataAccessObjects.utenza.UtenteDaoImpl;
import controller.OrionException;
import controller.NotAuthorizedException;

public class InserimentoMetodoPagamentoTest extends ServletTest {
	
	private InserimentoMetodoPagamento servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new InserimentoMetodoPagamento();	
		
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(1);
			}
		});	
		setParameters();
	}
	
	private void setParameters() {
		parameters.put("mp-numero-carta", "5555-5555-5555-5555");
		parameters.put("mp-nome-titolare", "Michelangelo");
		parameters.put("mp-cognome-titolare", "Esposito");
		parameters.put("mp-data-scadenza", "2020-03-24");
		parameters.put("preferito", "si");
	}
	
	@Test
	public void TC_P1_1() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return null;
			}
		});	
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) { }
		finally {
			assertEquals(response_writer.toString(), "Utente non autenticato");
		}
	}
	
	@Test
	public void TC_P1_2() throws Exception {
		when(session.getAttribute("utente")).thenAnswer(new Answer<UtenteBean>() {
			@Override
			public UtenteBean answer(InvocationOnMock invocation) {
				return new UtenteDaoImpl().doRetrieveByKey(2);
			}
		});		
		try {
			servlet.doPost(request, response);
		} catch(NotAuthorizedException e) { }
		finally {
			assertEquals(response_writer.toString(), "Utente amministratore");
		}
	}
	
	@Test
	public void TC_P1_3() throws Exception {
		parameters.put("mp-numero-carta", "5555-5555-5555-5555-5555");
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) { }
		finally {
			assertEquals(response_writer.toString(), "Numero carta invalido");
		}
	}
	
	@Test
	public void TC_P1_4() throws Exception {
		parameters.put("mp-numero-carta", "5692-8503-7455-0539");	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) { }
		finally {
			assertEquals(response_writer.toString(), "Metodo già inserito");
		}
	}
	
	@Test
	public void TC_P1_5() throws Exception {
		parameters.put("mp-nome-titolare", "");	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) { }
		finally {
			assertEquals(response_writer.toString(), "Nome titolare vuoto");
		}
	}
	
	@Test
	public void TC_P1_6() throws Exception {
		parameters.put("mp-cognome-titolare", "");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) { }
		finally {
			assertEquals(response_writer.toString(), "Cognome titolare vuoto");
		}
	}
	

	@Test
	public void TC_P1_7() throws Exception {
		parameters.put("mp-data-scadenza", "2019-03-24");	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) { }
		finally {
			assertEquals(response_writer.toString(), "Data di scadenza invalida");
		}
	}
	
	@Test
	public void TC_P1_8() throws Exception {	
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "INSERIMENTO_OK");
	}
}
