Feature: Student API Testing

  Scenario: Get all students
    Given the student API is available
    When I request all students
    Then I should receive a successful response