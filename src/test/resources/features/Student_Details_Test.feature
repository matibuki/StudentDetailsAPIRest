Feature: Add new Student, Edit Student and Remove Student
  Scenario: Student can be added, edited and removed
    Given new student is created
    When details of the new student are saved in the system
    And middle name of student is updated to a new value
    And new student middle name value is verified
    And student record is deleted
    Then verify that student does not exist in the system