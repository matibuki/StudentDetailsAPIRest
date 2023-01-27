package stepDefinitions.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static stepDefinitions.base.ApiParams.API_PATH;
import static stepDefinitions.base.ApiParams.BASE_URL;

public class RequestResponseSpecification {

    public RequestSpecification setupRequestSpecification() {
        return RestAssured.given()
                .baseUri(BASE_URL + API_PATH)
                .contentType(ContentType.JSON);
    }

    public ResponseSpecification setupResponseSpecification() {
        return RestAssured.expect()
                .contentType(ContentType.JSON)
                .time(Matchers.lessThan(10000L));
    }

}
