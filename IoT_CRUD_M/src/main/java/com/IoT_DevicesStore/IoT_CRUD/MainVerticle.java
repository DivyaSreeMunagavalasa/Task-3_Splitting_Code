package com.IoT_DevicesStore.IoT_CRUD;

import io.vertx.core.Vertx;
import io.vertx.core.AsyncResult;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DatabaseService databaseService = new DatabaseService(vertx);

    // Deploying PostgresVerticle first.....
    vertx.deployVerticle(new PostgresVerticle(databaseService), res -> {
      if (res.succeeded()) {
        logger.info("PostgresVerticle deployed successfully.");

        // Once PostgresVerticle is deployed, deploying APIVerticle.....
        vertx.deployVerticle(new APIVerticle(), result -> {
          if (result.succeeded()) {
            logger.info("APIVerticle deployed successfully.");
          } else {
            logger.error("Failed to deploy APIVerticle: " + result.cause());
          }
        });

      } else {
        logger.error("Failed to deploy PostgresVerticle: " + res.cause());
      }
    });
  }
}
