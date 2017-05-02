package com.vertx.example3;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.SensorServiceVertxEBProxy;
import com.vertx.example3.service.sensor.SensorServiceVertxProxyHandler;
import com.vertx.example3.service.sensor.impl.SensorServiceImpl;

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

	/** Instancia del servicio unica para facilitar (al ser un elemento con persistencia cada vez que creamos uno creamos un cliente de conexión) */
	public static SensorService SENSOR_SERVICE = null;

	@Override
	public void start() throws Exception {
		super.start();
		/**
		 * En este caso no sincronizamos nada ya que será un único hilo el que lo arranque, pero habría que preveer está situación, en este punto podríamos usar
		 * cualquier inyector de dependencias
		 */
		SENSOR_SERVICE = new SensorServiceImpl.Builder().create(vertx, config());

		/* INICIO DESCOMENTAR PARA HACER USO DEL BUS EN LAS OPERACIONES CON LA GENERACIÓN DE VERT.X */
		/* Registramos el servicio */
		ProxyHelper.registerService(SensorService.class, vertx, new SensorServiceImpl.Builder().create(vertx, config()), SensorService.SERVICE_ADDRESS);

		/* Create the proxy interface to HelloWorldService. */
		SENSOR_SERVICE = ProxyHelper.createProxy(SensorService.class, vertx, SensorService.SERVICE_ADDRESS);
		
		/* FIN DESCOMENTAR PARA HACER USO DEL BUS EN LAS OPERACIONES CON LA GENERACIÓN DE VERT.X */
		
		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setConfig(config());
		/** Importante observar las trazas y ver la asignación que se nos ha realizado del Event Loop */
		//deploymentOptions.setInstances(Runtime.getRuntime().availableProcessors() * 2);
		/* Desplegamos nuestro verticle con los datos adecuados */
		vertx.deployVerticle(Example3HttpServerVerticle.class.getName(), deploymentOptions, ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format("Deployment verticle  ok Example3HttpServerVerticle "));
				// future.complete();
			} else {
				LOGGER.info(String.format("Deployment verticle ko Example3HttpServerVerticle " + ar.cause()));
				// future.fail(ar.cause());
			}
		});

	}

}
