<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="stylesheet" value=""/>
</jsp:include>
	
<c:set var="utente" value="${sessionScope.utente}"/>
<c:set var="inserzione" value="${requestScope.inserzione}"/>
<c:set var="immagini" value="${requestScope.immagini}"/>

<c:if test="${!inserzione.visibilità}">
	<div style="width: 100%; height: 10%; background-color: rgba(255,255,0,0.3);">
		<br>
		<span>Questa inserzione è attualmente in fase di revisione.</span>
		<br>
		<br>
	</div>
</c:if>
<c:if test="${amministratore != null}">
	<div class="row">
		<c:if test="${!inserzione.visibilità}">
			<form action="ApprovaInserzione" method="post">
				<div class=form-group>
					<input type="hidden" name="ID" value="${inserzione.idInserzione}">
					<input class="btn btn-primary" type="submit" value="Approva inserzione">
				</div>
			</form>
		</c:if>	
		<form action="RimozioneInserzione" method="post">
			<div class="form-group">
				<input type="hidden" name="ID" value="${inserzione.idInserzione}">
				<input class="btn btn-primary" type="submit" value="Rimuovi inserzione">
			</div>
		</form>
	</div>
</c:if>



<!-- Immagini ed informazioni -->
<div class="container">
	<h1 class="my-4">${inserzione.stato} - ${inserzione.città}<br>
	</h1>
	<div class="row">
		<c:choose>
			<c:when test="${(immagini != null) && (! empty immagini)}">
			
				<!-- Carousel immagini -->
				<div id="carousel" class="carousel slide col-md-8" data-ride="carousel">
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
				<div class="col-md-8">
					<img class="w-100" src="assets/no_image.jpg">
				</div>
			</c:otherwise>
		</c:choose>
		
		<!-- Informazioni inserzione -->
		<div class="col-md-4"> 	
	      <h3 class="my-3">${inserzione.prezzoGiornaliero} &euro; / giorno</h3>
	      <ul>
	      	<li>
	        	Indirizzo: ${inserzione.strada} ${inserzione.numeroCivico} - 
	        	${inserzione.città} (${inserzione.cap})
	        </li>
	        <li>Dimensioni: ${inserzione.metratura} metri quadri</li>
	        <li>Massimo numero di ospiti: ${inserzione.maxNumeroOspiti}</li>
	        <li>Metratura: ${inserzione.metratura} &#x33A1;</li>
	      </ul>
	      <br>
	      <c:if test="${requestScope.stiliInserzione != null && ! empty requestScope.stiliInserzione}">
	      	<h5>Stili</h5>
	      	<ul>
		      	<c:forEach var="stile" items="${requestScope.stiliInserzione}">
					<li>${stile.nomeStile}</li>
		      	</c:forEach>
		      </ul>
	      </c:if>
	      <small>Inserita il: ${inserzione.dataInserimento}</small>
    	</div>
	</div>
</div>
<br>
<br>
<div class="container">
	<div class="row">
		<div class="col-md-10">
			${inserzione.descrizione}
		</div>
		<div class="cold-md-2">
			<!-- Modifica e rimozione inserzione -->
			<c:if test="${utente != null && utente.idUtente == inserzione.idProprietario}">
				<form action="RimozioneInserzione" method="post">
					<input type="hidden" name="ID" value="${inserzione.idInserzione}">
					<input class="btn btn-primary" type="submit" value="Rimuovi inserzione">
				</form>
			</c:if>
		</div>
	</div>
	<br>
</div>
<br>
<br>
<!-- Disponibilità e form prenotazione -->
<div class="container">
	<div class="row">
		<c:set var="intervalliDisponibilità" value="${requestScope.intervalliDisponibilità}"/>
		<c:choose>
			<c:when test="${intervalliDisponibilità != null && !empty intervalliDisponibilità}">
				<!-- Disponibilità -->
				<div class="col-md-6">
					<h4 class="my-4">Periodi di disponibilità dell'appartamento</h4>
					<div>
						<ul>
							<c:forEach var="intervallo" items="${requestScope.intervalliDisponibilità}">
								<li>Dal <b>${intervallo.dataInizio}</b> al <b>${intervallo.dataFine}</b></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<c:choose>
					<c:when test="${sessionScope.prenotazioneAvviata == null}">	
						<!-- Form prenotazione -->
						<div class="col-md-6">
							<c:if test="${requestScope.flagCliente != null}">
								<h4 class="my-4">Prenotati</h4>
								<div>
									<form id="formPrenotazione" action="AvvioPrenotazione" method="post">
										<div class="form-group">
											<label for="check-in">Data check-in</label>
											<input id="check-in" class="form-control" type="date" name="check-in">
										</div>
										<div class="form-group">
											<label for="check-out">Data check-out</label>
											<input id="check-out" class="form-control" type="date" name="check-out">
										</div>
										<div class="form-group">
											<label for="numero-ospiti">Numero ospiti</label>
											<select id="numero-ospiti" class="form-control" name="numero-ospiti">
												<c:forEach var="i" begin="1" end="${inserzione.maxNumeroOspiti}">
													<option value="${i}">${i}</option>
												</c:forEach>
											</select>
										</div>
										<hr>
										<div class="form-group">
											<input type="hidden" name="ID-inserzione" value="${inserzione.idInserzione}">
											<input class="form-control btn btn-primary" type="submit" value="Prenota" style="margin:0px;"> 
										</div>
									</form>
								</div>
							</c:if>				
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.preotazioneAvviata.idInserzione != inserzione.idInserzione}">		
							<c:set var="prenotazioneAvviata" value="${sessionScope.prenotazioneAvviata}"/>
							<div class="col-md-6">
								<h4 class="my-4">Prenotazione in attesa di conferma</h4>
								<h5>Riepilogo</h5>
								<ul>
									<li>Check-in: ${prenotazioneAvviata.dataCheckIn}</li>
									<li>Check-out: ${prenotazioneAvviata.dataCheckOut}</li>
									<li>Numero ospiti: ${prenotazioneAvviata.numeroOspiti}</li>
									<li>Totale: ${prenotazioneAvviata.totale}</li>
								</ul>
								<div>
									<form action="CompletamentoPrenotazione" method="post">
										<div class="form-group">
											<c:set var="numeriCarta" value="${requestScope.numeriCarta}"/>
											<c:choose>			
												<c:when test="${numeriCarta != null && !empty numeriCarta}">
													<label for="metodo-pagamento-prenotazione">Scegli un metodo di pagamento</label>
													<select id="metodo-pagamento-prenotazione" class="form-control" name="numero-carta">
														<c:forEach var="numeroCarta" items="${requestScope.numeriCarta}">
															<option value="${numeroCarta}">${numeroCarta}</option>
														</c:forEach>	
													</select>
													<div class="form-group">
														<input type="hidden" name="ID-inserzione" value="${inserzione.idInserzione}">
														<br>
														<input class="form-control btn btn-primary" type="submit" value="Completa prenotazione" style="margin:0px;">
													</div>							
												</c:when>
												<c:otherwise>
													<span>Nessun metodo di pagamento inserito</span>
													<a class="btn btn-primary" href="ProfiloUtente" role="button">Inseriscine uno</a>
												</c:otherwise>
											</c:choose>					
										</div>
										<hr>
									</form>
									<form action="AnnullamentoPrenotazione" method="post">
										<br>
										<div class="form-group">
											<input type="hidden" name="ID-inserzione" value="${inserzione.idInserzione}">
											<input class="form-control btn btn-primary" type="submit" value="Annulla prenotazione" style="margin:0px;">
										</div>
									</form>
								</div>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="col-md-6">
					<span>L'appartamento non è attualmente disponibile</span>	
				</div>
			</c:otherwise>
		</c:choose>	
	</div>
</div>

<br><br>

<c:set var="recensioni" value="${requestScope.recensioni}"/>

<!-- Area recensioni e commenti-->
<div class="container">
	<c:set var="mappaRecensioni" value="${requestScope.mappaRecensioni}"/>
	<c:if test="${mappaRecensioni != null && !empty mappaRecensioni}">
		<h3 class="my-3">Recensioni</h3>
		<c:forEach var="entry" items="${requestScope.mappaRecensioni}">
			<c:set var="recensione" value="${entry.key}"/>
			<c:set var="commento" value="${entry.value}"/>
				
			<div class="row">	
				<div class="recensione col-md-6">
					<h4>${recensione.titolo} - ${recensione.punteggio}</h4>
					<small>${recensione.dataPubblicazione}</small>
					<p>${recensione.contenuto}</p>
						
					<c:if test="${sessionScope.utente != null && sessionScope.utente.idUtente == recensione.idCliente}">
						<form action="RimozioneRecensione" method="post">
							<div class="form-group">
								<input type="hidden" name="ID-recensione" value="${recensione.idRecensione}">
								<input type="hidden" name="ID-cliente" value="${recensione.idCliente}">
								<input type="hidden" name="ID-inserzione" value="${recensione.idInserzione}">
								<input class="form-control btn btn-primary" type="submit" value="Elimina recensione">
							</div>
						</form>
					</c:if>
				</div>
			</div>
			<c:choose>
				<c:when test="${commento != null}">
					<div style="margin-left:20px;">
						<h4>Risposta del proprietario:</h4>
						<p>${commento.contenuto}</p>
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${requestScope.flagProprietario != null}">
						<div class="col-md-6">
							<form action="InserimentoCommento" method="post">
								<div class="form-group">
									<label for="commento">Commento</label>
									<textarea id="commento" class="form-control" name="commento"></textarea>
								</div>
								<div class="form-group">
									<input type="hidden" name="ID-recensione" value="${recensione.idRecensione}">
									<input type="hidden" name="ID-proprietario" value="${inserzione.idProprietario}">
									<input type="hidden" name="ID-inserzione" value="${recensione.idInserzione}">
									<input class="form-control btn btn-primary" type="submit" value="Commenta recensione">
								</div>
							</form>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:if>	
	
	<br>
	<!-- Area inserimento recensione -->
	<c:if test="${requestScope.flagRecensione != null}">
		<div class="col-md-6">
			<form action="InserimentoRecensione" method="post">
				<div class="form-group">
					<label for="punteggio">Punteggio</label>
					<input id="punteggio" class="form-control" type="number" min="1" max="5" name="punteggio" required>
				</div>
				<div class="form-group">
					<label for="titolo">Titolo</label>
					<input id="titolo" class="form-control" type="text" name="titolo" required>
				</div>
				<div class="form-group">
					<label for="contenuto">Contenuto</label>
					<textarea id="contenuto" class="form-control" name="contenuto"></textarea>
				</div>
				<div class="form-group">
					<input type="hidden" name="ID-inserzione" value="${inserzione.idInserzione}">
					<input class="btn btn-primary form-control" type="submit" value="Inserisci recensione">
				</div>
			</form>
		</div>
	</c:if>
</div>

<jsp:include page="/WEB-INF/view/footer.jsp"></jsp:include>