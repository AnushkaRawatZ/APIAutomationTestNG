import io.restassured.RestAssured;
import org.api.util.AuthUtil;
import org.api.util.Constants;
import org.api.util.DataProviderUtil;
import org.testng.annotations.Test;

public class DeleteBookingTest {

    @Test(dataProvider = "bookingIds", dataProviderClass = DataProviderUtil.class)
    public void testDeleteBooking(int bookingId) {
        String token = AuthUtil.getAuthToken();
        RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .basePath(Constants.BOOKING_SERVICE_ENDPOINT + "/"+ bookingId)
                .header("Content-Type", "application/json")
                .cookie("token", token)
                .when()
                .delete()
                .then()
                .statusCode(201);
    }
}
