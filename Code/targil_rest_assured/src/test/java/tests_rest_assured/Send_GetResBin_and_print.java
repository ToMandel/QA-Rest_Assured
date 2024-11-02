package tests_rest_assured;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import io.restassured.response.Response;
import targil_rest_assured.GetResBin;

import java.util.regex.Pattern;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Send_GetResBin_and_print {
	public static Excel readObject = new Excel();

	public static boolean isValid(String email) {
		String emailRegex = "([a-zA-Z0-9_+&.-]+)@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public Response init(String sheetName) throws IOException {
		readObject.readExcel("C:\\Users\\Bored\\Downloads", "APIread.xls", sheetName);
		GetResBin getresbin = new GetResBin();
		Sheet thsSheet = Excel.getsheet();
		Row row = thsSheet.getRow(0);
		String x = row.getCell(0).getStringCellValue();
		Response TestRes = getresbin.send_res(x);
		return TestRes;
	}

	// check if there are any empty titles
	@Test
	public void empty_title() throws IOException {
		Response TestRes = init("posts");
		String s_repronse = TestRes.toString();
		readObject.writeToFile("Test-1. check if there are any empty titles");
		readObject.writeToFile(s_repronse + "\n");
		readObject.writeToFile(TestRes.getStatusCode() + "\n");
		String[] arrOfStr = TestRes.jsonPath().getString("title").split(",", -2);
		for (int i = 0; i < arrOfStr.length; i++) {
			if (arrOfStr[i].compareTo("") == 0)
				fail();
		}
	}

	// check if there are any body text duplicates
	@Test
	public void duplicate_body() throws IOException {
		Response TestRes = init("posts");
		String s_repronse = TestRes.toString();
		readObject.writeToFile("Test-2. check if there are any body text duplicates");
		readObject.writeToFile(s_repronse + "\n");
		readObject.writeToFile(TestRes.getStatusCode() + "\n");
		String[] arrOfStr = TestRes.jsonPath().getString("body").split(",", -2);
		for (int i = 0; i < arrOfStr.length - 1; i++) {
			if (arrOfStr[i].compareTo(arrOfStr[i + 1]) == 0)
				fail();
		}
	}

	// check if id in ascending
	@Test
	public void id_order() throws NumberFormatException, IOException {
		Response TestRes = init("posts");
		String s_repronse = TestRes.toString();
		readObject.writeToFile("Test-3. check if id in ascending");
		readObject.writeToFile(s_repronse + "\n"); // print response
		readObject.writeToFile(TestRes.getStatusCode() + "\n"); // print status code
		List<Object> array = TestRes.jsonPath().getList("id");
		int size = array.size();

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				Integer x = (Integer) array.get(i);
				Integer y = (Integer) array.get(j);
				if (x > y)
					fail();
			}
		}
	}

	// check if we get a single comment of the third user and validate his email
	// address
	@Test
	public void status_code() throws NumberFormatException, IOException {
		Response TestRes = init("SingleComment");
		String s_repronse = TestRes.toString();
		readObject.writeToFile(
				"Test-4. check if we get a single comment of the third user and validate his email address");

		readObject.writeToFile(s_repronse + "\n"); // print response
		readObject.writeToFile(TestRes.getStatusCode() + "\n"); // print status code
		String x = TestRes.jsonPath().getString("id");
		readObject.writeToFile(TestRes.jsonPath().getString("email") + "\n");
		if (x.compareTo("3") != 0)
			fail();
		if (!isValid(TestRes.jsonPath().getString("email")))
			fail();
	}

	// check if we get a proper Status Code
	// We supposed to get status code 404 because were sending a non-existing path
	// of the url
	@Test
	public void commentor_email() throws NumberFormatException, IOException {
		Response TestRes = init("PageNotFound");
		String s_repronse = TestRes.toString();
		readObject.writeToFile(
				"Test-5. check if we get a proper Status Code We supposed to get status code 404 because were sending a non-existing path of the url");
		readObject.writeToFile(s_repronse + "\n"); // print response
		readObject.writeToFile(TestRes.getStatusCode() + "\n"); // print status code
		if (TestRes.getStatusCode() != 404)
			fail();
	}

	public static void main(String args[]) throws IOException {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(Send_GetResBin_and_print.class); 
		if (result.getFailureCount() > 0) {
			readObject.writeToFile("Test failed.");
		} else {
			readObject.writeToFile("\nTest finished successfully. No Failures Found");
		}
	}
}
