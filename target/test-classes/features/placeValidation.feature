Feature: Validating Place API

	@AddPlaceAPI
  Scenario Outline: Verify Place Successfully added using AddPlaceAPI
    Given Add Place Payload with "<Name>" "<Address>"
    When User calls "AddPlaceAPI" with "POST" Http request
    Then API call is success with status code 200
    And "status" in response body id "OK"
    And "scope" in response body id "APP"
    And Verify placeId created maps to "<Name>" using "GetPlaceAPI"
    
    Examples:
    |Name						|Address									|
		|Frontline house|29, side layout, cohen 09|
		|Backline house	|30, side layout, cohen 10|
	
	@DeletePlaceAPI	
	Scenario: Verify Place Successfully deleted
		Given DeletePlace Payload
		When User calls "DeletePlaceAPI" with "DELETE" Http request
    Then API call is success with status code 200
    And "status" in response body id "OK"