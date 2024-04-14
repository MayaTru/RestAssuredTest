package StepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;


public class Hooks  {
	@Before("@DeletePlaceAPI")
	public void beforeScenario() throws IOException {
		StepDefination obj = new StepDefination();
		if(StepDefination.placeId == null) {
			obj.add_place_payload_with("Maya", "30, Nu Baz");
			obj.user_calls_with_http_request("AddPlaceAPI", "POST");
			obj.verify_place_id_created_maps_to_using("Maya", "GetPlaceAPI");
		}
	}
}
