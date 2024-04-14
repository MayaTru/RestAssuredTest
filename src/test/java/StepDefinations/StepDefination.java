package StepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import Resources.APIResources;
import Resources.TestDataBuilder;
import Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefination extends Utils {
	
	RequestSpecification reqSpec;
	ResponseSpecification rspSpec;
	
	Response rrsp;

	TestDataBuilder data = new TestDataBuilder();
	APIResources resAPI;
	static String placeId;
	JsonPath rspjson;

	@Given("Add Place Payload with {string} {string}")
	public void add_place_payload_with(String Name, String Address) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		reqSpec = given().log().all().spec(requestSpecification()).body(data.addPlacePayload(Name, Address));
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		reqSpec = given().log().all().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}

	//Not in use
	@When("User calls {string} with Post Http request")
	public void user_calls_with_post_http_request(String resource) {
		// Write code here that turns the phrase above into concrete actions
		resAPI = APIResources.valueOf(resource);
		rspSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		rrsp = reqSpec.when().post(resAPI.getResource()).then().log().all().spec(rspSpec).extract().response();
	}

	@When("User calls {string} with {string} Http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
		// Write code here that turns the phrase above into concrete actions
		resAPI = APIResources.valueOf(resource);
		rspSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (httpMethod.equalsIgnoreCase("POST")) {
			rrsp = reqSpec.when().post(resAPI.getResource()).then().log().all().spec(rspSpec).extract().response();
		} else if (httpMethod.equalsIgnoreCase("GET")) {
			rrsp = reqSpec.when().get(resAPI.getResource()).then().log().all().spec(rspSpec).extract().response();
		}
		else if (httpMethod.equalsIgnoreCase("DELETE")) {
			rrsp = reqSpec.when().delete(resAPI.getResource()).then().log().all().spec(rspSpec).extract().response();
		}
	}

	@Then("API call is success with status code {int}")
	public void api_call_is_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(200, rrsp.getStatusCode());
	}

	@Then("{string} in response body id {string}")
	public void in_response_body_id(String KeyValue, String Expectedvalue) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(getJsonPath(rrsp, KeyValue), Expectedvalue);

	}

	@Then("Verify placeId created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String Name, String resource) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		placeId = getJsonPath(rrsp, "place_id");
		reqSpec = given().spec(requestSpecification()).queryParam("place_id", placeId);
		user_calls_with_http_request(resource, "GET");
		String name = getJsonPath(rrsp, "name");
		assertEquals(name, Name);
	}

}
