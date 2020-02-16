<%@ 
	page isELIgnored = "false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	import="java.util.*, model.beans.*"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp">
	<jsp:param name="pageName" value="Orion"/>
</jsp:include>


<!-- Automatic carousel -->
<div id="carouselBanner" class="carousel slide z-depth-1-half"
	data-ride="carousel">
	<div class="carousel-inner">
		<div class="carousel-item active">
			<img class="d-block w-100" src="assets\b1.jpg">
		</div>
		<div class="carousel-item">
			<img class="d-block w-100" src="assets\b2.jpg">
		</div>
		<div class="carousel-item">
			<img class="d-block w-100" src="assets\b3.jpg">
		</div>
	</div>
	<a class="carousel-control-prev" href="#carouselBanner" role="button"
		data-slide="prev"> <span class="carousel-control-prev-icon"
		aria-hidden="true"></span> <span class="sr-only">Precedente</span>
	</a> 
	<a class="carousel-control-next" href="#carouselBanner" role="button"
		data-slide="next"> <span class="carousel-control-next-icon"
		aria-hidden="true"></span> <span class="sr-only">Successiva</span>
	</a>
</div>

<!-- Area inserzioni in evidenza -->
<br>
<div class="main">
	<div class="row">
		<c:forEach var="entry" items="${requestScope.mappaInserzioni}">
			<div class="col-md-2">
				<div class="card mb-5 box-shadow" style="height: 100%;">
					<div>
						<img class="card-img-top" src="${((entry.value != null) && (! empty entry.value)) ? 
							entry.value.get(0).pathname : 'assets/no_image.jpg'}" 
							data-holder-rendered="true" style="height:225px; width: 100%; display:block;">
					</div>
					<div class="card-body">
						<p class="card-text">${entry.key.getDescrizione()}</p>
					</div>
					<div class="card-footer">
						<a class="btn btn-primary center-block" href="Inserzione?ID=${entry.key.getIdInserzione()}" role="button">Scopri di più</a>
						<br>
					</div>
				</div>
			</div>
			
		</c:forEach>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('#modale-modifiche').modal('show');
		jQuery.fn.carousel.Constructor.TRANSITION_DURATION = 2000;
	});
	
	$('.carousel').carousel({
		interval: 5000
	});
</script>

<jsp:include page="footer.jsp"></jsp:include>