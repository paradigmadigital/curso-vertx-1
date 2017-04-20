package com.vertx.example1;

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
public class Example1MainVerticle extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example1MainVerticle.class);

	@Override
	public void start(Future<Void> future) throws Exception {

		/* Http server verticle */
		String verticleName = Example1HttpServerVerticle.class.getName();
		
		/* Opciones del servidor http */
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(16);
		vertx.deployVerticle(verticleName, options, ar -> {
			if (ar.succeeded()) {
				future.complete();
				LOGGER.info(String.format("Deployment verticle %s ok ", verticleName));
			} else {
				future.fail(ar.cause());
				LOGGER.info(String.format("Deployment verticle %s ko ", verticleName));

			}
		});
	}

}
