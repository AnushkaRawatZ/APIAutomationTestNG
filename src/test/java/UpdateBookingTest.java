import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.api.util.Constants;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.api.util.DataProviderUtil;

import static org.api.util.AuthUtil.getAuthToken;
import static org.hamcrest.core.IsEqual.equalTo;

public class UpdateBookingTest {

    @Test(dataProvider = "bookingIds", dataProviderClass = DataProviderUtil.class, groups = "regression")
    public void updateBookingTest(int bookingId) {
        String token = getAuthToken();

        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .basePath(Constants.BOOKING_SERVICE_ENDPOINT + "/" + bookingId)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(Constants.UPDATE_STRING)
                .when()
                .put();

        response.then().statusCode(200);

        response.then().body("firstname", equalTo("James"));
        response.then().body("lastname", equalTo("Brown"));
        response.then().body("totalprice", equalTo(111));
        response.then().body("depositpaid", equalTo(true));
        response.then().body("bookingdates.checkin", equalTo("2018-01-01"));
        response.then().body("bookingdates.checkout", equalTo("2019-01-01"));
        response.then().body("additionalneeds", equalTo("Breakfast"));

        System.out.println("Updated booking with ID: " + bookingId);
    }
    @Test
    public void exampleTest(){
        System.out.println("Example Test 123");
    }
}
