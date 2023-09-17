package com.IoT_DevicesStore.IoT_CRUD;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  Vertx vertx;
  DatabaseService databaseService;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    this.vertx = vertx;
    databaseService = new DatabaseService(vertx);
    CompositeFuture.all(
      vertx.deployVerticle(new APIVerticle()),
      vertx.deployVerticle(new PostgresVerticle(databaseService))
    ).onComplete(testContext.succeedingThenComplete());
  }

  @Test
  void verticles_deployed(Vertx vertx, VertxTestContext testContext) {
    testContext.completeNow();
  }
}
