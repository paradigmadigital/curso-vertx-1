package com.vertx.example3.service.syncsensor.verticle;

import org.apache.commons.lang3.StringUtils;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.SensorServiceProvider;
import com.vertx.example3.service.sensor.dto.SensorDTO;
import com.vertx.example3.service.sensor.dto.SensorDTOConverter;
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

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(VERTICLE_ADDRESS, this);
		
		LOGGER.info(String.format("Service  %s started.", VERTICLE_ADDRESS));
	}

	@Override
	public void handle(Message<JsonObject> event) {
		/* Referencia al servicio (ojo si estamos usando la comunicación por el bus o bien sin él) */	
		SensorService service = new SensorSyncServiceImpl(vertx, config());
		
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
				SensorDTO result = handler.result();
				if (handler.succeeded() && result != null) {
					SensorDTOConverter.toJson(result, resultObject);
				} else {
					resultObject.put("error", "Not Found element");
				}
				event.reply(resultObject);

			});
		}
	}
}
