package Resources;

import java.util.ArrayList;
import java.util.List;

import org.maya.RestAssuredTest.AddPlaceReq;
import org.maya.RestAssuredTest.location;

public class TestDataBuilder {
	public AddPlaceReq addPlacePayload(String Name, String Address) {
		AddPlaceReq rBody = new AddPlaceReq();
		location lBody = new location();
		lBody.setLat(-38.383494);
		lBody.setLng(33.427362);
		rBody.setLocation(lBody);
		rBody.setAccuracy(50);
		rBody.setName(Name);
		rBody.setPhone_number("(+91) 983 893 3937");
		rBody.setAddress(Address);
		List<String> l1 = new ArrayList<String>();
		l1.add("shoe park");
		l1.add("shop");
		rBody.setTypes(l1);
		rBody.setWebsite("http://google.com");
		rBody.setLanguage("French-IN");
		return rBody;
	}
	public String deletePlacePayload(String placeId) {
		String str1 = "{\r\n"
				+ "    \"place_id\": \""+placeId+"\"\r\n"
				+ "}";
		return str1;
	}
}
