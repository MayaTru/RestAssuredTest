package org.maya.RestAssuredTest;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class C2_ComplexJsonParse {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mckRsp = "{\r\n"
				+ "\r\n"
				+ "\"dashboard\": {\r\n"
				+ "\r\n"
				+ "\"purchaseAmount\": 910,\r\n"
				+ "\r\n"
				+ "\"website\": \"rahulshettyacademy.com\"\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "\"courses\": [\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Selenium Python\",\r\n"
				+ "\r\n"
				+ "\"price\": 50,\r\n"
				+ "\r\n"
				+ "\"copies\": 6\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"Cypress\",\r\n"
				+ "\r\n"
				+ "\"price\": 40,\r\n"
				+ "\r\n"
				+ "\"copies\": 4\r\n"
				+ "\r\n"
				+ "},\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "\r\n"
				+ "\"title\": \"RPA\",\r\n"
				+ "\r\n"
				+ "\"price\": 45,\r\n"
				+ "\r\n"
				+ "\"copies\": 10\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "}";
		
		JsonPath jsp = new JsonPath(mckRsp);
		
		//Print No of Courses
		int c = jsp.getInt("courses.size()");
		System.out.println(c);
		
		//Print Purchase Amount
		int pa = jsp.getInt("dashboard.purchaseAmount");
		System.out.println(pa);
		
		//Print First Title
		String t1 = jsp.getString("courses[0].title");
		System.out.println(t1);
		
		//Print All courses and title
		for(int i=0;i<c;i++) {
			String temp = jsp.getString("courses["+i+"].title");
			System.out.println(temp);
		}
		
		//Print No of Copies sold by RPA Course
		for(int i=0;i<c;i++) {
			String temp = jsp.getString("courses["+i+"].title");
			if(temp.equalsIgnoreCase("RPA")) {
				int p = jsp.getInt("courses["+i+"].copies");
				System.out.println(p);
				break;
			}
		}
		
		//Verify Sum of Course with Purchase Amount
		int sum = 0;
		for(int i=0;i<c;i++) {
			int c1 = jsp.getInt("courses["+i+"].price");
			int p1 = jsp.getInt("courses["+i+"].copies");
			int temp = c1*p1;
			sum += temp;
		}
		System.out.println(sum);
		Assert.assertEquals(sum, pa);
	}
}
