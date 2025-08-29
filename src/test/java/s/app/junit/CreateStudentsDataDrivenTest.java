package s.app.junit;

import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Concurrent(threads="4x")
@UseTestDataFrom("testdata/studentinfo.csv")
@RunWith(SerenityRunner.class)
public class CreateStudentsDataDrivenTest extends TestBase {
	
	private String firstName;
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public StudentSerenitySteps getSteps() {
		return steps;
	}

	public void setSteps(StudentSerenitySteps steps) {
		this.steps = steps;
	}

	private String lastName;
	private String email;
	private String programme;
	private String course;


	@Steps
	StudentSerenitySteps steps;
	
	
	@Title("DataDriven Test for adding multiple students to the Student App.")
	@Test
	public void createMultiplestudents(){
		ArrayList<String> courses = new ArrayList<>();
		courses.add(course);
		
		steps.createStudent(firstName, lastName, email, programme, courses)
                .statusCode(201);
		
	}

    @Test
    public void test001(){

        String p1 = "findAll{it.firstName='";
        String p2 = "'}.get(0)";

        HashMap<String, Objects> value =
        SerenityRest.rest().given()
                .when()
                .get("/list")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .path(p1+firstName+p2);

        System.out.println(value);
    }



}
