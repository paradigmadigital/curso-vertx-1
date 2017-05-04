package com.vertx.example3;

import org.apache.commons.lang3.StringUtils;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.dto.SensorDTO;
import com.vertx.example3.service.syncsensor.verticle.SensorWorkerGetVerticle;

import io.vertx.blueprint.microservice.common.RestAPIVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Verticle con un API REST sencillo de sensores y de
 * 
 * @author manuel
 *
 */
public class Example3HttpServerVerticle extends RestAPIVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(Example3MainVerticle.class);

	private static final String API_PATH = "sensors";

	/** Declaración de los mappings */
	private static final String API_SAVE = String.format("/%s", API_PATH);
	private static final String API_RETRIEVE = String.format("/%s/:id", API_PATH);
	private static final String API_DELETE = String.format("/%s/:id", API_PATH);

	/** ejemplo de worker síncrono */
	private static final String API_SYNC_RETRIEVE = String.format("/%s/sync/:id", API_PATH);

	private SensorService service;

	public Example3HttpServerVerticle() {
	}

	@Override
	public void start(Future<Void> future) throws Exception {

		super.start();
		service = Example3MainVerticle.SENSOR_SERVICE;
		Router router = Router.router(vertx);

		/* Habilitamos el CORS */
		enableCorsSupport(router);

		/* Nos permitirá parsear el body handler para obtener el payload */
		router.route().handler(BodyHandler.create());

		/* Rutas */
		router.post(API_SAVE).handler(this::apiSave);
		router.get(API_RETRIEVE).handler(this::apiGet);
		router.get(API_SYNC_RETRIEVE).handler(this::apiSyncGet);
		router.delete(API_DELETE).handler(this::apiDelete);

		/* Típico ¿Estas vivo? :p */
		enableHeartbeatCheck(router, config());

		// Datos del server de arranque y por defecto
		String host = config().getString("vertx.host", "0.0.0.0");
		int port = config().getInteger("vertx.port", 7777);

		/* Creamos nuestro servidor http */
		createHttpServer(router, host, port).setHandler(future.completer());

		LOGGER.info(" started " + this.getClass().getSimpleName());

	}

	private void apiSave(RoutingContext context) {
		/* Parseamos a nuesto objeto de entrada */
		SensorDTO sensorDTO = new SensorDTO(new JsonObject(context.getBodyAsString()));
		LOGGER.info(" apiSave with values  " + sensorDTO.toString());

		/* Estas comprobaciones serían muy mejorables con la jsr303 */
		if (StringUtils.isBlank(sensorDTO.getDescription()) || StringUtils.isBlank(sensorDTO.getType())) {
			badRequest(context, new IllegalStateException("Sensor bad request"));
		} else {
			service.saveSensor(sensorDTO, ar -> {
				if (ar.succeeded()) {
					SensorDTO newSensorDTO = ar.result();
					JsonObject result = new JsonObject().put("message", String.format("sensor %s saved", newSensorDTO.getId()));
					resultBody(context, result, 200);
				} else {
					internalError(context, ar.cause());
				}
			});
		}
	}

	private void apiGet(RoutingContext context) {
		/* Obtenemos el parámetro de la url */
		String id = context.request().getParam("id");
		service.getSensor(id, resultHandlerNonEmpty(context));
	}

	private void apiDelete(RoutingContext context) {
		/* Obtenemos el parámetro de la url */
		String id = context.request().getParam("id");
		service.removeSensor(id, deleteResultHandler(context));
	}

	/**
	 * Obtenemos de forma síncrona un sensor
	 * 
	 * @param context
	 */
	private void apiSyncGet(RoutingContext context) {
		/* Obtenemos el parámetro de la url */
		String id = context.request().getParam("id");

		vertx.eventBus().send(SensorWorkerGetVerticle.VERTICLE_ADDRESS, new JsonObject().put("id", id), handler -> {
			context.response().putHeader("content-type", CONTENT_TYPE_JSON).end(handler.result().body().toString());
			
		});
	}

}
