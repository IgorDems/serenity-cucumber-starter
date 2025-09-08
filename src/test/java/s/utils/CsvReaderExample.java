package s.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CsvReaderExample {

    public String firstName;
    public String lastName;
    public String email;
    public String programme;
    public String course;

    public static class StudentRecord {
        String firstName;
        String lastName;
        String email;
        String programme;
        String course;

        public StudentRecord(String firstName, String lastName, String email, String programme, String course) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.programme = programme;
            this.course = course;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + " | " + email + " | " + programme + " | " + course;
        }
    }

    private static void requireNotBlank(String name, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("CSV mapping failed: '" + name + "' is null/blank");
        }
    }


    public static void testDataIsAvailable(String path) throws IOException {
        File file = new File(path);

        assertThat(file.exists())
                .as("⚠ studentinfo.csv file should exist")
                .isTrue();

        long lineCount = Files.lines(Paths.get(path)).count();
        assertThat(lineCount)
                .as("⚠ studentinfo.csv should contain at least one record (besides header)")
                .isGreaterThan(1);

        System.out.println("✅ studentinfo.csv is valid and contains " + (lineCount - 1) + " records");
        System.out.println("==============Test Data Are Present===============");
    }

    public List<StudentRecord> readCsv(String path) {
        List<StudentRecord> students = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Пропустити заголовок
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 5) {
                    StudentRecord student = new StudentRecord(
                            values[0].trim(), // firstName
                            values[1].trim(), // lastName
                            values[2].trim(), // email
                            values[3].trim(), // programme
                            values[4].trim()  // course
                    );
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

}
