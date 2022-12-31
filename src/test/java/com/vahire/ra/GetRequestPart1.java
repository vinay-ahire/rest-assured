package com.vahire.ra;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * <p>
 * Test class to send a GET request and validate the response.
 * <ul>
 *     <li> Simple GET request. </li>
 *     <li> GET request with path parameter. </li>
 *     <li> GET request with query parameter. </li>
 *     <li> GET request with HTTP 404 error response. </li>
 * <ul/>
 *
 * In order to use REST Assured effectively its recommended ti statically import methods from following:
 * <ol>
 *      <li> import static io.restassured.RestAssured.*; </li>
 *      <li> import static org.hamcrest.Matchers.*; </li>
 * </ol>
 * </p>
 */
public class GetRequestPart1 {

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
     * Purpose          :   Send a GET request and print the response.
     * Request Method   :   GET
     * Path             :   "/api/users"
     * Sample response  :   "resources/GET-sample-response-single-user.json"
     * </pre>
     */
    @Test(description = "Get single user details and print the response.")
    public void getUserListAndPrintTheResponse() {
        given()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .prettyPrint();
    }

    /**
     * <pre>
     *  Purpose          :   Validate a GET request which fetches the user details with given userId.
     *  Request Method   :   GET
     *  Path             :   "/api/users/2" -> Note '2' is passed as a path param in the url.
     *  Sample response  :   "resources/GET-sample-response-single-user.json"
     *  </pre>
     *
     * Assertion:
     * <ol>
     *      <li> Status code = 200. </li>
     *      <li> Verify if the response.data contains keys id, email, first_name, last_name, avatar. </li>
     *      <li> Verify if the response.data does not contains keys url, text. </li>
     *      <li> Verify if the response contains id = 2, first_name = "Janet". </li>
     *      <li> Verify if id is integer and first_name is String. </li>
     * </ol>
     */
    @Test(description = "Get single user details with userId as a path parameter.")
    public void getSingleUserWithPathParam() {
        int userId = 2;
        given()
                .pathParam("userId", userId)
                .get("/api/users/{userId}")
                .then()
                .statusCode(200)
                .assertThat()
                .body("data", hasKey("id"))
                .body("data", hasKey("email"))
                .body("data", hasKey("first_name"))
                .body("data", hasKey("last_name"))
                .body("data", hasKey("avatar"))
                .body("data", not(hasKey("url")))
                .body("data", not(hasKey("text")))
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.id", isA(Integer.class))
                .body("data.first_name", isA(String.class));
    }

    /**
     * <pre>
     * Purpose          :   Validate a GET request which fetches the list of users with query parameter.
     * Request Method   :   GET
     * Path             :   "/api/users?page=2" -> Note 'page=2' is passed as a query param in the url.
     * Sample response  :   "resources/GET-sample-response-list-of-users.json"
     * </pre>
     *
     * Assertion:
     * <ol>
     * <li> Status code = 200. </li>
     * <li> Verify if the response.data contains keys page, per_page, total, total_pages. </li>
     * <li> Verify if the response contains page = 2, total_pages = 2. </li>
     * <li> Verify if the response.data array size > 0. </li>
     * </ol>
     */
    @Test(description = "Get list of users with page = 2 as a query parameter.")
    public void getListOfUsersWithQueryParam() {
        int userId = 2;
        given()
                .queryParam("page", 2)
                .get("/api/users")
                .then()
                .statusCode(200)
                .assertThat()
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("data.size()", equalTo(6));
    }

    /**
     * <pre>
     * Purpose          :   Validate a GET request sends HTTP 404 code when a given userId is not present in database.
     * Request Method   :   GET
     * Path             :   "/api/users/999" -> Note '999' is passed as a path param in the url.
     * Response         :   {}
     * </pre>
     *
     * Assertion:
     * <ol>
     * <li> Status code = 404. </li>
     * <li> Verify if the response body is empty. </li>
     * </ol>
     */
    @Test(description = "")
    public void userNotFound() {
        int userId = 999;
        given()
                .pathParam("userId", 999)
                .get("/api/users/{userId}")
                .then()
                .statusCode(404)
                .assertThat()
                .body("size()", equalTo(0));
    }
}
