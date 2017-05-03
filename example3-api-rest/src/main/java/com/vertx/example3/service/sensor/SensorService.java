package com.vertx.example3.service.sensor;

import com.vertx.example3.service.sensor.domain.SensorDTO;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Definición de la comunicación con el BUS de Vert.x en cuanto a las operaciones que podremos realizar con nuestro sensor
 * 
 * @author manuel
 *
 */
@VertxGen
@ProxyGen
public interface SensorService {

	public static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

	public static final String SERVICE_NAME = "sensor-eb-service";
	public static final String SERVICE_ADDRESS = "service.sensor";

	public abstract void saveSensor(SensorDTO dto, Handler<AsyncResult<SensorDTO>> resultHandler);

	public abstract void getSensor(String id, Handler<AsyncResult<SensorDTO>> resultHandler);

	public abstract void removeSensor(String id, Handler<AsyncResult<Void>> resultHandler);

}
