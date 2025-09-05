package s.app.junit;

import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateStudentsDataDrivenTestAlternative extends TestBase {

    private String firstName;
    private String lastName;
    private String email;
    private String programme;
    private String course;

    @Steps
    StudentSerenitySteps steps;

    // Constructor to receive parameters
    public CreateStudentsDataDrivenTestAlternative(String firstName, String lastName,
                                                   String email, String programme, String course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.programme = programme;
        this.course = course;
    }

    @Parameterized.Parameters(name = "Student: {0} {1}")
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> testData = new ArrayList<>();

        String csvFile = "src/test/resources/testdata/studentinfo.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] values = line.split(",");
                if (values.length >= 5) {
                    testData.add(new Object[]{
                            values[0].trim(), // firstName
                            values[1].trim(), // lastName
                            values[2].trim(), // email
                            values[3].trim(), // programme
                            values[4].trim()  // course
                    });
                }
            }
        }
        return testData;
    }

    @Title("DataDriven Test for adding multiple students to the Student App.")
    @Test
    public void createMultiplestudents() {
        System.out.printf("Processing student: %s %s, %s, %s, %s%n",
                firstName, lastName, email, programme, course);

        ArrayList<String> courses = new ArrayList<>();
        courses.add(course);

        steps.createStudent(firstName, lastName, email, programme, courses)
                .statusCode(201);
    }
}