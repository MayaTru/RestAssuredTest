package org.maya.RestAssuredTest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C6_Deserialization {
	@Test
	public void getBookData() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		Map<String, Object> fp = new HashMap<String, Object>();
		fp.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		fp.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		fp.put("grant_type", "client_credentials");
		fp.put("scope", "trust");
	
		
		String tokenrsp = given().log().all().formParams(fp)
		.when().post("oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jp = new JsonPath(tokenrsp);
		String access_token = jp.getString("access_token");
		//String token_type = jp.getString("token_type");
		
		CourseRsp getbook = given().log().all().queryParam("access_token", access_token)
		.when().get("oauthapi/getCourseDetails")
		.then().log().all().statusCode(401)
		.extract().response().as(CourseRsp.class);
		
		String lin = getbook.getLinkedIn();
		System.out.println(lin);
		
		List<webAutomation> lst = getbook.getCourses().getWebAutomation();
		String titl = lst.get(0).getCourseTitle();
		System.out.println(titl);
		
		for(int i=0;i<lst.size();i++) {
			if(lst.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java")) {
				System.out.println(lst.get(i).getPrice());
			}
		}
		
	}
}
