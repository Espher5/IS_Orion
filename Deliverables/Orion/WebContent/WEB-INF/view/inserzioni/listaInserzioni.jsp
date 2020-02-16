<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="pageName" value="Orion"/>
</jsp:include>

<br>
<br>
<c:set var="mappaRisultati" value="${requestScope.mappaRisultati}"/>
<c:choose>
	<c:when test="${mappaRisultati != null && ! empty mappaRisultati}">
		<div class="container">
			<c:forEach var="entry" items="${requestScope.mappaRisultati}">
				<c:set var="inserzione" value="${entry.key}"/>
				<c:set var="immagini" value="${entry.value}"/>
				
				<div class="row align-items-center" style="border: 1px solid black;">
					<div class="col-md-6">
						<c:choose>
							<c:when test="${immagini != null && ! empty immagini}">
								<div id="carousel" class="carousel slide" data-ride="carousel">
									<div class="carousel-inner">
										<ul class="carousel-indicators">
											<li class="active" data-target="#carousel" data-slide-to="0">
												<c:forEach var="i" begin="1" end="${immagini.size() - 1}">
													<li data-target="#carousel" data-slide-to="${i}">
												</c:forEach>
										</ul>
										<div class="carousel-item active">
											<img class="immagine-inserzione w-100" src="${immagini.get(0).pathname}">
										</div>
										<c:forEach var="i" begin="1" end="${immagini.size() - 1}">
											<div class="carousel-item">
												<img class="immagine-inserzione w-100" src="${immagini.get(i).pathname}">
											</div>
										</c:forEach>
									</div>
									<a class="carousel-control-prev" href="#carousel"
										role="button" data-slide="prev"> <span
										class="carousel-control-prev-icon" aria-hidden="true"></span> <span
										class="sr-only">Precedente</span>
									</a> <a class="carousel-control-next" href="#carousel"
										role="button" data-slide="next"> <span
										class="carousel-control-next-icon" aria-hidden="true"></span> <span
										class="sr-only">Successiva</span>
									</a>
								</div>								
							</c:when>
							<c:otherwise>
								<img class="w-100" src="assets/no_image.jpg">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-6">
						<h3 class="my-3">${inserzione.stato} - ${inserzione.città}</h3>
						<b>${inserzione.prezzoGiornaliero}&euro; / giorno</b>
						<p>${inserzione.descrizione}</p>
						<c:if test="${sessionScope.utente != null && sessionScope.utente.idUtente == inserzione.idProprietario}">
							<a class="btn btn-primary" role="button" href="StoricoPrenotazioniInserzione?ID=${inserzione.idInserzione}">Visualizza storico prenotazioni</a>
						</c:if>
						<a class="btn btn-primary" role="button" href="Inserzione?ID=${inserzione.idInserzione}">Maggiori informazioni</a>
					</div>
				</div>
				<br>
				<br>
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<h3 class="my-3">Nessun risultato trovato</h3>
		<a class="btn btn-primary" role="button" href=".">Torna in homepage</a>
	</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/view/footer.jsp"></jsp:include>