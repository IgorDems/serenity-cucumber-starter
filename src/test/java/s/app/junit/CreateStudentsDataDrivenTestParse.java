package s.app.junit;

import com.app.TestBase;
import com.app.serenity.StudentSerenitySteps;
import com.opencsv.bean.CsvToBeanBuilder;
import io.cucumber.junit.Cucumber;
//import net.serenitybdd.annotations.Steps;
//import net.serenitybdd.annotations.Title;
//import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import s.utils.CsvReaderExample;
import s.utils.StudentCsvRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(Cucumber.class)
public class CreateStudentsDataDrivenTestParse extends TestBase {

//    @Steps
    StudentSerenitySteps steps;

    @BeforeClass
    public static void verifyCsvFile() throws Exception {
        CsvReaderExample.testDataIsAvailable("src/test/resources/testdata/studentinfo.csv");
    }


//    @Title("Delete all students with ID >= 100 and verify they are deleted")
    @Test
    public void testDeleteStudentsOver100() {
        // отримуємо список ID
//        List<Integer> allIds = steps.getAllStudentIds();

        // фільтруємо тих, у кого ID >= 100
//        List<Integer> idsToDelete = allIds.stream()
//                .filter(id -> id >= 100)
//                .collect(Collectors.toList());
//
//        System.out.println("IDs to delete: " + idsToDelete);
//
//        // видаляємо кожного
//        for (int id : idsToDelete) {
//            steps.deleteStudentById(id);
//        }
//
//        // перевіряємо, що вони видалені
//        for (int id : idsToDelete) {
//            steps.verifyStudentDeleted(id);
//        }
    }


//    @Title("DataDriven Test for adding multiple students to the Student App.")
    @Test
    public void createMultiplestudents() throws Exception {
        String path = "src/test/resources/testdata/studentinfo.csv";

        // читаємо CSV у список об’єктів
        List<StudentCsvRecord> students = new CsvToBeanBuilder<StudentCsvRecord>(new FileReader(path))
                .withType(StudentCsvRecord.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();

        // ітеруємо по студентах
        for (StudentCsvRecord student : students) {
            System.out.printf("Loaded student: %s %s, %s, %s, %s%n",
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getProgramme(),
                    student.getCourse());

            ArrayList<String> courses = new ArrayList<>();
            courses.add(student.getCourse());

//            steps.createStudent(student.getFirstName(),
//                            student.getLastName(),
//                            student.getEmail(),
//                            student.getProgramme(),
//                            courses)
//                    .statusCode(201);
        }
    }
}

