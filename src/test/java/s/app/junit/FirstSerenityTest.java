package s.app.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

//import net.serenitybdd.annotations.Manual;
//import net.serenitybdd.annotations.Pending;
//import net.serenitybdd.annotations.Title;
import io.cucumber.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
//import net.serenitybdd.junit.runners.SerenityRunner;
//import net.serenitybdd.rest.SerenityRest;

@RunWith(Cucumber.class)
public class FirstSerenityTest {
	
	@BeforeClass
	public static void init(){
		RestAssured.baseURI="http://127.0.0.1:8085/student";
	}
	
	@Test
	public void getAllStudents(){
		RestAssured.given()
		.when()
		.get("/list")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void thisIsaFailing(){
        RestAssured.given()
		.when()
		.get("/list")
		.then()
		.statusCode(200);
	}
	
//	@Pending
	@Test
	public void thisIsAPendingTest(){
		
	}
	
	@Ignore
	@Test
	public void thisIsASkippedTest(){
		
	}
	

	@Test
	public void thisIsAtestWithError(){
		System.out.println("This is an error"+(5/0));
	}
	
	
	@Test
	public void fileDoesNotExist() throws FileNotFoundException{
		File file = new File("E://file.txt");
		FileReader fr = new FileReader(file);
	}
	
//	@Manual
	@Test
	public void thisIsAManualTest() {
	
	}
	
//	@Title("This test will get the information of all the students from the Student App")
	@Test
	public void test01(){
		RestAssured.given()
		.when()
		.get("/list")
		.then()
		.statusCode(200);
	}
	

}
