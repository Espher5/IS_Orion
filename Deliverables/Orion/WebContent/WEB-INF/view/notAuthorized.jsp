<%@ 
	page contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	isErrorPage="true"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>
	<section style="text-align:center;">
		<h1>Divieto di accesso!</h1>
		<h1><%= exception.getMessage() %></h1>
		<br><br>
	</section>
<jsp:include page="footer.jsp"/>