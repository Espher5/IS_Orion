package test.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ServletTest {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected StringWriter response_writer;
	protected Map<String, String> parameters;
	
	protected void setup() throws IOException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);	
		session = mock(HttpSession.class);
		response_writer = new StringWriter();
		parameters = new HashMap<>();
		
		when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return parameters.get((String) invocation.getArguments()[0]);
			}
		});	
		when(request.getSession()).thenAnswer(new Answer<HttpSession>() {
			@Override
			public HttpSession answer(InvocationOnMock arg0) throws Throwable {
				return session;
			}		
		});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
	}
}
