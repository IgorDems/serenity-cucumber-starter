package s.app.junit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            return firstName + " " + lastName + " " + email + " " + programme + " " + course;
        }
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

    public static void main(String[] args) {
        CsvReaderExample reader = new CsvReaderExample();
        List<StudentRecord> records = reader.readCsv("src/test/resources/testdata/studentinfo.csv");

        for (StudentRecord record : records) {
            System.out.println(record);
        }
    }
}

