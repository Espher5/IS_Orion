<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=devce-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	
		<link rel="stylesheet" type="text/css" href="CSS/stileHeader.css">
		<c:set var="stylesheet" value="${requestScope.stylesheet}"/>
		<c:if test="${stylesheet != null}">
			<link rel="stylesheet" type="text/css" href="CSS/${stylesheet}">
		</c:if>
  		<script src="Javascript/validation.js"></script>
  		
		<title>Orion</title>
	</head>

	<body>
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
  			</button>
			<a class="navbar-brand" href=".">Home</a>
			<div id="navbar" class="collapse navbar-collapse justify-content-between">
				<div class="navbar-nav">
					
					<!-- Form dropdown per la ricerca di inserzioni -->
					<div class="dropdown">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Trova un appartamento</button>
						<div class="dropdown-menu w-100" style="background-color: #E0E0E0;">
							<form id="form-ricerca" action="RicercaInserzione" method="get">
								<div class="form-group">
									<label for="stato">Stato</label>
									<input id="stato" class="form-control" type="text" name="stato" placeholder="Ovunque">
								</div>
								<br>
								<div class="form-group">
									<label for="citta">Città</label>
									<input id="citta" class="form-control" type="text" name="citta" placeholder="Ovunque">
								</div>
								<br>
								<div class="form-group">
									<label for="check-in-ricerca">Check-in</label>
									<input id="check-in-ricerca" class="form-control" type="date" name="check-in" required>
								</div>
								<br>
								<div class="form-group">
									<label for="check-out-ricerca">Check-out</label>
									<input id="check-out-ricerca" class="form-control" type="date" name="check-out" required>
								</div>
								
								<hr>
								
								<button id="show-more-button" class="btn btn-primary text-center" type="button" style="margin: 0px 10px 0px 10px;">Altre opzioni</button>
								
								<div class="form-group" style="display: none;">
									<br>
									<div class="form-group">
										<label>Stili:</label><br>
										<c:forEach var="stile" items="${requestScope.stili}">
											<input type="checkbox" name="stili" value="${stile.nomeStile}">&nbsp;&nbsp;<label>${stile.nomeStile}</label><br>
										</c:forEach>
									</div>
									<br>
									<div class="form-group">
										<label for="ospiti-ricerca">Numero ospiti</label>										
										<input id="ospiti-ricerca" class="form-control" type="number" min="1" name="numero-ospiti">
									</div>
									<br>
									<div class="form-group">
										<label for="prezzo-minimo">Prezzo minimo</label>
										<input id="prezzo-minimo" class="form-control" type="text" name="prezzo-minimo">
									</div>
									<br>
									<div class="form-group">
										<label for="prezzo-massimo">Prezzo massimo</label>
										<input id="prezzo-massimo" class="form-control" type="text" name="prezzo-massimo">
									</div>
								</div>							
								<hr>							
								<div class="form-group">
									<input class="btn btn-primary" type="submit" value="Cerca">
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="navbar-nav">
					<c:choose>
						<c:when test="${sessionScope.utente != null}">
							<a class="btn btn-primary" href="ProfiloUtente" role="button">Profilo</a>
							<c:if test="${sessionScope.proprietario != null}">
								<a class="btn btn-primary" href="InserimentoInserzione?redirectFlag=true" role="button">Inserisci un'inserzione</a>
							</c:if>
							<c:if test="${sessionScope.amministratore != null}">
								<a class="btn btn-primary" href="Amministrazione" role="button">Amministrazione</a>
							</c:if>
							<a class="btn btn-primary" href="Logout" role="button">Esci</a>
						</c:when>
						<c:otherwise>
							<button class="btn btn-primary" type="button" data-toggle="modal" data-target="#modale-accesso">Accedi</button>
							<button class="btn btn-primary" type="button" data-toggle="modal" data-target="#modale-registrazione">Registrati</button>					
						</c:otherwise>				
					</c:choose>
				</div>
			</div>	
		</nav>
		
		<!-- Modale per il form di accesso -->
		<div id="modale-accesso" class="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title">Accedi</h3>
						<button class="close" type="button" data-dismiss="modal">&times;</button>
					</div>

					<div class="modal-body">
						<form id="form-accesso" action="Accesso" method="post">
							<div class="form-group">
								<label for="email-accesso">Email:</label><span id="email-accesso-valida"></span>
								<input id="email-accesso" class="form-control" type="text" name="email">
							</div>
							<br>
							<div class="form-group">
								<label for="password-accesso">Password:</label><span id="password-accesso-valida"></span>
								<input id="password-accesso" class="form-control" type="password" name="password">
							</div>
							<br><br>
							<div class="form-group">
								<input class="form-control btn btn-primary" type="submit" value="Accedi" style="margin:0px;">
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<div class="from-group">
							<button class="btn btn-danger" type="button" data-dismiss="modal">Chiudi</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Modale per il form di registrazione -->
		<div id="modale-registrazione" class="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title">Registrati</h3>
						<button class="close" type="button" data-dismiss="modal">&times;</button>
					</div>

					<div class="modal-body">
						<ul id="sign-up-nav" class="nav nav-tabs nav-justified" role="tablist">
							<li class="nav-item">
								<a id="home-tab" class="nav-link active sign-up-tab" href="#home" data-toggle="tab" role="tab" aria-controls="home" aria-selected="true">Cliente</a>
							</li>
							<li class="nav-item">
								<a id="profile-tab" class="nav-link sign-up-tab" href="#profile" data-toggle="tab" role="tab" aria-controls="profile" aria-selected="false">Proprietario</a>
							</li>
						</ul>

						<div id="" class="tab-content">
							<div id="home" class="tab-pane fade show active" role="tabpanel" aria-labelledby="home-tab">
								<br>
								<h4>Registrati come cliente</h4>
								<br>
								<div>
									<form id="form-registrazione-cliente" action="Registrazione" method="post" onsubmit="return validaFormCliente()">
										<div class="form-group">											
											<label for="email-cliente">Email:</label><span id="email-cliente-valida"></span>
											<input id="email-cliente" class="form-control" type="text" name="email" required>
										</div>
										<br>
										<div class="form-group">
											<label for="password-cliente">Password(<a href="#" data-toggle ="popover" title="Vincoli password" 
																					data-content="Lunghezza minima di 6 caratteri, tra i quali sia presente almeno una lettera maiuscola, una lettera minuscola ed un numero.">?</a>):</label>
																					<span id="password-cliente-valida"></span>
											<input id="password-cliente" class="form-control" type="password" name="password" required>
										</div>
										<br>
										<div class="form-group">
											<label for="conferma-password-cliente">Conferma password:</label><span id="conferma-password-cliente-valida"></span>
											<input id="conferma-password-cliente" class="form-control" type="password" name="conferma-password" required >
										</div>
										<br>
										<div class="form-group">
											<label for="nome-cliente">Nome:</label>
											<input id="nome-cliente" class="form-control" type="text" name="nome" required>
										</div>
										<br>
										<div class="form-group">
											<label for="cognome-cliente">Cognome:</label>
											<input id="cognome-cliente" class="form-control" type="text" name="cognome" required>
										</div>
										<br>
										<div class="form-group">
											<input type="hidden" name="cliente" value="true">
										</div>
										<hr>
										<div class="form-group">
											<input class="form-control btn btn-primary" type="submit" value="Registrati" style="margin:0px;">
										</div>
									</form>
								</div>
							</div>
							<div id="profile" class="tab-pane fade show" role="tabpanel" aria-labelledby="profile-tab">
								<br>
								<h4>Registrati come proprietario</h4>
								<br>
								<div>
									<form id="form-registrazione-proprietario" action="Registrazione" method="post" onsubmit="return validaFormProprietario()">
										<div class="form-group">
											<label for="email-proprietario">Email</label><span id="email-proprietario-valida"></span>
											<input id="email-proprietario" class="form-control" type="text" name="email" required>
										</div>
										<br>
										<div class="form-group">
											<label for="password-proprietario">Password(<a href="#" data-toggle ="popover" title="Vincoli password" 
																					data-content="Lunghezza minima di 6 caratteri, tra i quali sia presente 
																					almeno una lettera maiuscola, una lettera minuscola ed un numero.">?</a>)</label>
																					<span id="password-proprietario-valida"></span>
											<input id="password-proprietario" class="form-control" type="password" name="password" required>
										</div>
										<br>
										<div class="form-group">
											<label for="conferma-password-proprietario">Conferma password</label><span id="conferma-password-proprietario-valida"></span>
											<input id="conferma-password-proprietario" class="form-control" type="password" name="conferma-password" required>
										</div>
										<br>
										<div class="form-group">
											<label for="nome-proprietario">Nome</label>
											<input id="nome-roprietario" class="form-control" type="text" name="nome" required>
										</div>
										<br>
										<div class="form-group">
											<label for="cognome-proprietario">Cognome</label>
											<input id="cognome-proprietario" class="form-control" type="text" name="cognome" required>
										</div>
										<br>
										<div class="form-group">
											<label for="codice-fiscale">Codice fiscale</label><span id="codice-fiscale-valido"></span>
											<input id="codice-fiscale" class="form-control" type="text" name="codice-fiscale" required>
										</div>
										<hr>
										<div>
											<h4 class="my-4">Metodo di pagamento predefinito</h4>
											<div class="form-group">
												<label for="mp-numero-carta">Numero carta</label><span id="numero-carta-valido"></span>
												<input id="mp-numero-carta" class="form-control" type="text" name="mp-numero-carta" required>
											</div>
											<br>
											<div class="form-group">
												<label for="mp-nome-titolare">Nome titolare</label>
												<input id="mp-nome-titolare" class="form-control" type="text" name="mp-nome-titolare" required>
											</div>
											<br>
											<div class="form-group">
												<label for="mp-cognome-titolare">Cognome titolare</label>
												<input id="mp-cognome-titolare" class="form-control" type="text" name="mp-cognome-titolare" required>
											</div>
											<br>
											<div class="form-group">
												<label for="mp-data-scadenza">Data scadenza</label><span id="data-scadenza-valida"></span>
												<input id="mp-data-scadenza" class="form-control" type="date" name="mp-data-scadenza" required>
											</div>
										</div>
										<hr>
										<div class="form-group">
											<input type="hidden" name="proprietario" value="true">
										</div>
										<div class="form-group">
											<input class="form-control btn btn-primary" type="submit" value="Registrati" style="margin:0px;">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-danger" type="button" data-dismiss="modal">Chiudi</button>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">	
			$(document).ready(function() {
				$('[data-toggle="popover"]').popover();
			});
		
			$('#show-more-button').click(function() {
				$(this).next().toggle();
			});
			
			$('#slider-prezzo').slider({});
							
		
			var emailOk;
			var passwordOk;
			var confermaPasswordOk;
			
			/*
			* Valida le credenziali di accesso
			*/
			function validaFormAccesso() {
				emailOk = false;
				passwordOk = false;
				var statusOk = false;
				
				$.ajax ({
					type: 'POST',
					url: 'AjaxAccesso',
					async: false,
					dataType: 'json',
					data: {
						'email=':  $('#email-accesso').val(),
						'password': + $('#password-accesso').val()
					},
					success: function(response) {
						var email = response[0];
						var password = response[1];
						var status = response[2];
						
						alert('email: ' + email + '\npass: ' + password + '\nstatus: ' + status);
						
						if(email == 'OK' && status == 'OK') {
							display('email-accesso-valida', 'OK', '');
							emailOk = statusOk = true;
						}
						else if(email == 'OK' && status == 'NO') {
							display('email-accesso-valida', 'NO', 'L\'account è stato sospeso e non può effettuare l\'accesso!');
							emailOk = true;
						}
						else {
							display('email-accesso-valida', 'NO', 'L\'account non esiste');
						}
						
						if(passowrd == 'OK'){
							display('password-accesso-valida', 'OK', '');
							passwordOk = true;
						}
						else {
							display('password-accesso-valida', 'NO', 'Password errata');
						}
					}
				});
				
				//alert('email: ' + emailOk + '\npass: ' + passwordOk + '\nstatus: ' + statusOk);
				
				return emailOk && passwordOk && statusOk;
			}
			
			
			/*
			* Valida le credenziali di registrazione del cliente, assicurandosi 
			* che email, password e conferma password 
			*/
			function validaFormCliente() {
				emailOk = false;
				passwordOk = false;
				confermaPasswordOk = false;
				
				validaEmail($('#email-cliente').val(), 'email-cliente-valida');
				passwordOk = validaPassword($('#password-cliente').val(), 'password-cliente-valida')
				confermaPasswordOk = matchPassword($('#password-cliente').val(), $('#conferma-password-cliente').val(), 'conferma-password-cliente-valida')
				
				return emailOk && passwordOk && confermaPasswordOk;
			}

			
			/*
			* Valida le credenziali di registrazione del proprietario, assicurandosi 
			* che email, password e conferma password 
			*/
			function validaFormProprietario() {
				emailOk = false;
				passwordOk = false;
				confermaPasswordOk = false;
				var codiceFiscaleOk = false;
				var numeroCartaOk = false;
				var dataOk = false;
				
				validaEmail($('#email-proprietario').val(), 'email-proprietario-valida');
				passwordOk = validaPassword($('#password-proprietario').val(), 'password-proprietario-valida')
				confermaPasswordOk = matchPassword($('#password-proprietario').val(), $('#conferma-password-proprietario').val(), 'conferma-password-proprietario-valida')
				codiceFiscaleOk = validaCodiceFiscale($('#codice-fiscale').val(), 'codice-fiscale-valido');
				
				numeroCartaOk = validaNumeroCarta($('#mp-numero-carta').val(), 'numero-carta-valido');
				dataOk = validaData($('#mp-data-scadenza').val(),'data-scadenza-valida');
				
				return emailOk && passwordOk && confermaPasswordOk 
					&& codiceFiscaleOk && numeroCartaOk && dataOk;
			}

				
			/*
			* Effettua il match dell'email con un'espressione regolare e 
			* verifica se l'email inserita è già presente nel databse, mediante 
			* chiamata AJAX
			* 
			* @param email l'email da validare
			* @param target id della componente in cui mostrare messaggi di successo/errore
			*/
			function validaEmail(email, target) {
				var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/;
				
				if(email.match(emailRegex)) {
					display(target, 'OK', '');
					
					$.ajax ({
						type: 'POST',
						url: 'AjaxEmail',
						data: 'email=' + email,
						async: false,
						success: function(msg) {
							if(msg == 'OK') {
								display(target, 'OK', '');
								emailOk = true;
							}
							else {
								display(target, 'NO', 'Email già in uso!');
								emailOk = false;
							}
						}
					});
				} 
				else {					
					display(target, 'NO', 'Email non valida!');
					emailOk = false;
				}
				return;
			}
		</script>