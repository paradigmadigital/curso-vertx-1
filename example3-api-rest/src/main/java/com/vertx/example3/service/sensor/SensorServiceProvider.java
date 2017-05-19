package com.vertx.example3.service.sensor;

import com.vertx.example3.service.sensor.impl.SensorServiceImpl;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Provider para realizar el singleton de la implementación de los sensores
 * 
 * @author manuel
 *
 */
public final class SensorServiceProvider {

	private SensorService sensorService;

	/* Singleton */
	private static SensorServiceProvider instance = new SensorServiceProvider();

	public synchronized SensorService init(Vertx vertx, JsonObject config) {
		setSensorService(new SensorServiceImpl(vertx, config));
		return getSensorService();
	}

	public static SensorServiceProvider getInstance() {
		return instance;
	}

	private SensorServiceProvider() {
	}

	public SensorService getSensorService() {
		return sensorService;
	}

	private void setSensorService(SensorService SensorService) {
		this.sensorService = SensorService;
	}

}
