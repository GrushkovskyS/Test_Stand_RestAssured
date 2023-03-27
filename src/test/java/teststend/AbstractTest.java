package teststend;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AbstractTest {

    static Properties prop = new Properties();
    public  static InputStream configureFile;
    private static String token;
    private static String token_url;
    private static String base_url;
    private static String owner_notMe;
    protected static ResponseSpecification responseSpecification;
    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void  initTest() throws IOException {
        configureFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configureFile);

        token = prop.getProperty("X-Auth-Token");
        token_url = prop.getProperty("token_url");
        base_url = prop.getProperty("Base_url");
        owner_notMe = prop.getProperty("owner");

        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("token_url",token_url)
                .addQueryParam("Base_url",base_url)
                .addHeader("X-Auth-Token",token)
                .addQueryParam("owner",owner_notMe)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static String getToken() {
        return token;
    }

    public static String getOwner_notMe() {
        return owner_notMe;
    }


    public static String getToken_url() {
        return token_url;
    }

    public static String getBase_url() {
        return base_url;
    }

    public static ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
