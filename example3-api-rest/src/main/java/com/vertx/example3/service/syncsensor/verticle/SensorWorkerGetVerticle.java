package com.vertx.example3.service.syncsensor.verticle;

import org.apache.commons.lang3.StringUtils;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.domain.SensorDTOConverter;
import com.vertx.example3.service.sensor.dto.SensorDTO;
import com.vertx.example3.service.syncsensor.impl.SensorSyncServiceImpl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Ejemplo de verticle worker para la operación get con configuración mediante anotación
 * 
 *
 * @author manuel
 *
 */
public class SensorWorkerGetVerticle extends AbstractVerticle implements Handler<Message<JsonObject>> {

	public static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

	/** Dirección del verticle dentro del Bus */
	public static final String VERTICLE_ADDRESS = "verticle.worker.get.sensor";

	/* Referencia al servicio (candidato a singleton) */
	private SensorService service;

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(VERTICLE_ADDRESS, this);
		service = new SensorSyncServiceImpl.Builder().create(vertx, config());
		LOGGER.info(String.format("Service  %s started.", VERTICLE_ADDRESS));
	}

	@Override
	public void handle(Message<JsonObject> event) {
		
		JsonObject body = event.body();
		/* Obtenemos el id */
		String id = body.getString("id");

		LOGGER.info(String.format(" get worker sync data", id));
		
		/* Resultado */
		JsonObject resultObject = new JsonObject();
		if (StringUtils.isBlank(id)) {
			resultObject.put("error", " required id");
		} else {
			service.getSensor(id, handler -> {
				if (handler.succeeded()) {
					SensorDTOConverter.toJson(handler.result(), resultObject);
				} else {
					resultObject.put("error", handler.cause().getMessage());
				}
				event.reply(resultObject);

			});
		}
	}
}
