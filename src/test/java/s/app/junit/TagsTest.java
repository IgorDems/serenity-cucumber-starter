package s.app.junit;

import com.app.TestBase;
//import net.serenitybdd.annotations.Title;
//import net.serenitybdd.annotations.WithTag;
//import net.serenitybdd.annotations.WithTags;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
//import net.serenitybdd.junit.runners.SerenityRunner;
//import net.serenitybdd.rest.SerenityRest;

@RunWith(Cucumber.class)
public class TagsTest extends TestBase {

//	@WithTag("studentfeature:NEGATIVE")
//	@Title("Provide a 405 status code when incorrect HTTP method is used to access a resource")
	@Test
	public void inValidMethod() {
		RestAssured.given().when()
		.post("/list")
		.then()
		.statusCode(405)
		.log().all();
	}
	
	
//	@WithTags(
//			{
//			@WithTag("studentfeature:SMOKE"),
//			@WithTag("studentfeature:POSITIVE")
//			}
//			)
	@Test
//	@Title("This test will verify if a status code of 200 is returned for GET request")
	public void verifyIftheStatusCodeIs200() {
		RestAssured
		.given().when()
		.get("/list")
		.then()
		.statusCode(200);
	}
	
//	@WithTags(
//			{
//			@WithTag("studentfeature:SMOKE"),
//			@WithTag("studentfeature:NEGATIVE")
//			}
//			)
	
//	@Title("This test will provide an error code of 400 when user tries to access an invalid resource")
	@Test
	public void incorrectResource() {
		RestAssured.given().when()
		.get("/list")
		.then()
		.statusCode(400)
		.log().all();
	}
}
