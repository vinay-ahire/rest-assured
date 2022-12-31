package com.vahire.ra;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;

public class PostRequest {

    /**
     * <pre>
     * Set up the base url of the REST API under test.
     * Default value of baseURI = "http://localhost".
     * </pre>
     */
    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    /**
     * <pre>
     * Purpose          :   Send a POST request to create a user and validate the response.
     * Request Method   :   POST
     * Path             :   "/api/users"
     * Sample response  :   "resources/GET-sample-response-single-user.json"
     * </pre>
     *
     * Assertion:
     * <ol>
     *      <li> Status code = 201. </li>
     *      <li> Verify if the response contains keys id, name, job, createdAt. </li>
     *      <li> Verify if name = 'vinay' & job = 'leader' in the response. </li>
     *      <li> Verify if id is not empty and is a string. </li>
     * </ol>
     */
    @Test(description = "Create user using a POST request and validate the response.")
    public void createUserRequestBodyAsString() {

        String requestBody = "{ \"name\": \"vinay\", \"job\": \"leader\" }";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("api/users")
                .then()
                .statusCode(201)
                .assertThat()
                .body("", hasKey("id"))
                .body("", hasKey("name"))
                .body("", hasKey("job"))
                .body("", hasKey("createdAt"))
                .body("name", equalTo("vinay"))
                .body("job", equalTo("leader"))
                .body("id", is(not(emptyOrNullString())))
                .body("id", isA(String.class));
    }

    /**
     * <pre>
     * Purpose          :   Send a POST request to create a user and validate the response.
     * Request Method   :   POST
     * Path             :   "/api/users"
     * Sample response  :   "resources/GET-sample-response-single-user.json"
     * </pre>
     *
     * Assertion:
     * <ol>
     *      <li> Status code = 201. </li>
     *      <li> Verify if the response contains keys id, name, job, createdAt. </li>
     *      <li> Verify if name = 'vinay' & job = 'leader' in the response. </li>
     *      <li> Verify if id is not empty and is a string. </li>
     * </ol>
     */
    @Test(description = "Get single user details and print the response.")
    public void createUserUsingRequestBodyFromJSONFile() {

        File createUserJsonFile = new File("src/test/resources/data/create_user.json");

        given()
                .header("Content-Type", "application/json")
                .body(createUserJsonFile)
                .post("api/users")
                .then()
                .statusCode(201)
                .assertThat()
                .body("", hasKey("id"))
                .body("", hasKey("name"))
                .body("", hasKey("job"))
                .body("", hasKey("createdAt"))
                .body("name", equalTo("vinay"))
                .body("job", equalTo("leader"))
                .body("id", is(not(emptyOrNullString())))
                .body("id", isA(String.class));
    }

}
