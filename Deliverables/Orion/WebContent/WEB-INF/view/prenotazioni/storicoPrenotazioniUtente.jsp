<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="pageName" value="Storico prenotazioni utente"/>
</jsp:include>


<br>
<br>
<div class="container">
	<c:set var="mappaPrenotazioni" value="${requestScope.mappaPrenotazioni}"/>
	<c:choose>
		<c:when test="${mappaPrenotazioni!= null && !empty mappaPrenotazioni}">
			<c:forEach var="entry" items="${requestScope.mappaPrenotazioni}">
				<c:set var="prenotazione" value="${entry.key}"/>
				<c:set var="prenotazioneArchiviata" value="${entry.value}"/>
				<div class="row" style="border: 1px solid black;">
					<div class="col-md-6" >
						<h4>Riepilogo prenotazione</h4>
						<ul>
							<li>Data prenotazione: ${prenotazione.dataPrenotazione}</li>
							<li>Data check-in: ${prenotazione.dataCheckIn}</li>
							<li>Data check-out: ${prenotazione.dataCheckOut}</li>
							<li>Totale: ${prenotazione.totale}</li>
							<li>Numero ospiti: ${prenotazione.numeroOspiti}</li>
						</ul>
					</div>
					<div class="col-md-6">
						<h4>Riepilogo inserzione</h4>
						<ul>
							<li>${prenotazioneArchiviata.stato}, ${prenotazioneArchiviata.città} - ${prenotazioneArchiviata.regione}</li>
							<li>Indirizzo: ${prenotazioneArchiviata.indirizzo} (${prenotazioneArchiviata.cap})</li>
							<li>Prezzo pagato(al giorno): ${prenotazioneArchiviata.prezzoGiornaliero}</li>
						</ul>
						<br>
					</div>
				</div>
				<br>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<span>Nessuna prenotazione recuperata</span>
		</c:otherwise>
	</c:choose>
</div>

<jsp:include page="/WEB-INF/view/footer.jsp"></jsp:include>