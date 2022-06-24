package com.petstore.api.base;

import com.google.gson.Gson;
import com.petstore.api.Configs;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class Base {
    protected Gson gson = new Gson();

    public RequestSpecification requestSpec() {
       return new RequestSpecBuilder()
               .addHeader("Content-Type", "application/json")
               .log(LogDetail.ALL)
               .addFilter(new RestAssuredFilter())
               .setBaseUri(Configs.BASE_URL)
               .build();
    }
}
