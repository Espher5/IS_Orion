package test.controller.gestioneUtenza;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneUtenza.Registrazione;
import controller.OrionException;

public class RegistrazioneClienteTest extends ServletTest {
	
	private Registrazione servlet;
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new Registrazione();	
		when(request.getParameter("proprietario")).thenReturn(new Boolean(true).toString());
		setParameters();
	}
	
	private void setParameters() {
		parameters.put("email", "NuovaEmail@gmail.com");
		parameters.put("password", "Abcde1");
		parameters.put("conferma-password", "Abcde1");
		parameters.put("nome", "Michelangelo");
		parameters.put("cognome", "Esposito");
		parameters.put("cliente", new Boolean(true).toString());
	}
	
	@Test
	public void TC_U1_1() throws Exception {
		parameters.put("email", "NuovaEmailgmail.com");	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_2() throws Exception {
		parameters.put("email", "EmailEsistente@gmail.com");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Account esistente");
		}		
	}
	
	@Test
	public void TC_U1_3() throws Exception {
		parameters.put("password", "Abcde");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_4() throws Exception {
		parameters.put("conferma-password", "Abcde");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_5() throws Exception {
		parameters.put("nome", "");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_6() throws Exception {
		parameters.put("cognome", "");		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_7() throws Exception {
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "REGISTRAZIONE_OK");	
	}
}
