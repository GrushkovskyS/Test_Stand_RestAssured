package teststend;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestStendTest extends AbstractTest {

    @BeforeAll
    static void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(0)
    @DisplayName("Авторизация валидные значения")
    void avtoValidTest0(){
        ResponseData response = given()
                .formParam("username", "Use")
                .formParam("password","ad8783089f")
                .when()
                .post(getToken_url())
                .then()
                .extract()
                .response()
                .body()
                .as(ResponseData.class);
        assertThat(response.getUsername(), equalTo("Use"));
//                .assertThat()
//                .statusCode(200);

    }
    @Test
    @Order(1)
    @DisplayName("Авторизация валидные значения")
    void avtoValidTest() {
        given()
                .formParam("username", "Use")
                .formParam("password", "ad8783089f")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.TEXT);
    }
    @Test
    @Order(2)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid1Test() {
        given()
                .formParam("username", "U1")
                .formParam("password", "f89412c11b")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.TEXT)
                .body("error",equalTo("Проверьте логин и пароль."));
    }
    @Test
    @Order(3)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid2Test() {
        given()
                .formParam("username", "Vasiliev@!%^&*()_+=-")
                .formParam("password", "7bcd847f04")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.TEXT)
                .body("error",equalTo("Проверьте логин и пароль."));
    }
    @Test
    @Order(4)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid3Test() {
        given()
                .formParam("username", "Vova29292836546709876")
                .formParam("password", "6f7f0a52ac")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error",equalTo("Проверьте логин и пароль."));
    }
    @Test
    @Order(5)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid4Test() {
        given()
                .formParam("username", "КириллКиряя")
                .formParam("password", "7e8b7954d0")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error",equalTo("Проверьте логин и пароль."));
    }
    @Test
    @Order(6)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid5Test() {
        given()
                .formParam("username", "Vasiapupki")
                .formParam("password", "")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error",equalTo("Проверьте логин и пароль."));
    }
    @Test
    @Order(7)
    @DisplayName("Авторизация не валидные значения")
    void avtoNotValid6Test() {
        given()
                .formParam("username", "")
                .formParam("password", "")
                .when()
                .post(getToken_url())
                .then()
                .assertThat()
                .statusCode(401) .contentType(ContentType.JSON)
                .body("error",equalTo("Проверьте логин и пароль."));
    }

    @Test
    @Order(8)
    @DisplayName("Лента своих постов без query")
    void myLentaNotQueryTest() {
        given()
                .header("X-Auth-Token",getToken())
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data[0].createdAt",equalTo("2023-03-22T04:58:34+00:00"));

    }
    @Test
    @Order(9)
    @DisplayName("Своя лента. Сортировки в порядке возрастания ASC")
    void myLentaOrderASCTest() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("order", "ASC")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data[0].createdAt",equalTo("2023-03-21T09:37:45+00:00"));
    }
    @Test
    @Order(10)
    @DisplayName("Своя лента. Сортировки в порядке убывания DESC")
    void myLentaOrderDESCTest() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("order", "DESC")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data[0].createdAt",equalTo("2023-03-22T04:58:34+00:00"));
    }
    @Test
    @Order(11)
    @DisplayName("Своя лента. Ввод существующей страницы № 1")
    void myLentaPage1Test() {
      given()
                .header("X-Auth-Token", getToken())
                .queryParam("page", "1")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200)
              .contentType(ContentType.JSON)
              .body("id",equalTo(get("id)));

    }
    @Test
    @Order(12)
    @DisplayName("Своялента. Ввод несуществующей страницы № 0")
    void myLentaPage0Test() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("page", "0")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    @Order(13)
    @DisplayName("Своя лента. Ввод несуществующей страницы № 3")
    void myLentaPage3Test() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("page", "3")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    @Order(14)
    @DisplayName("Чужая лента. Без параметров")
    void notMyLentaTest() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner",getOwner_notMe())
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    @Order(15)
    @DisplayName("Чужая лента. Сортировки в порядке возрастания ASC")
    void notMyLentaOrderASCTest() {
        given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner",getOwner_notMe())
                .queryParam("order", "ASC")
                .when()
                .get(getBase_url())
                .then()
                .assertThat()
                .statusCode(200);
    }
}
