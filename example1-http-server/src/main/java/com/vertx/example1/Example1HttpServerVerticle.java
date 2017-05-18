package com.vertx.example1;



import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Verticle con un ejemplo de un servidor http
 * 
 * @author manuel
 *
 */
public class Example1HttpServerVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example1HttpServerVerticle.class);

	/** Nombre de la funcionalidad del contador */
	private static final String COUNTER_PARAM_NAME = "counter";
	
	@Override
	public void start(Future<Void> future) throws Exception {
		Router router = Router.router(vertx);

		// Obtenemos las propiedades de configuración del fichero o los valores por defecto
		String host = config().getString("vertx.host", "0.0.0.0");
		int port = config().getInteger("vertx.port", 7777);

		/* Cualquier petición hacemos que que responda esto */
		router.get("/test/*").handler(context -> {
			LOGGER.info("Executing mapping /test/*");
			context.next();
		});

		router.get("/test/test1").handler(context -> {
			LOGGER.info("Executing mapping /test/test1");
			String payload = new JsonObject().put("hello", "Hello world !!!").encode();
			context.response().putHeader("content-type", "application/json").end(payload);
		});

		router.get("/ping").handler(context -> {
			LOGGER.info("Executing mapping /ping");
			String payload = new JsonObject().put("uuid", UUID.randomUUID().toString()).encode();
			context.response().putHeader("content-type", "application/json").end(payload);
		});

		/* Endpoin de incrementar en uno un valor en el bus */
		router.get("/add/:counter").handler(this::addOneInSharedDataBus);
		
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

	
	/**
	 * Sumaremos uno al valor existente en la información compartida en el Bus
	 * 
	 * @param context
	 */
	private void addOneInSharedDataBus(RoutingContext context) {
		try {
			/* Obtenemos el path param */
			
			@Nullable
			String param = context.request().getParam(COUNTER_PARAM_NAME);
			int increase = Integer.valueOf(param);
			
			/* Local map dentro el bus */
			LocalMap<String, String> map = vertx.sharedData().getLocalMap("myMap");
			
			/* Obtenemos el valor actual*/
			String stringCounter = map.get(COUNTER_PARAM_NAME);
			Integer counter = increase;
			if (StringUtils.isNotBlank(stringCounter)){
				counter += Integer.valueOf(stringCounter);
			}
			
			/* Ponemos el valor */
			map.put(COUNTER_PARAM_NAME, String.valueOf(counter));
			LOGGER.debug("Valor del counter " + counter);
			
			/* Payload de respuesta */
			String payload = new JsonObject().put(COUNTER_PARAM_NAME, counter).encode();
			context.response().putHeader("content-type", "application/json").end(payload);
			
		} catch (Exception ex) {
			context.fail(400);
		}
	}

}
