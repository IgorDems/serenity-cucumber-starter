package starter.stepdefinitions;

import com.app.serenity.StudentSerenitySteps;
import io.cucumber.java.en.*;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import s.utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.*;

public class StudentStepDefinitions {
    @Steps
    StudentSerenitySteps studentSteps;


    private String baseUrl = System.getProperty("webdriver.base.url", "http://127.0.0.1:8085/student");

    @Given("the Student API is available")
    public void the_student_api_is_available() {
        SerenityRest.useRelaxedHTTPSValidation();
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        SerenityRest.when().get(baseUrl + endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        restAssuredThat(response -> response.statusCode(statusCode));
    }

    @Then("the response should contain a list of students")
    public void the_response_should_contain_a_list_of_students() {
        restAssuredThat(response -> response.body("size()", greaterThan(0)));
    }


    @When("I send a POST request to {string} with the following data:")
    public void i_send_a_post_request_to_with_the_following_data(String endpoint, io.cucumber.datatable.DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);

// Create a unique email using timestamp
//        String uniqueEmail = System.currentTimeMillis() + "@test.com";
        String uniqueEmail = TestUtils.getRandomValue() + "@test.com";

// Prepare the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstName", data.get("firstName"));
        requestBody.put("lastName", data.get("lastName"));
        requestBody.put("email", uniqueEmail); // override with unique email
        requestBody.put("programme", data.get("programme"));


        // Save values for later assertions
        Serenity.setSessionVariable("expectedFirstName").to(data.get("firstName"));
        Serenity.setSessionVariable("expectedEmail").to(uniqueEmail);


        for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        SerenityRest.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(baseUrl + endpoint)
                .then()
                .statusCode(201);
    }


    @Then("the response should contain the created student")
    public void the_response_should_contain_the_created_student() {
        String expectedFirstName = Serenity.sessionVariableCalled("expectedFirstName");
        String expectedEmail = Serenity.sessionVariableCalled("expectedEmail");

        SerenityRest.given()
                .when()
                .get(baseUrl+"/list")
                .then()
                .statusCode(200)
                .body("find { it.email == '" + expectedEmail + "' }.firstName", equalTo(expectedFirstName));
    }


}
