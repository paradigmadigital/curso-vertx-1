package com.vertx.example3.service.sensor;

import com.vertx.example3.service.sensor.domain.SensorDTO;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Definición de la comunicación con el BUS de Vert.x en cuanto a las operaciones que podremos realizar con nuestro sensor
 * 
 * @author manuel
 *
 */
@VertxGen
@ProxyGen
public interface SensorService {

	String SERVICE_NAME = "sensor-eb-service";
	String SERVICE_ADDRESS = "service.sensor";

	void saveSensor(SensorDTO dto, Handler<AsyncResult<SensorDTO>> resultHandler);

	void getSensor(String id, Handler<AsyncResult<SensorDTO>> resultHandler);

	void removeSensor(String id, Handler<AsyncResult<Void>> resultHandler);

}
