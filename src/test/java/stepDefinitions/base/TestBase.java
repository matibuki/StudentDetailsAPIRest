package stepDefinitions.base;

import configuration.factory.PropertyStore;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger("TestBase.class");
    static PropertyStore propertyStore;

    @BeforeAll
    public static void setupTests(){
        propertyStore = PropertyStore.getInstance();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        logger.info(">>>> SETUP FINISHED");
    }
}
