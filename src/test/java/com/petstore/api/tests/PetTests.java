package com.petstore.api.tests;

import com.petstore.api.actions.PetActions;
import com.petstore.api.models.Pet;
import com.petstore.api.models.PetStatus;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Verify Pet scenarios")
public class PetTests {
    PetActions petActions = new PetActions();

    @Story("Verify add and update pet details")
    @Test(groups = {"pet", "petstore", "smoke"})
    public void verifyAddUpdatePet() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual)
                .then()
                .statusCode(200)
                .body("id", is(petActual.getId()));

        petActual.setName("Max");
        petActions.updatePet(petActual)
                .then()
                .statusCode(200)
                .body("name", is(petActual.getName()))
                .body("id", is(petActual.getId()));
    }

    @Story("Verify update pet with invalid pet id")
    @Test(groups = {"pet", "petstore"})
    public void verifyUpdatePetWithInvalidPetId() {
        petActions.updatePet(new PetActions().getPetData())
                .then()
                .statusCode(404)
                .body(is("Pet not found"));
    }

    @Story("Verify update pet details with form data")
    @Test(groups = {"pet", "petstore"})
    public void verifyUpdatePetWithFormData() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual)
                .then()
                .statusCode(200);

        String newPetName = "doggie";
        PetStatus newPetStatus = PetStatus.pending;

        petActions.updatePetWithFormData(petActual.getId(), newPetName, newPetStatus)
                .then()
                .statusCode(200)
                .body("name", is(newPetName))
                .body("status", is(newPetStatus.toString()));
    }

    @Story("Verify update pet details with form data, with invalid pet status")
    @Test(groups = {"pet", "petstore"})
    public void verifyUpdatePetWithFormDataWIthInvalidPetStatus() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual)
                .then()
                .statusCode(200);

        String newPetName = "doggie";
        PetStatus newPetStatus = PetStatus.invalid;

        petActions.updatePetWithFormData(petActual.getId(), newPetName, newPetStatus)
                .then()
                .statusCode(405)
                .body("name", is(petActual.getName()))
                .body("status", is(petActual.getStatus()));
    }

    @Story("Verify Get ped by invalid id")
    @Test(groups = {"pet", "petstore"})
    public void verifyGetPetWithInvalidPetId() {
        petActions.getPet(new Random().nextInt(5000))
                .then()
                .statusCode(404)
                .body(is("Pet not found"));
    }

    @Story("Verify Get ped by valid id")
    @Test(groups = {"pet", "petstore"})
    public void verifyGetPetWithValidPetId() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual)
                .then()
                .statusCode(200);

        petActions.getPet(petActual.getId())
                .then()
                .statusCode(200)
                .body("id", is(petActual.getId()))
                .body("name", is(petActual.getName()));
    }

    @Story("Verify delete pet with valid id")
    @Test(groups = {"pet", "petstore"})
    public void verifyDeletePetWithValidPetId() {
        Pet petActual = new PetActions().getPetData();
        petActions.addPet(petActual);
        Assert.assertEquals(petActions.deletePet(petActual.getId()).asString(), "Pet deleted");
        Assert.assertEquals(petActions.getPet(petActual.getId()).statusCode(), 404);
    }

    @Story("Verify delete pet with invalid id")
    @Test(groups = {"pet", "petstore"})
    public void verifyDeletePetWithInvalidPetId() {
        petActions.deletePet(new Random().nextInt(5000))
                .then()
                .statusCode(400);
    }

    @Story("Verify upload image")
    @Test(groups = {"pet", "petstore"})
    public void verifyUploadImg() {
        Pet petActual = petActions.getPetData();
        Pet petResponse = petActions.addPet(petActual)
                .then().body("name", notNullValue())
                .extract().as(Pet.class);

        URL image = getClass().getClassLoader().getResource("petImage.jpg");
        Assert.assertNotNull(image);

        try {
            petResponse = petActions.uploadPetImage(Paths.get(image.toURI()).toFile(), petResponse.getId());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(petResponse.getName());
        Assert.assertEquals(petActual.getPhotoUrls().size() + 1, petResponse.getPhotoUrls().size());
    }

    @Story("Verify find pet by valid status")
    @Test(groups = {"pet", "petstore"})
    public void verifyFindPetByValidStatus() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual);
        Response response = petActions.findPetByStatus(petActual.getStatus().toString());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(1, Arrays.stream(response.as(Pet[].class))
                .filter(pet -> pet.getId() == petActual.getId()).count());
    }

    @Story("Verify find pet by invalid status")
    @Test(groups = {"pet", "petstore"})
    public void verifyFindPetByInvalidStatus() {
        petActions.findPetByStatus("unavailable")
                .then()
                .statusCode(400)
                .body(is("Invalid status value"));
    }

    @Story("Verify find pet by tags")
    @Test(groups = {"pet", "petstore"})
    public void verifyFindPetByTags() {
        Pet petActual = petActions.getPetData();
        petActions.addPet(petActual);

        List<String> tagNames = new ArrayList<>();
        tagNames.add(petActual.getTags().get(0).getName());

        Response response = petActions.findPetByTags(tagNames);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(1, Arrays.stream(response.as(Pet[].class))
                .filter(pet -> pet.getId() == petActual.getId()).count());
    }
}
