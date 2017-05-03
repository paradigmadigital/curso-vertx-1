package com.vertx.example3.service.sensor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;

import com.vertx.example3.service.sensor.SensorService;
import com.vertx.example3.service.sensor.domain.SensorDTO;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * 
 * @author manuel
 *
 */
public class SensorServiceImpl implements SensorService {

	private static final String COLLECTION = "sensor";

	private final MongoClient client;
	protected final Vertx vertx;

	/**
	 * Builder para la creación de una instancia genérica del servicio
	 *
	 * @author manuel
	 *
	 */
	public static class Builder {
		public SensorService create(Vertx vertx, JsonObject config) {
			return new SensorServiceImpl(vertx, config);
		}
	}

	public SensorServiceImpl(Vertx vertx, JsonObject config) {
		/* Configuración de la conexión http://vertx.io/docs/vertx-mongo-client/java/#_configuring_the_client */
		JsonObject jsonObject = config.getJsonObject("mongo");
		this.client = MongoClient.createNonShared(vertx, jsonObject);
		this.vertx = vertx;
	}

	@Override
	public void saveSensor(SensorDTO sensor, Handler<AsyncResult<SensorDTO>> resultHandler) {

		sensor.setId(new ObjectId().toString());
		client.save(COLLECTION, new JsonObject().put("_id", sensor.getId()).put("name", sensor.getType()).put("description", sensor.getDescription())
				.put("measure", sensor.getMeasure()).put("createdAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())), ar -> {
					if (ar.succeeded()) {
						SensorDTO newSensor = new SensorDTO(sensor);
						resultHandler.handle(Future.succeededFuture(newSensor));
					} else {
						resultHandler.handle(Future.failedFuture(ar.cause()));
					}
				});
	}

	@Override
	public void getSensor(String id, Handler<AsyncResult<SensorDTO>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.findOne(COLLECTION, query, null, ar -> {
			if (ar.succeeded()) {
				if (ar.result() == null) {
					resultHandler.handle(Future.succeededFuture());
				} else {
					SensorDTO sensor = new SensorDTO(ar.result().put("id", ar.result().getString("_id")));
					resultHandler.handle(Future.succeededFuture(sensor));
				}
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public void removeSensor(String id, Handler<AsyncResult<Void>> resultHandler) {
		JsonObject query = new JsonObject().put("_id", id);
		client.removeDocument(COLLECTION, query, ar -> {
			if (ar.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}
}
