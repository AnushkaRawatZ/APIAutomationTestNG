package org.api.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderUtil {

    private static List<Integer> bookingIds = new ArrayList<>();

    public static void fetchBookingIds() {
        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .basePath(Constants.BOOKING_SERVICE_ENDPOINT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        List<Integer> ids = response.jsonPath().getList("bookingid[0..7]");
        bookingIds.addAll(ids);
    }

    @DataProvider(name = "bookingIds")
    public static Object[][] provideBookingIds() {

        fetchBookingIds();

        Object[][] ids = new Object[bookingIds.size()][1];
        for (int i = 0; i < bookingIds.size(); i++) {
            ids[i][0] = bookingIds.get(i);
        }
        return ids;
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/APIDataProvider.xlsx";
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rows - 1][cols];

        for (int i = 1; i < rows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data[i - 1][j] = cell.getDateCellValue().toString();
                            } else {
                                data[i - 1][j] = (int) cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        default:
                            data[i - 1][j] = "";
                    }
                }
            }
        }
        workbook.close();
        file.close();
        return data;
    }

}
