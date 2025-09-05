Feature: Student API Testing

#  Scenario: Get all students
#    Given the student API is available
#    When I request all students
#    Then I should receive a successful response

#Feature: Student API Management
#  As a QA Automation Engineer
#  I want to test the Student API
#  So that I can validate the functionality of Student endpoints

  @regression @api
  Scenario: Get all students
    Given the Student API is available
    When I send a GET request to "/list"
    Then the response status code should be 200
    And the response should contain a list of students

  @regression @api
  Scenario: Create a new student
    Given the Student API is available
    When I send a POST request to "" with the following data:
      | firstName | lastName | email             | programme |
      | John      | Doe      | @test.com | Computer Science |
    And the response status code should be 201
    Then the response should contain the created student
