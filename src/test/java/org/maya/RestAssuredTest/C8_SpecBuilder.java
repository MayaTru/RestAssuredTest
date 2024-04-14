package org.maya.RestAssuredTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

public class C8_SpecBuilder {
	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/maps/api";
		
		AddPlaceReq rBody = new AddPlaceReq();
		location lBody = new location();
		lBody.setLat(-38.383494);
		lBody.setLng(33.427362);
		rBody.setLocation(lBody);
		rBody.setAccuracy(50);
		rBody.setName("Frontline house");
		rBody.setPhone_number("(+91) 983 893 3937");
		rBody.setAddress("29, side layout, cohen 09");
		List<String> l1 = new ArrayList<String>();
		l1.add("shoe park");
		l1.add("shop");
		rBody.setTypes(l1);
		rBody.setWebsite("http://google.com");
		rBody.setLanguage("French-IN");
		
		RequestSpecification req =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/maps/api").setContentType(ContentType.JSON)
		.addQueryParam("key","qaclick123").build();
		
		ResponseSpecification rsp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification AddBodyReq = given().log().all().spec(req).body(rBody);
		
		Response AddBodyRsp = AddBodyReq.when().post("place/add/json").then().log().all().spec(rsp).body("scope",equalTo("APP"))
				.extract().response();
		
		String resAddbody = AddBodyRsp.asString();
		
		JsonPath rspjson = new JsonPath(resAddbody);
		String placeId = rspjson.getString("place_id");
		
		given().log().all().queryParam("place_id", placeId).queryParam("key","qaclick123")
		.when().get("place/get/json")
		.then().log().all().assertThat().body("accuracy", equalTo("50"))
		.extract().response().asString();
	}
}
