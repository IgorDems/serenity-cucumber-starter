package s.app.junit;

import io.cucumber.junit.CucumberSerenityRunner;
import io.restassured.RestAssured;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


//@RunWith(CucumberSerenityRunner.class)
public class SerenityTestUrl {

    @BeforeClass
    public static void init(){
        RestAssured.baseURI = "http://127.0.0.1:8085/student";
    }

    @Test
    public void getAllList(){
        SerenityRest.given()
                .when()
                .get("/list")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
}