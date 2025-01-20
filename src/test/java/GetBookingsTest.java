import io.restassured.RestAssured;
import org.api.util.Constants;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class GetBookingsTest {
    @Test
    public void testGetBookingDetails() {
        RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .basePath(Constants.BOOKING_SERVICE_ENDPOINT)
                .when()
                .get()
                .then()
                .statusCode(SC_OK)
                .log().all();
    }
}
