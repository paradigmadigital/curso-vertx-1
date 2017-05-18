package com.vertx.example2;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

/**
 * Verticle con un ejemplo de un servidor http + templates + estáticos + push cliente
 * 
 * @author manuel
 *
 */
public class Example2HttpServerVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example2HttpServerVerticle.class);

	/** Dirección en el bus de los mensajes */
	private static final String ADDRESS_GOKU_HAPPY_LEVEL = "happy.goku.level";

	@Override
	public void start(Future<Void> future) throws Exception {
		Router router = Router.router(vertx);

		// Obtenemos las propiedades de configuración del fichero o los valores por defecto
		String host = config().getString("vertx.host", "0.0.0.0");
		int port = config().getInteger("vertx.port", 7777);

		// router.route("/*").handler(LoggerHandler.create());

		/* Handler para el contenido estático */
		StaticHandler staticHandler = StaticHandler.create();
		router.route("/static/*").handler(staticHandler);

		/* Manejo de los templates dinámicos */
		TemplateEngine engine = FreeMarkerTemplateEngine.create();
		router.get("/dynamic/index").handler(routingContext -> {
			routingContext.put("myMessage", "Ejemplo de página con valores de freemarker y sintaxis propia");
			/* Renderizamos la plantilla */
			engine.render(routingContext, "templates/index.ftl", res -> {
				if (res.succeeded()) {
					routingContext.response().end(res.result());
				} else {
					routingContext.fail(res.cause());
				}
			});

		});

		/* Acceso a los datos de modo bidireccional con un servidor de sockets */

		router.route("/eventbus/*").handler(
				SockJSHandler.create(vertx).bridge(new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress(ADDRESS_GOKU_HAPPY_LEVEL))));

		/** Periódicamente dejamos la información de la cpu en el bus */
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		vertx.setPeriodic(5000, event -> {
			/* Publicamos los valores */
			vertx.eventBus().send(ADDRESS_GOKU_HAPPY_LEVEL, new JsonObject().put("value", operatingSystemMXBean.getSystemLoadAverage()));
		});

		// creamos el HTTP server
		vertx.createHttpServer().requestHandler(router::accept).listen(port, host, ar -> {
			if (ar.succeeded()) {
				future.complete();
				LOGGER.info(String.format("Our http server is running at %d", port));
			} else {
				future.fail(ar.cause());
				LOGGER.info(String.format("Our http server is not running !!!!!!!! at %d", port));

			}
		});

	}

}
