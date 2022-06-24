package com.petstore.api.tests;

import com.petstore.api.actions.PetActions;
import com.petstore.api.actions.StoreActions;
import com.petstore.api.models.Order;
import com.petstore.api.models.OrderStatus;
import com.petstore.api.models.Pet;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Verify Store scenarios")
public class StoreTests {
    PetActions petActions = new PetActions();
    StoreActions storeActions = new StoreActions();

    @Story("Verify order can be placed, retrieved and deleted")
    @Test(groups = {"store", "smoke", "petstore"})
    public void verifyOrderWorkflow() {
        int petId = petActions.addPet(petActions.getPetData())
                .as(Pet.class).getId();

        Order orderDetails = Order.builder()
                .id(new Random().nextInt(1000))
                .petId(petId)
                .quantity(10)
                .shipDate(LocalDateTime.now().atZone(ZoneOffset.UTC).toString())
                .status(OrderStatus.approved)
                .complete(true)
                .build();

        storeActions.placeOrder(orderDetails)
                .then()
                .statusCode(200)
                .body("id", is(orderDetails.getId()))
                .body("quantity", is(orderDetails.getQuantity()));

        storeActions.getOrder(orderDetails.getId())
                .then()
                .statusCode(200)
                .body("id", is(orderDetails.getId()));

        storeActions.deleteOrder(orderDetails.getId())
                .then()
                .statusCode(200);

        storeActions.getOrder(orderDetails.getId())
                .then()
                .statusCode(404);
    }

    @Story("Verify get inventory")
    @Test(groups = {"store", "smoke", "petstore"})
    public void verifyGetInventory() {
        storeActions.getInventory()
                .then()
                .statusCode(200)
                .body("approved", notNullValue())
                .body("placed", notNullValue())
                .body("delivered", notNullValue());
    }

}
