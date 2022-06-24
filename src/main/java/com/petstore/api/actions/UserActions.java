package com.petstore.api.actions;

import com.petstore.api.Configs;
import com.petstore.api.base.Base;
import com.petstore.api.models.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

import static org.apache.logging.log4j.LogManager.getLogger;

public class UserActions extends Base {

    private static final Logger LOG = getLogger(UserActions.class);

    public User getUserData() {
        return User.builder().id(new Random().nextInt(1000))
                .username("ialex")
                .firstName("Alex")
                .lastName("John")
                .phone("12397767")
                .email("alexjohn@test.com")
                .password("Berlin")
                .userStatus(1)
                .build();
    }

    public Response createUser(User user) {
        LOG.info("Creating a customer with id {}", user.getId());

        return RestAssured.given(requestSpec())
                .body(gson.toJson(user))
                .post(Configs.USER_ENDPOINT);
    }

    public Response createUserWithList(List<User> user) {
        LOG.info("Creating customers with with list");

        return RestAssured.given(requestSpec())
                .body(gson.toJson(user))
                .post(Configs.USER_ENDPOINT.concat("/createWithList"));
    }

    public Response userLogin(String username, String password) {
        LOG.info("Logging in with user {}", username);

        return RestAssured.given(requestSpec())
                .queryParam("username", username)
                .queryParam("password", password)
                .get(Configs.USER_ENDPOINT + "/login");
    }

    public Response logout() {
        LOG.info("Logging out the user");

        return RestAssured.given(requestSpec())
                .get(Configs.USER_ENDPOINT + "/logout");
    }

    public Response getUser(String username) {
        LOG.info("Get an user with username {}", username);

        return RestAssured.given(requestSpec())
                .pathParam("username", username)
                .get(Configs.USER_ENDPOINT.concat("/{username}"));
    }

    public Response updateUser(String username, User user) {
        LOG.info("Updating an user with username {}", username);

        return RestAssured.given(requestSpec())
                .pathParam("username", username)
                .body(user)
                .put(Configs.USER_ENDPOINT + "/{username}");
    }

    public Response deleteUser(String username) {
        LOG.info("Deleting an user with username {}", username);

        return RestAssured.given(requestSpec())
                .pathParam("username", username)
                .delete(Configs.USER_ENDPOINT + "/{username}");
    }
}