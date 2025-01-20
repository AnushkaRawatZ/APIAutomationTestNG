import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.api.requestModel.BookingDates;
import org.api.requestModel.BookingRequest;
import org.api.util.Constants;
import org.api.util.DataProviderUtil;
import org.api.util.RestUtil;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;

import static io.restassured.http.Method.POST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.api.util.Constants.BASE_URI;
import static org.api.util.Constants.BOOKING_SERVICE_ENDPOINT;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateBookingTest {

    BookingRequest bookingRequest = new BookingRequest();
    BookingDates bookingDates = new BookingDates();
    String jsonRequest;

    @Test(dataProvider = "excelData", dataProviderClass = DataProviderUtil.class, groups = {"smoke", "regression"})
    public void testCreateRequest(String fname, String lname, int totalPrice, String depositPaidString, String checkIn, String checkOut, String additional){
        boolean depositPaid = Boolean.parseBoolean(depositPaidString);
        bookingRequest.setFirstName(fname);
        bookingRequest.setLastName(lname);
        bookingRequest.setTotalPrice(totalPrice);
        bookingRequest.setDepositPaid(depositPaid);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCheckIn = formatDateString(checkIn, dateFormat);
        String formattedCheckOut = formatDateString(checkOut, dateFormat);

        bookingDates.setCheckIn(formattedCheckIn);
        bookingDates.setCheckOut(formattedCheckOut);

        bookingRequest.setBookingDates(bookingDates);
        bookingRequest.setAdditionalNeeds(additional);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonRequest = objectMapper.writeValueAsString(bookingRequest);
            System.out.println("JSON Request Body: " + jsonRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(BOOKING_SERVICE_ENDPOINT)
                .contentType("application/json")
                .accept("application/json")
                .body(jsonRequest)
                .when()
                .post()
                .then()
                .statusCode(SC_OK)
                .body("booking.firstname", equalTo(fname))
                .body("booking.lastname", equalTo(lname))
                .body("booking.totalprice", equalTo(totalPrice))
                .body("booking.depositpaid", equalTo(depositPaid))
                .body("booking.bookingdates.checkin", equalTo(formattedCheckIn))
                .body("booking.bookingdates.checkout", equalTo(formattedCheckOut))
                .body("booking.additionalneeds", equalTo(additional))
                .body("booking", notNullValue());
    }

    private String formatDateString(String dateString, SimpleDateFormat dateFormat) {
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dateString);
            return dateFormat.format(date);
        } catch (Exception e) {
            return dateString;
        }
    }

    @Test
    public void exampleTest(){
        System.out.println("Example Test 123");
    }
}
