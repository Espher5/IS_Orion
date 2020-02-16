<%@ 
	page contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isErrorPage="true"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
	<jsp:param name="pageName" value="Errore"/>
</jsp:include>

<br><br>
<div style="text-align:center;">
	<h1>Oh no! Qualcosa è andato storto</h1>
			
			
	<div><%= exception.getMessage() %></div>
	<br><br>
	<a class="btn" href="./">Torna alla pagina principale</a>
</div>
<br><br>
	
<jsp:include page="footer.jsp"/>