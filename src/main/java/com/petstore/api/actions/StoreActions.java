package com.petstore.api.actions;

import com.petstore.api.Configs;
import com.petstore.api.base.Base;
import com.petstore.api.models.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class StoreActions extends Base {

    private static final Logger LOG = getLogger(StoreActions.class);

    public Response placeOrder(Order orderDetails) {
        LOG.info("Placing an order with order id {}", orderDetails.getId());

        return RestAssured.given(requestSpec())
                .body(gson.toJson(orderDetails))
                .post(Configs.STORE_ENDPOINT.concat("/order"));
    }

    public Response getOrder(int orderId) {
        LOG.info("Get an order with order id {}", orderId);

        return RestAssured.given(requestSpec())
                .pathParam("orderId", orderId)
                .get(Configs.STORE_ENDPOINT.concat("/order/{orderId}"));
    }

    public Response deleteOrder(int orderId) {
        LOG.info("Delete an order with order id {}", orderId);

        return RestAssured.given(requestSpec())
                .pathParam("orderId", orderId)
                .delete(Configs.STORE_ENDPOINT.concat("/order/{orderId}"));
    }

    public Response getInventory() {
        LOG.info("Get inventory details");

        return RestAssured.given(requestSpec())
                .get(Configs.STORE_ENDPOINT.concat("/inventory"));
    }
}