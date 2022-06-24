package com.petstore.api.tests;

import com.petstore.api.actions.UserActions;
import com.petstore.api.models.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;

@Epic("Verify User scenarios")
public class UserTests {
    UserActions userActions = new UserActions();

    @Story("Verify user can login")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyUserLogin() {
        userActions.userLogin("Alex", "Berlin")
                .then()
                .statusCode(200)
                .body(containsString("Logged in user session:"));
    }

    @Story("Verify create user")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyCreateUser() {
        User actualUser = userActions.getUserData();

        userActions.createUser(actualUser)
                .then().statusCode(200)
                .body("id", is(actualUser.getId()))
                .body("username", is(actualUser.getUsername()));
    }

    @Story("Verify create user with list")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyCreateUserWithList() {
        User firstUser = userActions.getUserData();
        User secondUser = userActions.getUserData();

        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        userActions.createUserWithList(userList)
                .then().statusCode(200)
                .body("size()", is(2));
    }

    @Story("Verify get and delete user APIs")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyGetAndDeleteUser() {
        User actualUser = userActions.getUserData();

        userActions.createUser(actualUser)
                .then().statusCode(200);

        userActions.getUser(actualUser.getUsername())
                .then()
                .statusCode(200)
                .body("id", is(actualUser.getId()))
                .body("username", is(actualUser.getUsername()));

        userActions.deleteUser(actualUser.getUsername())
                .then()
                .statusCode(200);
    }

    @Story("Verify update user")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyUpdateUser() {
        User actualUser = userActions.getUserData();

        userActions.createUser(actualUser)
                .then().statusCode(200);

        actualUser.setFirstName("Rohit");
        actualUser.setLastName("Sharma");

       userActions.updateUser(actualUser.getUsername(), actualUser)
               .then()
               .statusCode(200)
               .body("firstName", is(actualUser.getFirstName()))
               .body("lastName", is(actualUser.getLastName()));
    }

    @Story("Verify delete user when invalid username is provided")
    @Test(groups = {"user", "smoke", "petstore"})
    public void verifyDeleteUserWithInvalidUser() {
        userActions.deleteUser("nonexistinguser".concat(String.valueOf(new Random().nextInt(100))))
                .then()
                .statusCode(404);
    }
}
