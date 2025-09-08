package s.utils;

import com.opencsv.bean.CsvBindByName;

public class StudentCsvRecord {

    @CsvBindByName(column = "firstName")
    private String firstName;

    @CsvBindByName(column = "lastName")
    private String lastName;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "programme")
    private String programme;

    @CsvBindByName(column = "course")
    private String course;

    // геттери
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return TestUtils.getRandomValue()+email; }
    public String getProgramme() { return programme; }
    public String getCourse() { return course; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | " + email + " | " + programme + " | " + course;
    }
}
