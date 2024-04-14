package org.maya.RestAssuredTest;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

public class C3_LibrarayTestNG {
	@Test(dataProvider = "getData")
	public void AddBook(String isbn1, String aisle1) {
		
		//Headers
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("Content-Type", "application/json");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/Library";
		
		String rspAddBody = given().log().all().headers(m).body(getAddBookPayload(isbn1,aisle1))
		.when().post("Addbook.php")
		.then().log().all().assertThat()
		.statusCode(200).extract().response().asString();
		
		JsonPath jp = new JsonPath(rspAddBody);
		String id = jp.getString("ID");
		
		String rspGetBody = given().log().all().headers(m).queryParam("ID", id)
		.when().get("GetBook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jp1 = new JsonPath(rspGetBody);
		String isbn = jp1.getString("isbn[0]");
		String aisle = jp1.getString("aisle[0]");
		
		String temp = isbn+aisle;
		System.out.println("ID -> "+temp);
		
		Assert.assertEquals(id, temp);

		
	}
	static String getAddBookPayload(String isbn, String aisle) {
		String pyload = "{\r\n"
				+ "\"name\":\"Test Maya\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\"Maya Tru\"\r\n"
				+ "}\r\n"
				+ "";
		return pyload;
	}
	
	@DataProvider(name="getData")
	public Object[][] getData() {
		Object[][] obj = new Object[2][2];
		obj[0][0] = "bmx";
		obj[0][1] = "1235";
		obj[1][0] = "bmx";
		obj[1][1] = "1236";
		return obj;
	}
}
