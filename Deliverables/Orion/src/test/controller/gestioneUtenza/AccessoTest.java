package test.controller.gestioneUtenza;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import test.controller.ServletTest;
import controller.gestioneUtenza.Accesso;
import controller.OrionException;


public class AccessoTest extends ServletTest {
	
	private Accesso servlet;
	
	@Before
	public void setup() throws IOException {
		super.setup();
		servlet = new Accesso();
		setParameters();
	}
	
	private void setParameters() {
		parameters.put("email", "M.ESPOSITO253@studenti.unisa.it");
		parameters.put("password", "Abcde1");
	}
	
	@Test
	public void TC_U1_1() throws Exception {
		parameters.put("email", "M.ESPOSITO253studenti.unisa.it");	
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Parametri invalidi");
		}		
	}
	
	@Test
	public void TC_U1_2() throws Exception {
		parameters.put("email", "M.ESPOSITO255@studenti.unisa.it");
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente inesistente");
		}		
	}
	
	@Test
	public void TC_U1_3() throws Exception {
		parameters.put("email", "utenteSospeso@gmail.com");
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Utente sospeso");
		}
	}
	
	@Test
	public void TC_U1_4() throws Exception {
		parameters.put("password", "Abcde5");
		
		try {
			servlet.doPost(request, response);
		} catch(OrionException e) {}
		finally {
			assertEquals(response_writer.toString(), "Password non corrispondente");
		}
	}
	
	@Test
	public void TC_U1_5() throws Exception {		
		servlet.doPost(request, response);
		assertEquals(response_writer.toString(), "Accesso_OK");
	}
}
