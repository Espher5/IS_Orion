<%@ 
	page contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isErrorPage="true"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<br>
<div style="text-align:center;">
	<h1>Oh no! Qualcosa è andato storto</h1>
	<h1>Errore ${requestScope['javax.servlet.error.status_code']}</h1>
	<a class="btn" href="./">Torna alla pagina principale</a>
</div>
<br><br>
	
<jsp:include page="footer.jsp"/>