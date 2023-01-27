package configuration.factory;

import com.github.javafaker.Faker;
import models.StudentResponse;
import models.bodyRequestModels.StudentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;

public class StudentFactory {
    private static final Logger logger = LoggerFactory.getLogger("StudentFactory.class");
    static Faker faker = new Faker();

    public static StudentRequest createRandomStudent(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        logger.info(">> Adding new student object");
        return new StudentRequest(
                faker.name().firstName(),
                faker.name().name(),
                faker.name().lastName(),
                dateFormat.format(faker.date().birthday(15, 35))
        );
    }

    public static void changeStudentMiddleName(StudentResponse studentResponse) {
        logger.info(">> Middle name changed for student object");
        studentResponse.getData().setMiddle_name(faker.name().firstName());
    }
}
