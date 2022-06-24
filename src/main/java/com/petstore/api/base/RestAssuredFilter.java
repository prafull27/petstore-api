package com.petstore.api.base;

import com.petstore.api.actions.PetActions;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class RestAssuredFilter implements Filter {
    private static final Logger LOG = getLogger(PetActions.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        LOG.info("{} : {}\nRequest Body => {}\nResponse status code => {}\nResponse Body => {}",
                requestSpec.getMethod(), requestSpec.getURI(), requestSpec.getBody(),
                response.getStatusCode(), response.getBody().print());

        return response;
    }
}
