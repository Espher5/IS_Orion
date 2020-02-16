<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="" value=""/>
</jsp:include>

<div class="container">
	<div class="row">
		<!-- Sezione utenti -->
		<div class="col-md-6" style="border: 1px solid black; border-collapse: collapse;">
			<h3 class="my-3">Utenti</h3>
			<div>
				<h4 class="my-4">Clienti</h4>
				<c:set var="mappaClienti" value="${requestScope.mappaClienti}"/>
				<c:choose>
					<c:when test="${mappaClienti != null && !empty mappaClienti}">
						<c:forEach var="entry" items="${requestScope.mappaClienti}">
							<c:set var="utenteCliente" value="${entry.key}"/>
							<c:set var="cliente" value="${entry.value}"/>
							
							<ul>
								<li>${utenteCliente.email}</li>
								<li>${utenteCliente.nome} ${utenteCliente.cognome}</li>
								<li>Stato: ${utenteCliente.stato ? "Attivo" : "Sospeso"}</li> 
							</ul>
							<div class="container">
								<div class="row">
									<div class="col-md-6">
										<form action="RimozioneAccount" method="post">
											<input type="hidden" name="ID" value="${utenteCliente.idUtente}">
											<input type="hidden" value="cliente">
											<input class="form-control btn btn-primary" type="submit" value="Rimuovi account">
										</form>
									</div>
									<div class="col-md-6">
										<c:choose>
											<c:when test="${utenteCliente.stato}">
												<form action="SospensioneAccount" method="post">
													<input type="hidden" name="ID" value="${utenteCliente.idUtente}">
													<div class="form-group">
														<input class="form-control btn btn-primary" type="submit" value="Sospendi">
													</div>
												</form>
											</c:when>			
											<c:otherwise>
												<form action="RiabilitazioneAccount" method="post">
													<input type="hidden" name="ID" value="${utenteCliente.idUtente}">
													<div class="form-group">
														<input class="form-control btn btn-primary" type="submit" value="Riabilita">
													</div>
												</form>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<br>
						</c:forEach>					
					</c:when>
					<c:otherwise>
						<span>Nessun cliente trovato</span>
					</c:otherwise>
				</c:choose>
			</div>
			<hr>
			<div>
				<h4 class="my-4">Proprietari</h4>
				<c:set var="mappaProprietari" value="${requestScope.mappaProprietari}"/>
				<c:choose>
					<c:when test="${mappaProprietari != null && !empty mappaProprietari}">
						<c:forEach var="entry" items="${requestScope.mappaProprietari}">
							<c:set var="utenteProprietario" value="${entry.key}"/>
							<c:set var="proprietario" value="${entry.value}"/>
							
							<ul>
								<li>${utenteProprietario.email}</li>
								<li>${utenteProprietario.nome} ${utenteProprietario.cognome}</li>
								<li>Stato: ${utenteProprietario.stato ? "Attivo" : "Sospeso"}</li> 
							</ul>
							<div class="container">
								<div class="row">
									<div class="col-md-6">
										<form action="RimozioneAccount" method="post">
											<input type="hidden" name="ID" value="${utenteProprietario.idUtente}">
											<input type="hidden" value="proprietario">
											<input class="form-control btn btn-primary" type="submit" value="Rimuovi account">
										</form>
									</div>
									<div class="col-md-6">							
										<c:choose>
											<c:when test="${utenteProprietario.stato}">
												<form action="SospensioneAccount" method="post">
													<input type="hidden" name="ID" value="${utenteProprietario.idUtente}">
													<div class="form-group">
														<input class="form-control btn btn-primary" type="submit" value="Sospendi">
													</div>
												</form>
											</c:when>			
											<c:otherwise>
												<form action="RiabilitazioneAccount" method="post">
													<input type="hidden" name="ID" value="${utenteProprietario.idUtente}">
													<div class="form-group">
														<input class="form-control btn btn-primary" type="submit" value="Riabilita">
													</div>
												</form>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>	
							<br>					
						</c:forEach>
					</c:when>
					<c:otherwise>
						<span>Nessun proprietario trovato</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="col-md-6" style="border: 1px solid black; border-collapse: collapse;">
			<!-- Sezione inserzioni -->
			<h3 class="my-3">Inserzioni in attesa di revisione</h3>
			<c:set var="inserzioni" value="${requestScope.inserzioni}"/>
			<c:choose>
				<c:when test="${inserzioni != null && !empty inserzioni}">
					<c:forEach var="inserzione" items="${requestScope.inserzioni}">
						<div class="row align-items-center">
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
								<b>${inserzione.prezzoGiornaliero} / giorno</b>
								<a class="btn btn-primary" role="button" href="Inserzione?ID=${inserzione.idInserzione}">Revisiona</a>
							</div>
						</div>
						<br>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<span>Nessuna inserzione in attesa di revisione</span>
				</c:otherwise>
			</c:choose>	
			
			<br>
			<br>
			
			<!-- Sezione stili -->
			<h3 class="my-3">Stili</h3>
			<c:set var="stili" value="${requestScope.stili}"/>
			<c:choose>
				<c:when test="${stili != null && !empty stili}">
					<c:forEach var="stile" items="${requestScope.stili}">
						<div>
							<h6 class="my-6">${stile.nomeStile}</h6>
							<p>${stile.descrizione}</p>
							<div class="container">
								<div class="row">
									<div class="col-md-6">	
										<button class="btn btn-primary pulsante-modifica-stile">Modifica stile</button>
										<div style="display: none;">				
											<form action="ModificaStile" method="post">
												<div class="form-group">
													<label for="descrizione-stile-mod">Descrizione</label>
													<textarea id="descrizione-stile-mod" class="form-control" name="descrizione-stile"></textarea>
													<div class="form-group">
														<input type="hidden" name="nome-stile" value="${stile.nomeStile}">
														<input class="btn btn-primary" type="submit" value="Conferma modifica">
													</div>
												</div>
											</form>
										</div>
									</div>
									<div class="col-md-6">
										<form action="RimozioneStile" method="post">
											<div class="form-group">
												<input type="hidden" name="nome-stile" value="${stile.nomeStile}">
												<input class="btn btn-primary" type="submit" value="Rimuovi stile">
											</div>
										</form>
									</div>
								</div>
							</div>						
						</div>
						<br>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<span>Nessuno stile disponibile</span>
				</c:otherwise>
			</c:choose>
			
			<br>
			<br>
			
			<button id="pulsante-inserimento-stile" class="btn btn-primary">Inserisci uno stile</button>
			<div style="display: none;">
				<form action="InserimentoStile" method="post">
					<div class="form-group">
						<label for="nome-stile">Nome stile</label>
						<input id="nome-stile" class="form-control" type="text" name="nome-stile" required>
					</div>
					<div class="form-group">
						<label for="descrizione-stile">Descrizione</label>
						<textarea id="descrizione-stile" class="form-control" name="descrizione-stile"></textarea>
					</div>
					<input class="btn btn-primary" type="submit" value="Aggiungi stile">
				</form>	
			</div>	
		</div>						
	</div>
</div>

<script>
	$('#pulsante-inserimento-stile').click(function() {
		$(this).next().toggle();
	});
	
	$('.pulsante-modifica-stile').click(function() {
		$(this).next().toggle();
	});
</script>

<jsp:include page="/WEB-INF/view/footer.jsp"/>