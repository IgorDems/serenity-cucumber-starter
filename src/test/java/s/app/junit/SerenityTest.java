package s.app.junit;

import io.restassured.RestAssured;
import net.serenitybdd.annotations.Manual;
import net.serenitybdd.annotations.Pending;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class SerenityTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://127.0.0.1:8085/student";
    }
    @Title("Get student information")
    @Test
    public void getAllList() {
        SerenityRest.given()
                .when()
                .get("/list")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Ignore
    @Test
    public void getErr() {
        // Ігнорується, у звіті буде як Skipped
    }

    @Pending
    @Test
    public void getPend() {
        // Pending, у звіті Serenity теж буде видно
    }


    @Manual
    @Test
    public void getMan() {
        // Pending, у звіті Serenity теж буде видно
    }





}
