/*
 * Verifica se la password rispetta un'espressione regolare 
 * 
 * @param password la password da validare
 * @param target id della componente in cui mostrare messaggi di successo/errore
 */
function validaPassword(password, target) {
	var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\S+$).{6,}$/;

	if(password.match(regex)) {
		display(target, 'OK', '');
		return true;
	}
	else {
		display(target, 'NO', 'Password non valida');
		return false;
	}
}


/*
 * Verifica se le due password corrispondono
 * 
 * @param password la prima password
 * @param confermaPassword la seconda password
 * @param target id della componente in cui mostrare messaggi di successo/errore
 */
function matchPassword(password, confermaPassword, target) {
	if(password == confermaPassword) {
		display(target, 'OK', '');
		return true;
	} 
	else {
		display(target, 'NO', 'Le password non corrispondono!');
		return false;
	}
}


/*
 * Verifica se il codice fiscale rispetta un'espressione regolare 
 * 
 * @param codiceFiscale il codice fiscale da validare
 * @param target id della componente in cui mostrare messaggi di successo/errore
 */
function validaCodiceFiscale(codiceFiscale, target) {
	var regex = /^([\w\d]+[\.-][\w\d]+)+$/;
	
	if(codiceFiscale.match(regex)) {
		display(target, 'OK', '');
		return true;
	}
	else {
		display(target, 'NO', 'Codice fiscale non valido!');
		return false;
	}
}


/*
 * Verifica se il numero della carta rispetta un'espressione regolare 
 * 
 * @param numeroCarta il numero della carta da validare
 * @param target id della componente in cui mostrare messaggi di successo/errore
 */
function validaNumeroCarta(numeroCarta, target) {
	var regex = /^(\d+[\.-]\d+)+$/;
	
	if(numeroCarta.match(regex)) {
		display(target, 'OK', '');
		return true;
	}
	else {
		display(target, 'NO', 'Numero carta non valido!');
		return false;
	}
}


/*
 * Verifica se la data inserita è valida e non è inferiore
 * alla data di oggi
 */
function validaData(dataString, target) {	
	var today = new Date();
	var data = new Date(dataString);
	
	if(data > today) {
		display(target, 'OK', '');
		return true;
	}
	else {
		display(target, 'NO', 'Carta scaduta!');
		return false;
	}
}


/*
 * Funzione di utility per mostrare messaggi 
 * di successo/errore
 * 
 * @param id della componente nella quale mostrare il messaggio
 * @param result successo/errore
 * @param message il messaggio da mostrare
 */
function display(id, result, message) {
	$message = $('#' + id);

	if(result == 'OK') {
		$message.html('');
		$message.css('display', 'none');
	} else {
		$message.html(message);
		$message.css({
			'display' : 'block',
			'color' : 'red'
		});
	}
}