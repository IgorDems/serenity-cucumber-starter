package s.app.junit;


import java.util.ArrayList;
import java.util.HashMap;

import appmodel.StudentClass;
import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import net.serenitybdd.annotations.Step;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import net.serenitybdd.annotations.Title;
import net.serenitybdd.annotations.Steps;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import s.utils.ReuseableSpecifications;
import s.utils.TestUtils;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;



@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {

    static String firstName = "SMOKEUSER"+ TestUtils.getRandomValue();
    static String lastName = "SMOKEUSER"+TestUtils.getRandomValue();
    static String programme = "ComputerScience";
    static String email = TestUtils.getRandomValue()+"xyz1@gmail.com";
    static int studentId;

    @Steps
    StudentSerenitySteps steps;


    @Test
    public void addUser(){
        ArrayList<String> courses = new ArrayList<String>();
        courses.add("JAVA");
        courses.add("C++");


        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .body(student)
                .post()
                .then()
                .log()
                .all()
                .statusCode(201);

    }

//    @Steps
//    StudentSerenitySteps steps;



    @Title("Verify if the student was added to the application")
    @Test
    public void test002(){

        HashMap<String,Object> value =	steps.getStudentInfoByFirstName(firstName);
        assertThat(value,hasValue(firstName));

        System.out.println("The value is "+ value);

        studentId = (int) value.get("id");

    }

    @Title("Update the user information and verify the updated information!")
    @Test
    public void test003(){

        ArrayList<String> courses = new ArrayList<String>();
        courses.add("JAVA");
        courses.add("C++");

        firstName = firstName+"_Updated";
        steps.updateStudent(studentId, firstName, lastName,email, programme, courses);

        HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
        assertThat(value,hasValue(firstName));
    }

    @Title("Delete the student and verify if the student is deleted!")
    @Test
    public void test004(){
        steps.deleteStudent(studentId);
        steps.getStudentById(studentId).statusCode(404);
    }

    @Title("This test will create a new student")
    @Test
    public void test005(){
        ArrayList<String> courses = new ArrayList<String>();
        courses.add("JAVA");
        courses.add("C++");

        steps.createStudent(firstName, lastName, email, programme, courses)
                .statusCode(201)
                .spec(ReuseableSpecifications.getGenericResponseSpec());


    }

    @Title("Verify if the student was added to the application")
    @Test
    public void test006(){

        HashMap<String,Object> value =	steps.getStudentInfoByFirstName(firstName);
        assertThat(value,hasValue(firstName));

        studentId = (int) value.get("id");

    }

    @Title("Update the user information and verify the updated information!")
    @Test
    public void test007(){

        ArrayList<String> courses = new ArrayList<String>();
        courses.add("JAVA");
        courses.add("C++");

        firstName = firstName+"_Updated";
        steps.updateStudent(studentId, firstName, lastName,email, programme, courses);

        HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
        assertThat(value,hasValue(firstName));
    }


//    @Ignore

    @Title("Delete the student and verify if the student is deleted!")
    @Test
    public void test008(){
        steps.deleteStudent(studentId);
        steps.getStudentById(studentId).statusCode(404);
    }


    @Ignore
    @Title("updateUser")
    @Test
    public void test009(){
        ArrayList<String> courses = new ArrayList<String>();
        courses.add("JAVA");
        courses.add("C++");


        StudentClass student = new StudentClass();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setProgramme(programme);
        student.setCourses(courses);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .body(student)
                .put()
                .then()
                .log()
                .all()
                .statusCode(201);

    }

}
