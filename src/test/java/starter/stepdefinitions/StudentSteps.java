package starter.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StudentSteps {

    private Response response;

    @Given("the student API is available")
    public void the_student_api_is_available() {
        RestAssured.baseURI = "http://127.0.0.1:8085/student";
    }

    @When("I request all students")
    public void i_request_all_students() {
        response = RestAssured.given()
                .when()
                .get("/list");
    }

    @Then("I should receive a successful response")
    public void i_should_receive_a_successful_response() {
        response.then()
                .log().all()
                .statusCode(200);
    }
}