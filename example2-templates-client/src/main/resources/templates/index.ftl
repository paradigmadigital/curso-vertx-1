<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Página dinámica | Curso Vertx 2017</title>

    <script src="../static/js/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="../static/js/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="../static/js/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
	<script src='../static/js/vertx-eventbus.js'></script>

	<script>
	
		var eb = new EventBus('/eventbus');
		/** Registraremos nuestros handlers */
		eb.onopen = function() {
	 		eb.registerHandler('happy.goku.level', function(error, message) {
	   			
	 			console.log('message from eventbus: ' + JSON.stringify(message));
	 			var body = message.body;
	   			/* Escribimos el mensaje con la información adecuada */
	 			$('#cpuvalue').html(body.value + " %");
	 		});
	
	 		// Podríamos enviar un mensaje si quisieramos
	 		//eb.send('some-address', {a:"a", b:"b"});
		}
	
	
	</script>    
    
</head>
<body>
<div class="container" role="main">
    <div class="jumbotron">
        <h1>Mi página dinámica con Freemarker</h1>
        <p>${context.myMessage}</p>
    </div>

    <div id="gokuphoto" class="centered">
		<!-- Listado de 5 fotos seguidas -->    	
		<#list 1..5 as i>
		    	<img alt="hello soy Goku ;)" src="../static/images/HappyGoku.png"/>
		</#list>
	</div>

	<!--  Valor de la cpu  -->
	<br/>
	
    <div  class="centered">
		<button class="btn btn-primary" type="button">
		  Uso de CPU <span class="badge" id="cpuvalue">0</span>
		</button>
	</div>

</div>
</body>
</html>