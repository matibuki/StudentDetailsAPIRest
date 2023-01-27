package stepDefinitions;

import stepDefinitions.base.RequestResponseSpecification;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.StudentResponse;
import models.bodyRequestModels.StudentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import configuration.factory.StudentFactory;

import static stepDefinitions.base.ApiParams.STUDENT_DETAILS_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentSteps {
    private static final Logger logger = LoggerFactory.getLogger("StudentSteps.class");
    protected RequestResponseSpecification specifications = new RequestResponseSpecification();
    int id;
    StudentResponse studentResponse;

    @Given("new student is created")
    public void newStudentsIsRegistered() {
        StudentRequest newStudent = StudentFactory.createRandomStudent();
        logger.info(">> New student registered");

        id = given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT)
                .body(newStudent).
                when()
                .post().
                then()
                .spec(specifications.setupResponseSpecification())
                .statusCode(201)
                .extract()
                .path("id");
    }

    @When("details of the new student are saved in the system")
    public void detailsOfStudentAreSaved() {
        logger.info(">> Student confirmed to exist in system");
        studentResponse = given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT + id).
                when()
                .get().
                then()
                .spec(specifications.setupResponseSpecification())
                .statusCode(200)
                .extract()
                .as(StudentResponse.class);
    }

    @And("middle name of student is updated to a new value")
    public void middleNameOfStudentIsChanged() {
        StudentFactory.changeStudentMiddleName(studentResponse);
        logger.info("Middle name of student has been changed");
        given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT + id)
                .body(studentResponse.getData()).
                when()
                .put().
                then()
                .spec(specifications.setupResponseSpecification())
                .statusCode(200);
    }

    @And("new student middle name value is verified")
    public void checksThatMiddleNameIsChanged() {
        logger.info(">> Middle name change verified");
        given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT + id).
                when()
                .get().
                then()
                .spec(specifications.setupResponseSpecification())
                .statusCode(200)
                .assertThat()
                .body("data.middle_name", equalTo(studentResponse.getData().getMiddle_name()));
    }

    @And("student record is deleted")
    public void deleteStudent() {
        logger.info(">>Student was removed");
        given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT + id).
                when()
                .delete().
                then()
                .spec(specifications.setupResponseSpecification());
    }

    @Then("verify that student does not exist in the system")
    public void verifyThatStudentDoesNotExist() {
        logger.info(">>Student record does not exist");
        given()
                .spec(specifications.setupRequestSpecification())
                .basePath(STUDENT_DETAILS_ENDPOINT + id).
                when()
                .get().
                then()
                .spec(specifications.setupResponseSpecification())
                .statusCode(200)
                .assertThat()
                .body("msg", equalTo(System.getProperty("errorMsg")));
    }
}