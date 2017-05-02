package com.vertx.example2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Verticle que arrancará el resto de los de la aplicación
 * 
 * @author manuel
 *
 */
public class Example2MainVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example2MainVerticle.class);

	@Override
	public void start(Future<Void> future) throws Exception {

		/* Http server verticle */
		String verticleName = Example2HttpServerVerticle.class.getName();
		
		/* Opciones del servidor http */
		DeploymentOptions options = new DeploymentOptions();
		/* Asignamos como instancias el numero de procesadores por 2 (Patrón multireactor) */
		options.setConfig(config());
		options.setInstances(Runtime.getRuntime().availableProcessors() * 2);
		vertx.deployVerticle(verticleName, options, ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format("Deployment verticle %s ok ", verticleName));
				future.complete();
			} else {
				LOGGER.info(String.format("Deployment verticle %s ko ", verticleName));
				future.fail(ar.cause());
			}
		});
	}

}
