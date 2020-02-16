<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="pageName" value="Orion"/>
</jsp:include>

<c:set var="utente" value="${sessionScope.utente}"/>
<div class="container error-box">
	<c:set var="errore" value="${requestScope.errore}"/>
	<c:if test="${errore != null}">
		<span style="color: red;">${errore}</span>
	</c:if>
</div>

<div class="container">
	<form action="InserimentoInserzione" method="post" enctype="multipart/form-data" accept-charset="utf-8" onsubmit="return checkImages()">
		<div class="form-group">
			<label for="stato-inserzione">Stato</label>
			<input id="stato-inserzione" class="form-control" type="text" name="stato" required>			
		</div>
		<div class="form-group">
			<label for="regione-inserzione">Regione</label>
			<input id="regione-inserzione" class="form-control" type="text" name="regione" required>			
		</div>
		<div class="form-group">
			<label for="città-inserzione">Città</label>
			<input id="città-inserzione" class="form-control" type="text" name="citta" required>			
		</div>
		<div class="form-group">
			<label for="cap-inserzione">CAP</label>
			<input id="cap-inserzione" class="form-control" type="text" name="cap" required>			
		</div>
		<div class="form-group">
			<label for="strada-inserzione">Strada</label>
			<input id="strada-inserzione" class="form-control" type="text" name="strada" required>			
		</div>
		<div class="form-group">
			<label for="numero-civico-inserzione">Numero civico</label>
			<input id="numero-civico-inserzione" class="form-control" type="text" name="numero-civico" required>			
		</div>		
		<div class="form-group">
			<label for="prezzo-inserzione">Prezzo giornaliero</label>
			<input id="prezzo-inserzione" class="form-control" type="number" min="1" step="0.01" name="prezzo" required>			
		</div>
		<div class="form-group">
			<label for="numero-ospiti-inserzione">Numero ospiti</label>
			<input id="numero-ospiti-inserzione" class="form-control" type="number" min="1" name="numero-ospiti" required>			
		</div>
		<div class="form-group">
			<label for="metratura-inserzione">Metratura</label>
			<input id="metratura-inserzione" class="form-control" type="number" name="metratura" required>			
		</div>
		<div class="form-group">
			<label for="descrizione-inserzione">Descrizione</label>
			<textarea id="descrizione-inserzione" class="form-control" name="descrizione" required></textarea>		
		</div>
		<div class="form-group">
			<label>Stili:</label><br>
			<c:forEach var="stile" items="${requestScope.stiliInserzione}">
				<input type="checkbox" name="stili" value="${stile.nomeStile}">&nbsp;&nbsp;<label>${stile.nomeStile}</label><br>
			</c:forEach>
		</div>
		<div class="from-group" style="margin:5px; padding:5px;">
			<label for="immagini">Immagini</label><br>
			<input id="immagini" type="file" name="file" 
				accept="image/png image/jpg image/jpeg" multiple>
		</div>		
		<div class="form-group">
			<input class="form-control" type="hidden" name="id-proprietario" value="${utente.idUtente}">
		</div>
		<br>
		<div class="form-group">
			<input class="btn btn-primary" type="submit" value="Inserisci inserzione" style="margin:0px;">
		</div>
	</form>
</div>

<script>
	function checkImages() {
		var images = document.getElementById('immagini').files;
		for(var i = 0; i < files[i].name; i++) {
			var filename = files[i].name;
			var ext = filename.substring(filename.lastIndexOf(".") + 1);
			if(xt != "png" && ext != "jpg" && ext != "jpeg") {
				alert("Il formato di una o più immagini non è supportato.");
				return false;
			}
		}
		return true;
	}
</script>

<jsp:include page="/WEB-INF/view/footer.jsp"></jsp:include>