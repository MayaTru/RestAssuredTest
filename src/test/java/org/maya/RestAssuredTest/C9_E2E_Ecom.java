package org.maya.RestAssuredTest;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C9_E2E_Ecom {
	static String getLoginReqPayload(String usr, String pwd) {
		String str = "{\r\n"
				+ "    \"userEmail\": \""+usr+"\",\r\n"
				+ "    \"userPassword\": \""+pwd+"\"\r\n"
				+ "}";
		return str;
	}
	@Test
	public void TestEcom() {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").setContentType(ContentType.JSON).build();
		String cred = getLoginReqPayload("mayatru@mail.com", "Maya@0812");
		RequestSpecification reqLogin = given().log().all().spec(req).body(cred);
		String rspLogin = reqLogin.when().post("auth/login").then().log().all().extract().response().asString();
		JsonPath jp = new JsonPath(rspLogin);
		String token = jp.getString("token");
		String userId = jp.getString("userId");
		
		System.out.println(token);
		
		RequestSpecification reqAP = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).build();
		
		Map<String, Object> formparam = new HashMap<String, Object>();
		formparam.put("productName", "LG AC");
		formparam.put("productAddedBy", userId);
		formparam.put("productCategory", "Applicance");
		formparam.put("productSubCategory", "AC");
		formparam.put("productPrice", "55000");
		formparam.put("productDescription", "2 Ton AC");
		formparam.put("productFor", "Men");
		
		RequestSpecification reqAddProd = given().log().all().spec(reqAP).params(formparam).multiPart("productImage",new File("C:\\Users\\Mayank\\Desktop\\ACIMg.jpg"));
		
		String rspAddProd = reqAddProd.when().post("product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath jp1 = new JsonPath(rspAddProd);
		String productId = jp1.getString("productId");
		
		System.out.println("productId -> "+productId);
		
		RequestSpecification reqAPTC = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		PlaceOrderReq pOrd = new PlaceOrderReq();
		CreateOrderReq cOrd = new CreateOrderReq();
		cOrd.setCountry("India");
		cOrd.setProductOrderedId(productId);
		List<CreateOrderReq> lst = new ArrayList<CreateOrderReq>();
		lst.add(cOrd);
		pOrd.setOrders(lst);
		
		String rspAPTC = given().log().all().spec(reqAPTC).body(pOrd)
		.when().post("order/create-order")
		.then().log().all().assertThat().body("message",equalTo("Order Placed Successfully")).extract().response().asString();
		
		JsonPath jp2 = new JsonPath(rspAPTC);
		String orderId = jp2.getString("orders[0]");
		
		RequestSpecification reqGO = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/api/ecom").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		String orderIdfromget = given().log().all().spec(reqGO)
		.when().get("order/get-orders-for-customer/"+userId)
		.then().log().all().extract().response().asString();
		
		JsonPath jp3 = new JsonPath(orderIdfromget);
		String orderIdFromGet = jp3.getString("data[0]._id");
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("orderId -> "+orderId);
		System.out.println("orderId -> "+orderIdFromGet);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		
		given().log().all().baseUri("https://rahulshettyacademy.com/api/ecom/product/delete-product").header("Authorization", token)
		.when().delete(productId)
		.then().log().all();
		
		/*
		String getAllProd = given().log().all().baseUri("https://rahulshettyacademy.com/api/ecom").header("Authorization", token).header("Content-Type", "application/json")
		.when().post("product/get-all-products")
		.then().log().all().extract().response().asString();
		
		JsonPath jp2 = new JsonPath(getAllProd);
		String temp = null, temp1 = null;
		for(int i=0;i<jp2.getInt("data.size()");i++) {
			if(jp2.getString("data["+i+"]._id").equals(productId)) {
				temp = jp2.getString("data["+i+"]");
			}
		}
		temp1 = "{\""+temp.substring(1,temp.length()-1).replaceAll(", ", ", \"").replaceAll(":", "\":\"").replaceAll("\"https\":\"", "\"https:").replaceAll(".jpg,", ".jpg\",")+"\"}";
		System.out.println(temp1);
		*/
		/*
		AddProductReq ap = new AddProductReq();
		ap.set_id(userId);
		Product p = new Product();
		p.set_id(productId);
		p.setProductName("LG AC");
		p.setProductCategory("Applicance");
		p.setProductSubCategory("AC");
		p.setProductPrice(55000);
		p.setProductDescription("2 Ton AC");
		*/
	}
}
