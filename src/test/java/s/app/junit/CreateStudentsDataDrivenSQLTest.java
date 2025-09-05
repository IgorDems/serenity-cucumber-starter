package s.app.junit;

import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import connection.MyDBCon;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Concurrent(threads="2x")
@RunWith(SerenityRunner.class)
public class CreateStudentsDataDrivenSQLTest extends TestBase {

    private static List<StudentRecord> studentData = new ArrayList<>();

    @Steps
    StudentSerenitySteps steps;

    // DTO для збереження даних
    static class StudentRecord {
        String firstName;
        String lastName;
        String email;
        String programme;
        String course;

        StudentRecord(String f, String l, String e, String p, String c) {
            this.firstName = f;
            this.lastName = l;
            this.email = s.utils.TestUtils.getRandomValue() + "@mail.net";
            this.programme = p;
            this.course = c;
        }
    }

    @BeforeClass
    public static void loadDataFromDB() {

        try (Connection conn = MyDBCon.getConnection()) {

            // ✅ Перевірка з'єднання
            if (conn == null || conn.isClosed()) {
                throw new IllegalStateException("❌ Failed to establish DB connection!");
            }
            System.out.println("✅ Connection to MariaDB established successfully!");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT firstName, lastName, email, programme, course FROM students LIMIT 1")) {

                // ✅ Перевірка доступу на читання
                if (!rs.isBeforeFirst()) {
                    System.err.println("⚠️ Connected to DB but no data found in 'students' table.");
                } else {
                    System.out.println("✅ Read access to 'students' table verified.");
                }
            }

            // Завантаження усіх студентів
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT firstName, lastName, email, programme, course FROM students")) {

                while (rs.next()) {
                    studentData.add(new StudentRecord(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("programme"),
                            rs.getString("course")
                    ));
                }

                assertThat(studentData.size())
                        .as("❌ No student data found in MariaDB students table")
                        .isGreaterThan(0);

                System.out.println("✅ Loaded " + studentData.size() + " students from DB");

            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to connect or read from DB", e);
        }
    }


    @Title("DataDriven Test for adding multiple students to the Student App.")
    @Test
    public void createMultiplestudents(){
        for (StudentRecord student : studentData) {
            ArrayList<String> courses = new ArrayList<>();
            courses.add(student.course);

            steps.createStudent(student.firstName, student.lastName,
                            student.email, student.programme, courses)
                    .statusCode(201);

            System.out.printf("Inserted student: %s %s, %s, %s, %s%n",
                    student.firstName, student.lastName, student.email, student.programme, student.course);
        }
    }

    @Test
    public void test001(){
        if (studentData.isEmpty()) return;

        String firstName = studentData.get(0).firstName;
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
