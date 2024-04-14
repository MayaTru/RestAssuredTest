package org.maya.RestAssuredTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class C1_Basic {
	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/maps/api";
		String reqbody = "{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}";
		String resAddbody = given().log().all().queryParams("key","qaclick123").header("Content-Type", "application/json").body(reqbody)
		.when().post("place/add/json").then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.extract().response().asString();
		
		JsonPath rspjson = new JsonPath(resAddbody);
		String placeId = rspjson.getString("place_id");
		
		String resGetbody = given().log().all().queryParam("place_id", placeId).queryParam("key","qaclick123")
		.when().get("place/get/json")
		.then().assertThat().body("accuracy", equalTo("50"))
		.extract().response().asString();
	}
}
