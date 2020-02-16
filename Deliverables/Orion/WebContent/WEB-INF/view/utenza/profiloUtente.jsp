<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="pageName" value="Orion"/>
</jsp:include>

<style>
	h4 {
		display: inline;
	}
	
	.botton-modifica {
		margin-left: 10px;
	}
</style>

<div class="container">
	<c:set var="utente" value="${sessionScope.utente}"/>
	
	<h3 class="my-3">Informazioni account</h3>
	<form action="ModificaCredenziali" method="post">
		<div class="row">
			<div class="col-md-4">
				<h4 class="my-4">E-mail:</h4>&nbsp;&nbsp;<span>${utente.email}</span>
			</div>
			<div class="col-md-2">
				<button class="btn btn-primary bottone-modifica" type="button">Modifica</button>
			</div>
		</div>	
		<div class="form-group cold-md-4" style="display: none;">
			<input id="email-modifica" class="form-control input-credenziali" type="text" name="email" value="${utente.email}">
		</div>	
		
		<br><br>	
		<div class="row">
			<div class="col-md-4">			
				<h4 class="my-4">Password:</h4>&nbsp;&nbsp;<span>${utente.password}</span>
			</div>
			<div class="col-md-2">
				<button class="btn btn-primary bottone-modifica" type="button">Modifica</button>
			</div>
		</div>
		<div class="form-group" style="display: none;">
			<input id="password-modifica" class="form-control input-credenziali" type="text" name="password" value="${utente.password}">
			<br>
			<label for="conferma-password-modifica">Conferma password</label>
			<input id="conferma-password-modifica" class="form-control input-credenziali" type="text" name="conferma-password" value="${utente.password}">
		</div>
		
		<br><br>
		<div class="row">
			<div class="col-md-4">
				<h4 class="my-4">Nome:</h4>&nbsp;&nbsp;${utente.nome}<br>
			</div>
			<div class="col-md-2">
				<button class="btn btn-primary bottone-modifica" type="button">Modifica</button>
			</div>
		</div>
		<div class="form-group" style="display: none;">
			<input id="nome-modifica" class="form-control input-credenziali" type="text" name="nome" value="${utente.nome}">
		</div>
		
		<br><br>
		<div class="row">
			<div class="col-md-4">
				<h4 class="my-4">Cognome:</h4>&nbsp;&nbsp;${utente.cognome}<br>	
			</div>	
			<div class="col-md-2">
				<button class="btn btn-primary bottone-modifica" type="button">Modifica</button>
			</div>
		</div>
		<div class="form-group" style="display: none;">
			<input id="cognome-modifica" class="form-control input-credenziali" type="text" name="cognome" value="${utente.cognome}">
		</div>
				
		<input type="hidden" name="ID" value="${utente.idUtente}">
		<div class="row">
			<input class="btn btn-primary conferma-modifica-credenziali" type="submit" value="Conferma modifiche" style="display: none;">
		</div>
	</form>
	
	<br>	
	<c:set var="proprietario" value="${requestScope.proprietario}"/>
	<c:if test="${proprietario != null}">
		<br><br>
		<div class="row">
			<div class="col-md-4">
				<h4 class="my-4">Codice fiscale</h4>&nbsp;&nbsp;${proprietario.codiceFiscale}
			</div>
		</div>
		<br><br>
		<div class="row">
			<a class="btn btn-primary" role="button" href="InserzioniProprietario">Le mie inserzioni</a>
		</div>
		<form action="RimozioneAccount"  method="post">
			<div class="row">
				<input type="hidden" name="ID" value="${utente.idUtente}">
				<input class="btn btn-primary" type="submit" value="Disattiva account">
			</div>
		</form>
	</c:if>
	
	<br>
	<c:if test="${sessionScope.cliente}">
		<div class="row">
			<a class="btn btn-primary" role="button" href="StoricoPrenotazioniUtente">Le mie prenotazioni</a>
		</div>
		<form action="RimozioneAccount"  method="post">
			<div class="row">
				<input type="hidden" name="ID" value="${utente.idUtente}">
				<input class="btn btn-primary" type="submit" value="Disattiva account">
			</div>
		</form>
	</c:if>
</div>

<br>
<hr>
<br>

<!-- Area metodi di pagamento -->
<c:if test="${!sessionScope.amministratore}">
	<div class="container">
		<c:set var="metodiPagamento" value="${requestScope.metodiPagamento}"/>
		<div>
			<h3 class="my-3">
				Metodi di pagamento
				<button class="btn bottone-aggiunta-mp" style="border: 1px dotted gray;">+</button>
			</h3>
			
			<div id="area-from-aggiunta-mp" style="display: none;">
				<form id="form-aggiunta-metodo-pagamento" action="InserimentoMetodoPagamento" method="post" onsubmit="return validaMP()">
					<div class="form-group">
						<label for="mp-numero-carta-ins">Numero carta</label><span id="numero-carta-valido-ins"></span>
						<input id="mp-numero-carta-ins" class="form-control" type="text" name="mp-numero-carta" required>
					</div>
					<div class="form-group">
						<label for="mp-nome-titolare-ins">Nome titolare</label>
						<input id="mp-nome-titolare-ins" class="form-control" type="text" name="mp-nome-titolare" required>
					</div>
					<div class="form-group">
						<label for="mp-cognome-titolare-ins">Cognome titolare</label>
						<input id="mp-cognome-titolare-ins" class="form-control" type="text" name="mp-cognome-titolare" required>
					</div>
					<div class="form-group">
						<label for="mp-data-scadenza-ins">Data scadenza</label><span id="data-scadenza-valida-ins"></span>
						<input id="mp-data-scadenza-ins" class="form-control" type="date" name="mp-data-scadenza" required>
					</div>
					<div class="form-group">
						<label>Salvare come metodo di pagamento preferito?</label>
						Si&nbsp;<input type="radio" name="preferito" value="si">&nbsp;
						No&nbsp;<input type="radio" name="preferito" value="no" checked><br>
					</div>
					<div class="form-group">
						<input class="btn btn-primary" type="submit" value="Inserisci metodo pagamento">
					</div>
				</form>
				<br>
				<br>
			</div>
		</div>	
		
		<c:choose>
			<c:when test="${metodiPagamento != null && ! empty metodiPagamento}">
				<c:forEach var="metodoPagamento" items="${requestScope.metodiPagamento}">
					<div class="row" style="border: 1px solid gray;">
						<div class="col-md-8">
							<c:if test="${metodoPagamento.preferito}">
								<span style="color: green; text-decoration: underline;">Metodo di pagamento preferito</span><br>
							</c:if>
							NumeroCarta: ${metodoPagamento.numeroCarta}<br>
							Titolare: ${metodoPagamento.nomeTitolare} ${metodoPagamento.cognomeTitolare}<br>
							Data di scadenza: ${metodoPagamento.dataScadenza}<br>
						</div>
						<div class="col-md-4">
							<div>
								<div>
									<button class="btn btn-primary bottone-modifica">Modifica metodo</button>
								</div>
							</div>
							<div style="display:none">
								<form action="ModificaMetodoPagamento" method="post">
									<div class="form-group">
										<label for="mp-nome-titolare-mod">Nome titolare</label>
										<input id="mp-nome-titolare-mod" class="form-control" type="text" name="mp-nome-titolare" required>
									</div>
									<div class="form-group">
										<label for="mp-cognome-titolare-mod">Cognome titolare</label>
										<input id="mp-cognome-titolare-mod" class="form-control" type="text" name="mp-cognome-titolare" required>
									</div>
									<div class="form-group">
										<label for="mp-data-scadenza-mod">Data scadenza</label><span id="data-scadenza-valida-ins"></span>
										<input id="mp-data-scadenza-mod" class="form-control" type="date" name="mp-data-scadenza" required>
									</div>
									<div class="form-group">
										<label>Salvare come metodo di pagamento preferito?</label>
										Si&nbsp;<input type="radio" name="preferito" value="si">&nbsp;
										No&nbsp;<input type="radio" name="preferito" value="no" checked><br>
									</div>
									<div class="form-group">
										<input type="hidden" name="mp-numero-carta" value="${metodoPagamento.numeroCarta}"> 
										<input class="btn btn-primary" type="submit" value="Conferma modifiche">
									</div>
								</form>
							</div>
							<div>
								<form action="RimozioneMetodoPagamento" method="post">
									<input type="hidden" name="mp-numero-carta" value="${metodoPagamento.numeroCarta}">
									<input class="btn btn-primary" type="submit" value="Rimuovi metodo"> 
								</form>
							</div>
						</div>
					</div>
					<br>
				</c:forEach>
			</c:when>		
			<c:otherwise>
				<span>Nessun metodo di pagamento inserito</span><br>
			</c:otherwise>
		</c:choose>
	</div>
	<br>
	<br>
</c:if>
<script>
	function validaMP() {
		var numeroCartaOk = validaNumeroCarta($('#mp-numero-carta-ins').val(), 'numero-carta-valido-ins');
		var dataOk = validaData($('#mp-data-scadenza-ins').val(), 'data-scadenza-valida-ins');
		
		alert(numeroCarta);
		alert(dataOk);
		
		return numeroCartaOk && dataOk;
	} 

	$('.bottone-modifica').click(function() {
		$(this).parent().parent().next().toggle();
	});
	
	$('.bottone-aggiunta-mp').click(function() {
		$('#area-from-aggiunta-mp').toggle();
	});
	
	$('.input-credenziali').keypress(function() {
		$('.conferma-modifica-credenziali').show();
	});
</script>

<jsp:include page="/WEB-INF/view/footer.jsp"></jsp:include>