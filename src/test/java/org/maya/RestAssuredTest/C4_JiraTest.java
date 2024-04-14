package org.maya.RestAssuredTest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class C4_JiraTest {
	public static String getPayloadAddIssue() {
		String str = "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"MAYAP\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"My Third Jira Project via API\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        },\r\n"
				+ "        \"description\": \"Creating Issue via API\"\r\n"
				+ "    }\r\n"
				+ "}";
		return str;
	}
	
	public static String getPayloadAddComment() {
		String str = "{\r\n"
				+ "    \"body\": \"Added My First Comment\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
		return str;
	}
	
	@Test
	public void addIssueandComment() {
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		given().log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"username\": \"Kh.mayank8\",\r\n"
				+ "    \"password\": \"Jira@08\"\r\n"
				+ "}").filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		/*
		JsonPath jp1 = new JsonPath(getSessionRsp);
		String sname = jp1.getString("session.name");
		String svalue = jp1.getString("session.value");
		String coo = sname+"="+svalue;
		*/
		
		String AddIssueRsp = given().log().all().header("Content-Type", "application/json").filter(session).body(getPayloadAddIssue())
		.when().post("/rest/api/2/issue")
		.then().log().all().extract().response().asString();
		
		JsonPath jp2 = new JsonPath(AddIssueRsp);
		String issueid = jp2.getString("id");
		
		String AddCommentRsp = given().log().all().header("Content-Type", "application/json").filter(session).body(getPayloadAddComment()).pathParam("key", issueid)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().extract().response().asString();
		
		JsonPath jp3 = new JsonPath(AddCommentRsp);
		String commentid = jp3.getString("id");
		
		given().log().all().header("X-Atlassian-Token", "no-check").pathParam("Key", issueid).multiPart("file",new File("C:\\Users\\Mayank\\eclipse-workspace-new\\RestAssuredTest\\JiraTest.txt")).filter(session)
		.when().post("rest/api/2/issue/{Key}/attachments")
		.then().statusCode(200);
		
		String getIssueRsp = given().log().all().filter(session).header("Content-Type", "application/json").pathParam("key", issueid).queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		
		JsonPath jp4 = new JsonPath(getIssueRsp);
		int commentsize = jp4.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<commentsize;i++) {
			System.out.println(jp4.getInt("fields.comment.comments["+i+"].id"));
		}
		
	}
}
