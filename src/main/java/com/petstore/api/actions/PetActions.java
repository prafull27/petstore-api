package com.petstore.api.actions;

import com.petstore.api.Configs;
import com.petstore.api.base.Base;
import com.petstore.api.models.Category;
import com.petstore.api.models.Pet;
import com.petstore.api.models.PetStatus;
import com.petstore.api.models.Tag;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.apache.logging.log4j.LogManager.getLogger;

public class PetActions extends Base {
    private static final Logger LOG = getLogger(PetActions.class);

    public Pet getPetData() {
        return Pet.builder().id(new Random().nextInt(1000))
                .name("Nugget")
                .category(new Category(89, "CatA"))
                .status(PetStatus.available)
                .tags(Arrays.asList(new Tag(27, "kitty"), new Tag(90, "puppy")))
                .photoUrls(Collections.singletonList("https://demo.testimagedemo.jpg"))
                .build();
    }

    public Response addPet(Pet pet) {
        LOG.info("Adding a pet...");

        return RestAssured.given(requestSpec())
                .body(gson.toJson(pet))
                .post(Configs.PET_ENDPOINT);
    }

    public Response updatePet(Pet pet) {
        LOG.info("Updating pet details for id => {}", pet.getId());

        return RestAssured.given(requestSpec())
                .body(gson.toJson(pet))
                .put(Configs.PET_ENDPOINT);
    }

    public Response getPet(int petId) {
        LOG.info("Getting a pet details for Id => {}", petId);

        return RestAssured.given(requestSpec())
                .pathParam("petId", petId )
                .get(Configs.PET_ENDPOINT + "/{petId}");
    }

    public Response deletePet(int petId) {
        LOG.info("Deleting a pet for Id => {}", petId);

        return RestAssured.given(requestSpec())
                .pathParam("petId", petId )
                .delete(Configs.PET_ENDPOINT + "/{petId}");
    }

    public Response updatePetWithFormData(int petId, String petName, PetStatus petStatus) {
        LOG.info("Updating a pet with id {}", petId);

        return RestAssured.given(requestSpec())
                .pathParam("petId", petId)
                .queryParam("name", petName)
                .queryParam("status", petStatus)
                .post(Configs.PET_ENDPOINT + "/{petId}");
    }

    public Response findPetByStatus(String status) {
        LOG.info("Getting a pet details by status => {}", status);

        return RestAssured.given(requestSpec())
                .queryParam("status", status )
                .get(Configs.PET_ENDPOINT + "/findByStatus");
    }

    public Response findPetByTags(List<String> tags) {
        return RestAssured.given(requestSpec())
                .queryParams("tags", tags)
                .get(Configs.PET_ENDPOINT + "/findByTags");
    }

    public Pet uploadPetImage(File file, int petId) {
        LOG.info("Uploading image for for Id => {}", petId);

        return RestAssured.given(requestSpec())
                .config(RestAssured.config()
                        .encoderConfig(encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .contentType("application/octet-stream")
                .header("accept","application/json")
                .pathParam("petId", petId)
                .body(file)
                .post(Configs.PET_ENDPOINT + "/{petId}/uploadImage")
                .as(Pet.class);
    }
}