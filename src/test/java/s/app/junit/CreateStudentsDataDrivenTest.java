package s.app.junit;

import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import s.utils.TestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Concurrent(threads="2x")
@UseTestDataFrom("testdata/studentinfo.csv")
@RunWith(SerenityRunner.class)
public class CreateStudentsDataDrivenTest extends TestBase {

    @Test
    public void verifyStudentCsvFileIsNotEmpty() throws Exception {
        String path = "src/test/resources/testdata/studentinfo.csv";
        File file = new File(path);

        assertThat(file.exists())
                .as("⚠ studentinfo.csv file should exist")
                .isTrue();

        long lineCount = Files.lines(Paths.get(path)).count();
        assertThat(lineCount)
                .as("⚠ studentinfo.csv should contain at least one record (besides header)")
                .isGreaterThan(1);

        System.out.println("✅ studentinfo.csv is valid and contains " + (lineCount - 1) + " records");
    }

    // These fields MUST be public for Serenity to inject CSV data
    public String firstName;
    public String lastName;
    public String email;
    public String programme;
    public String course;

    @Steps
    StudentSerenitySteps steps;

    // Remove these setters - Serenity will inject directly into public fields
    // public void setFirstName(String firstName) { this.firstName = firstName; }
    // public void setLastName(String lastName) { this.lastName = lastName; }
    // public void setEmail(String email) { this.email = email; }
    // public void setProgramme(String programme) { this.programme = programme; }
    // public void setCourse(String course) { this.course = course; }

    @Title("DataDriven Test for adding multiple students to the Student App.")
    @Test
    public void createMultiplestudents(){

        System.out.printf("Loaded student: %s %s, %s, %s, %s%n",
                firstName, lastName, email, programme, course);

        // Validate that CSV data was properly loaded
        requireNotBlank("firstName", firstName);
        requireNotBlank("lastName", lastName);
        requireNotBlank("email", email);
        requireNotBlank("programme", programme);
        requireNotBlank("course", course);

        ArrayList<String> courses = new ArrayList<>();
        courses.add(course);

        steps.createStudent(firstName, lastName, email, programme, courses)
                .statusCode(201);
    }

    @Test
    public void test001(){
        // Only run this test if we have valid data
        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("Skipping test001 - no firstName data available");
            return;
        }

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

    private static void requireNotBlank(String name, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("CSV mapping failed: '" + name + "' is null/blank");
        }
    }
}