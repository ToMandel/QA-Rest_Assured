package targil_rest_assured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GetResBin {
	final String wantedStatus = "200";
		
	public static Response send_res(String path) {
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
		Response response = (Response) given().header("Content-type", "application/json").when().get(path).then()
				.extract();
		return (response);
	}
	
	

}
