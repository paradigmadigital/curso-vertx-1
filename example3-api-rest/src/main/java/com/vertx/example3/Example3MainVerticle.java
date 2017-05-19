package com.vertx.example3;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.SensorServiceProvider;
import com.vertx.example3.service.syncsensor.verticle.SensorWorkerGetVerticle;

import io.vertx.blueprint.microservice.common.BaseMicroserviceVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * Verticle que arrancará el resto de los de la aplicación
 * 
 * @author manuel
 *
 */
public class Example3MainVerticle extends BaseMicroserviceVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example3MainVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();
		/* Creamos una única instancia de nuestro servicio de sensores */
		SensorServiceProvider sensorServiceProvider = SensorServiceProvider.getInstance();
		sensorServiceProvider.init(vertx, config());

		/* Registramos el servicio (Para el ejemplo del uso del bus ) */
		ProxyHelper.registerService(SensorService.class, vertx, sensorServiceProvider.getSensorService(), SensorService.SERVICE_ADDRESS);

		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setConfig(config());

		/** Importante observar las trazas y ver la asignación que se nos ha realizado del Event Loop = siempre el mismo hilo */
		deploymentOptions.setInstances(1);
		// deploymentOptions.setInstances(Runtime.getRuntime().availableProcessors() * 2);

		/* Desplegamos nuestro verticle http server con los datos adecuados */
		vertx.deployVerticle(Example3HttpServerVerticle.class.getName(), deploymentOptions, ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format("Deployment verticle  ok Example3HttpServerVerticle "));
			} else {
				LOGGER.info(String.format("Deployment verticle ko Example3HttpServerVerticle " + ar.cause()));
			}
		});

		/* Ejemplo de verticle worker (esto se debería de componer en el arranque global de la aplicación controlando el resultado del despliegue) */
		deploySyncWorker();
	}

	/**
	 * Despliegue de otro verticle de tipo worker con operación síncrona
	 * 
	 */
	private void deploySyncWorker() {

		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setConfig(config());
		/* Podemos probar si lo asignamos como no worker ... que pasaría ? */
		deploymentOptions.setWorker(true);
		deploymentOptions.setInstances(5);

		/* Desplegamos nuestro verticle http server con los datos adecuados */
		vertx.deployVerticle(SensorWorkerGetVerticle.class.getName(), deploymentOptions, ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format("Deployment verticle  ok SensorWorkerGetVerticle "));
			} else {
				LOGGER.info(String.format("Deployment verticle ko SensorWorkerGetVerticle " + ar.cause()));
			}
		});

	}

}
